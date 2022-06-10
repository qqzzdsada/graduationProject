package gd.mall.dao;

import gd.mall.po.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminDao {

    public Admin findAdmin(@Param("adminCode") String adminCode, @Param("adminPwd") String adminPwd);

}
