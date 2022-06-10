package gd.mall.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.google.gson.Gson;
import gd.mall.po.Commodity;
import gd.mall.po.Order;
import gd.mall.po.Refund;
import gd.mall.service.AliPayService;
import gd.mall.service.CommodityService;
import gd.mall.service.OrderService;
import gd.mall.service.RefundService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Service
public class AliPayServiceImpl implements AliPayService {

    @Resource
    private OrderService orderService;

    @Resource
    private CommodityService commodityService;

    @Resource
    private AlipayClient alipayClient;

    @Resource
    private Environment config;

    @Resource
    private RefundService refundService;


    /**
     * 向支付宝发起支付请求,跳转到支付页面
     * @param userId
     * @param commodityId
     * @return
     */
    @Override
    @Transactional(rollbackFor =Exception.class)
    public String createTrade(int userId,int commodityId) {

        try {
        //生成订单
        Order order = orderService.createOrder(userId,commodityId);

        //调用支付宝接口
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();

        //配置可选的公共请求参数
        request.setNotifyUrl(config.getProperty("alipay.notify-url"));
//      request.setReturnUrl("");

        //组装当前业务方法的请求参数
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", order.getOrderNo());
        BigDecimal fee = new BigDecimal(order.getOrderFee().toString()).divide(new BigDecimal("100"));
        bizContent.put("total_amount", fee);
        bizContent.put("subject", order.getOrderTitle());
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");

        //发起请求,调用支付宝接口
        request.setBizContent(bizContent.toString());
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
        if(response.isSuccess()){
            System.out.println("支付接口调用成功");
            String res = response.getBody();
            return res;
        } else {
            System.out.println("调用失败");
            System.out.println(response.getCode()+"   "+response.getMsg());
            throw new RuntimeException("创建支付交易失败");
        }

            } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException("创建支付交易失败");
        }
    }

    /**
     * 交易处理
     * @param order
     */
    @Transactional(rollbackFor =Exception.class)
    public void processTrade(Order order) {

        //修改订单状态
        int r1 = orderService.updateOrderStatus(order.getOrderNo(), '1');

        //修改商品信息(商品状态和商品买家)
        Commodity commodity = commodityService.findCommodityById(order.getCommodityId());
        commodity.setCommodityStatus('0');
        commodity.setBuyerId(order.getUserId());
        int r2 = commodityService.updateCommodity(commodity);

    }

    /**
     * 订单查询
     * @param orderNo
     * @return
     */
    @Override
    public String queryOrder(String orderNo) {

        try {
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            JSONObject bizContent = new JSONObject();
            bizContent.put("out_trade_no", orderNo);
            request.setBizContent(bizContent.toString());

            AlipayTradeQueryResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
                System.out.println("调用成功");
                return response.getBody();
            } else {
                System.out.println("调用失败");
                return null;//订单不存在
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException("查单接口调用失败");
        }
    }

    /**
     * 取消该订单(修改订单状态)
     * @param orderNo
     */
    @Override
    public void cancelOrder(String orderNo) {

        //调用支付宝的统一收单交易关闭接口
        this.tradeClose(orderNo);

        //为保证商户系统和支付宝端的订单状态一致,更改商户系统中的订单状态
        int row = orderService.updateOrderStatus(orderNo, '2');
    }

    /**
     * 核实订单状态,保证同一笔订单在商户系统和支付宝端的订单状态的一致
     * @param orderNo
     */
    @Override
    public void checkOrderStatus(String orderNo) {
        //根据订单号调用支付宝查单接口,核实订单状态
        String result = this.queryOrder(orderNo);//result是JSON字符串的形式

        //如果订单未创建
        if(result == null)
        {
            //修改商户系统订单状态
            orderService.updateOrderStatus(orderNo,'2');
        }

        //解析查单响应结果
        Gson gson = new Gson();
        HashMap<String, LinkedHashMap> resultMap = gson.fromJson(result, HashMap.class);
        LinkedHashMap alipayTradeQueryResponse = resultMap.get("alipay_trade_query_response");

        String trade_status = (String) alipayTradeQueryResponse.get("trade_status");

        //如果订单未支付
        if(trade_status.equals("WAIT_BUYER_PAY")) {
            //调用关单接口修改订单状态
            this.tradeClose(orderNo);
            //修改商户系统订单状态
            orderService.updateOrderStatus(orderNo,'2');
        }
        //如果订单已支付
        else if(trade_status.equals("TRADE_SUCCESS")){
            Order order = orderService.findOrderByOrderNo(orderNo);
            //业务处理
            this.processTrade(order);
        }
    }

    /**
     * 发起退款请求
     * @param orderNo
     * @param reason
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void refund(String orderNo, String reason) {

        try {
            //创建退款单
            Refund refund = refundService.createRefund(orderNo, reason);

            //调用统一收单交易退款接口
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();

            JSONObject bizContent = new JSONObject();
            bizContent.put("out_trade_no", orderNo);
            BigDecimal fee = new BigDecimal(refund.getRefundFee().toString()).divide(new BigDecimal("100"));
            bizContent.put("refund_amount", fee);
            bizContent.put("refund_reason", reason);//可选

            //如果一笔订单可以发起多笔退款,则需要传入退款单编号
            //bizContent.put("out_request_no", "HZ01RF001");

            //发送请求,调用支付宝接口
            request.setBizContent(bizContent.toString());
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            //退款成功
            if(response.isSuccess()){
                //更新订单状态
                orderService.updateOrderStatus(orderNo,'3');
            }
            //退款失败
            else {
                //更新订单状态
                orderService.updateOrderStatus(orderNo,'4');
                //更新退款单状态
                refundService.updateRefundStatus(refund.getRefundNo(),'0');
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

    }

    /**
     * 退款单查询
     * @param orderNo
     * @return
     */
    @Override
    public String queryRefund(String orderNo) {

        try{
            AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
            JSONObject bizContent = new JSONObject();
            bizContent.put("out_trade_no", orderNo);
            bizContent.put("out_request_no", orderNo);

            request.setBizContent(bizContent.toString());
            AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
                System.out.println("调用成功");
                return response.getBody();
            } else {
                System.out.println("调用失败");
                return null; //退款单不存在
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException("");
        }

    }

    /**
     * 账单查询
     * @param billDate
     * @param billType
     * @return
     */
    @Override
    public String queryBill(String billDate, String billType) {

        try {
            AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();

            JSONObject bizContent = new JSONObject();
            bizContent.put("bill_type", billType);//账单类型分为交易账单和资金账单
            bizContent.put("bill_date", billDate);

            request.setBizContent(bizContent.toString());

            AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {

                //解析响应结果,获取账单下载地址
                Gson gson=new Gson();
                HashMap<String,LinkedHashMap> resultMap = gson.fromJson(response.getBody(), HashMap.class);
                LinkedHashMap billDownloadResponse = resultMap.get("alipay_data_dataservice_bill_downloadurl_query_response");
                String billDownloadUrl = (String) billDownloadResponse.get("bill_download_url");

                return billDownloadUrl;
            } else {
                System.out.println("调用失败");
                return null;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException("申请账单失败");
        }

    }

    private void tradeClose(String orderNo) {

        //当用户 "完成扫码登录" 或 "输入账户名和支付密码登录支付宝" 后
        //支付宝端才会生成交易记录(订单),此时的订单状态是等待用户支付(未支付)
        try {
            AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
            JSONObject bizContent = new JSONObject();
            bizContent.put("out_trade_no", orderNo);
            request.setBizContent(bizContent.toString());

            AlipayTradeCloseResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
                System.out.println("调用成功");
            } else {
                System.out.println("调用失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException("关单接口调用失败");
        }

    }
}
