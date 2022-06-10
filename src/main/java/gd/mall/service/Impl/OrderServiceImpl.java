package gd.mall.service.Impl;

import gd.mall.dao.CommodityDao;
import gd.mall.dao.OrderDao;
import gd.mall.po.Commodity;
import gd.mall.po.Order;
import gd.mall.service.OrderService;
import gd.mall.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.OrderUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.Duration;
import java.time.Instant;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CommodityDao commodityDao;

    //向数据库中添加一条订单记录
    @Override
    public int insertOrder(Order order) {
        return orderDao.insertOrder(order);
    }

    @Override
    public Order findNoPayOrder(int userId, int commodityId) {
        return orderDao.findNoPayOrder(userId,commodityId);
    }

    @Override
    public Order findOrderByOrderNo(String orderNo) {
        return orderDao.findOrderByOrderNo(orderNo);
    }

    @Override
    public int updateOrderStatus(String orderNo, char orderStatus) {
        return orderDao.updateOrderStatus(orderNo, orderStatus);
    }

    @Override
    public List<Order> findNoPayOrderByDuration(int minutes) {

        Instant instant = Instant.now().minus(Duration.ofMinutes(minutes));

        return orderDao.findNoPayOrderByDuration(instant);
    }

    //生成订单
    @Override
    public Order createOrder(int userId, int commodityId) {

        //查找已存在但未支付的订单
        Order order = this.findNoPayOrder(userId, commodityId);
        if(order!=null)
            return order;

        //获取商品信息
        Commodity commodity =commodityDao.findCommodityById(commodityId);

        //生成订单
        Order newOrder =new Order();
        newOrder.setOrderTitle(commodity.getCommodityDesc());
        newOrder.setOrderNo(OrderNoUtil.getOrderNo());
        newOrder.setUserId(userId);
        newOrder.setCommodityId(commodityId);
        newOrder.setOrderFee(commodity.getCommodityPrice());
        this.insertOrder(newOrder);

        return newOrder;
    }

}
