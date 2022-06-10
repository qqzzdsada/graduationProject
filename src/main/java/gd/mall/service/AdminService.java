package gd.mall.service;

import gd.mall.po.Admin;

public interface AdminService {

    public Admin findAdmin(String adminCode,String adminPwd);
}
