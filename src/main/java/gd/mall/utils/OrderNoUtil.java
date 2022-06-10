package gd.mall.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderNoUtil {

    //获取订单编号
    public static String getOrderNo(){
        return "ORDER_"+getNo();
    }

    //获取退款单编号
    public static String getRefundNo(){
        return "REFUND_"+getNo();
    }

    //生成编号
    public static String getNo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        String result = "";
        Random random = new Random();
        for(int i=0;i<3;i++) {
            result+= random.nextInt(10);
        }
        return newDate+result;
    }

}
