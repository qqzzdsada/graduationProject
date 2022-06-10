package gd.mall.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.AlipaySignature;
import gd.mall.po.Order;
import gd.mall.service.AliPayService;
import gd.mall.service.OrderService;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AliPayController {

    @Resource
    private AliPayService aliPayService;

    @Resource
    private Environment config;

    @Resource
    private OrderService orderService;

    @RequestMapping(value = "doBuy")
    public Map<String,Object> doBuy(int userId, int commodityId) {
        Map<String,Object> json =new HashMap<>();

        //支付宝开放平台接受AlipayTradePagePayRequest请求对象后
        //会为开发者生成一个html形式的form表单,包含自动提交脚本
        String formStr =aliPayService.createTrade(userId,commodityId);

        //将form表单字符串返回给前端,前端会调用自动提交脚本,进行表单提交
        //此时,表单会自动提交到action属性指向的支付宝开放平台中,从而为用户展示一个支付页面
        json.put("res",formStr);
        return json;
    }

    /**
     * 支付成功的异步通知接收,需内网穿透
     * @param params
     * @return
     */
    @PostMapping("tradeNoifty")
    public String tradeNoifty(@RequestParam Map<String,String> params)
    {
        try{
            //调用SDK验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(
                    params,
                    config.getProperty("alipay.alipay-public-key"),
                    AlipayConstants.CHARSET_UTF8,
                    AlipayConstants.SIGN_TYPE_RSA2);
            if(!signVerified){
                //验签失败则记录异常日志，并在response中返回failure.
                System.out.println("验签失败");
                return "failure";
            }

            //验签成功
            System.out.println("验签成功");
            //按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验
            //1.商户需要验证该通知数据中的 out_trade_no 是否为商户系统中创建的订单号
            String outTradeNo = params.get("out_trade_no");
            Order order = orderService.findOrderByOrderNo(outTradeNo);
            if(order == null) {
                System.out.println("订单不存在");
                return "failure";
            }

            //2.判断 total_amount 是否确实为该订单的实际金额（即商户订单创建时的金额）
            String totalAmount = params.get("total_amount");
            int fee = new BigDecimal(totalAmount).multiply(new BigDecimal("100")).intValue();
            int orderFee = order.getOrderFee().intValue();
            if(fee!=orderFee){
                System.out.println("金额校验失败");
                return "failure";
            }

            //3.校验通知中的 seller_id（或者 seller_email) 是否为 out_trade_no 这笔单据的对应的操作方
            String sellerId = params.get("seller_id");
            String Pid = config.getProperty("alipay.seller-id");
            if(!sellerId.equals(Pid)){
                System.out.println("商家pid校验失败");
                return "failure";
            }

            //4.验证 app_id 是否为该商户本身
            String appId = params.get("app_id");
            String appIdProperty = config.getProperty("alipay.app-id");
            if(!appId.equals(appIdProperty)){
                System.out.println("appId校验失败");
                return "failure";
            }

            //在支付宝的业务通知中，只有交易通知状态为 TRADE_SUCCESS时，支付宝才会认定为买家付款成功
            String tradeStatus = params.get("trade_status");
            if(!tradeStatus.equals("TRADE_SUCCESS")){
                System.out.println("支付未成功");
                return "failure";
            }

            //校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
            aliPayService.processTrade(order);

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "success";
    }


    @PostMapping("cancelOrder")
    public Map<String,Object> cancelOrder(String orderNo){
        Map<String,Object> json =new HashMap<>();
        aliPayService.cancelOrder(orderNo);
        return json;
    }

    @RequestMapping("queryOrder")
    public Map<String,Object> queryOrder(String orderNo)
    {
        Map<String,Object> json =new HashMap<>();
        String result = aliPayService.queryOrder(orderNo);
        json.put("res",result);
        return json;
    }

    @PostMapping("refund")
    public Map<String,Object> refund(String orderNo,String reason)
    {
        Map<String,Object> json =new HashMap<>();
        aliPayService.refund(orderNo,reason);
        return json;
    }

    @GetMapping("queryRefund")
    public Map<String,Object> queryRefund(String orderNo){
        Map<String,Object> json =new HashMap<>();
        String result = aliPayService.queryRefund(orderNo);
        json.put("res",result);
        return json;
    }

    @GetMapping("downloadBill")
    public Map<String,Object> downloadBill(String billDate,String billType)
    {
        Map<String,Object> json =new HashMap<>();
        String downloadUrl = aliPayService.queryBill(billDate,billType);
        json.put("res",downloadUrl);
        return json;
    }

}
