package gd.mall.dao;

import gd.mall.po.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.Instant;
import java.util.List;

@Mapper
public interface OrderDao {

    public int insertOrder(Order order);

    public Order findNoPayOrder(@Param("userId") int userId,@Param("commodityId") int commodityId);

    public Order findOrderByOrderNo(String orderNo);

    public int updateOrderStatus(@Param("orderNo") String orderNo,@Param("orderStatus") char orderStatus);

    public List<Order> findNoPayOrderByDuration(Instant instant);
}
