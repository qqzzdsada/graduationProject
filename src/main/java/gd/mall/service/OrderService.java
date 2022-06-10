package gd.mall.service;

import gd.mall.po.Order;

import java.time.Instant;
import java.util.List;

public interface OrderService {

    public int insertOrder(Order order);

    public Order createOrder(int userId, int commodityId);

    public Order findNoPayOrder(int userId, int commodityId);

    public Order findOrderByOrderNo(String orderNo);

    public int updateOrderStatus(String orderNo,char orderStatus);

    public List<Order> findNoPayOrderByDuration(int minutes);
}
