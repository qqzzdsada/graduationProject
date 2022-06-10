package gd.mall.service.Impl;

import gd.mall.dao.AdminDao;
import gd.mall.po.Admin;
import gd.mall.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Override
    public Admin findAdmin(String adminCode, String adminPwd) {
        return adminDao.findAdmin(adminCode, adminPwd);
    }
}
