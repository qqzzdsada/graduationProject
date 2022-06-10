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
public class Refund {

    private int refundId;

    private String orderNo;

    //退款单编号,一笔订单可能有多笔退款
    private String refundNo;

    private Integer orderFee;

    //所有退款单的金额之和须小于等于订单金额,若为全额退款,则该笔退款单的金额等于订单金额
    private Integer refundFee;

    private String refundReason;

    private char refundStatus;

    private Date refundCtime;
}
