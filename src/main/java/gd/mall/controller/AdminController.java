package gd.mall.controller;

import gd.mall.po.Admin;
import gd.mall.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @ResponseBody
    @RequestMapping("doadmin")
    public Map<String,Object> loginBackground(String adminCode, String adminPwd)
    {
        Admin admin=adminService.findAdmin(adminCode, adminPwd);
        Map<String,Object> json=new HashMap<>();
        if(admin!=null)
            json.put("status",1);
        else
            json.put("status",-1);
        return json;
    }

}
