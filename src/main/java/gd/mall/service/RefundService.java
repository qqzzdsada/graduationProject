package gd.mall.service;

import gd.mall.po.Refund;

public interface RefundService {

    public Refund createRefund(String orderNo,String refundReason);

    public int updateRefundStatus(String refundNo,char refundStatus);
}
