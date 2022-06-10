package gd.mall.task;

import gd.mall.po.Order;
import gd.mall.service.AliPayService;
import gd.mall.service.OrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class AliPayTask {

    @Resource
    private OrderService orderService;

    @Resource
    private AliPayService aliPayService;

    //设置定时执行此方法,从服务器运行开始经过每分钟的0秒和30秒执行
    @Scheduled(cron = "0/30 * * * * ?")
    public void orderConfirm(){

        //查询创建超过15分钟尚未支付的订单
        List<Order> orderList = orderService.findNoPayOrderByDuration(15);

        //修改这些订单的状态
        for(Order order:orderList)
        {
            String orderNo = order.getOrderNo();

            //调用支付宝查单接口,核实订单状态,保证同一笔订单在商户系统和支付宝端的状态一致
            aliPayService.checkOrderStatus(orderNo);
        }

    }
}
