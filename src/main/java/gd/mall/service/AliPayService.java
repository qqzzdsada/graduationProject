package gd.mall.service;

import gd.mall.po.Order;

public interface AliPayService {

    public String createTrade(int userId,int commodityId);

    public void processTrade(Order order);

    public String queryOrder(String orderNo);

    public void cancelOrder(String orderNo);

    public void checkOrderStatus(String orderNo);

    public void refund(String orderNo, String reason);

    public String queryBill(String billDate, String billType);

    public String queryRefund(String orderNo);

}
