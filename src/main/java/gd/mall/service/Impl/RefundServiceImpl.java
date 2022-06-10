package gd.mall.service.Impl;

import gd.mall.dao.RefundDao;
import gd.mall.po.Order;
import gd.mall.po.Refund;
import gd.mall.service.OrderService;
import gd.mall.service.RefundService;
import gd.mall.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RefundServiceImpl implements RefundService {

    @Autowired
    private OrderService orderService;

    @Resource
    private RefundDao refundDao;

    @Override
    public Refund createRefund(String orderNo, String refundReason) {

        //根据订单号获取订单信息
        Order order = orderService.findOrderByOrderNo(orderNo);

        Refund refund =new Refund();
        refund.setOrderNo(orderNo);
        refund.setRefundNo(OrderNoUtil.getRefundNo());//调用工具类生成退款单编号

        refund.setOrderFee(order.getOrderFee());//原订单金额
        refund.setRefundFee(order.getOrderFee());//全额退款,退款单金额与原订单金额一致
        refund.setRefundReason(refundReason);

        //将退款单保存进数据库
        refundDao.insertRefund(refund);

        return refund;
    }

    @Override
    public int updateRefundStatus(String refundNo, char refundStatus) {
        return refundDao.updateRefundStatus(refundNo, refundStatus);
    }
}
