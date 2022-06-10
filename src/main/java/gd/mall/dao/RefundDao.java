package gd.mall.dao;

import gd.mall.po.Refund;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RefundDao {

    public int insertRefund(Refund refund);

    public int updateRefundStatus(@Param("refundNo") String refundNo,@Param("refundStatus") char refundStatus);
}
