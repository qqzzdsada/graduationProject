package gd.mall.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {

    private int orderId;

    private String orderTitle;

    private String orderNo;

    private int userId;

    private int commodityId;

    private Integer orderFee;

    private char orderStatus;

    private Date orderCtime;

    private Date orderUtime;

}
