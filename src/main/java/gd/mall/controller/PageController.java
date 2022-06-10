package gd.mall.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController implements CommandLineRunner {

    //设置首页
    @Override
    public void run(String... args) throws Exception {
        try {
            Runtime.getRuntime().exec("cmd   /c   start   http://localhost:8080/mall/toindex");//可以指定自己的路径
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping("toindex")
    public String toIndex()
    {
        return "index";
    }

    @RequestMapping("tobackground")
    public String toBackground()
    {
        return "background";
    }

    @RequestMapping("tologin")
    public String toLogin()
    {
        return "login";
    }

    @RequestMapping("toregister")
    public String toRegister()
    {
        return "register";
    }

    @RequestMapping("tosell")
    public String toSell()
    {
        return "sell-commodity";
    }

    @RequestMapping("registersuccess")
    public String registersuccess()
    {
        return "register-success";
    }

    @RequestMapping("tomanage")
    public String tomanage()
    {
        return "management";
    }

    @RequestMapping("userlist")
    public String userlist(){
        return "user-list";
    }

    @RequestMapping("sellingcommoditylist")
    public String sellingcommoditylist()
    {
        return "sellingcommodity-list";
    }

    @RequestMapping("soldcommoditylist")
    public String soldcommoditylist()
    {
        return "soldcommodity-list";
    }

    @RequestMapping("offcommoditylist")
    public String offcommoditylist()
    {
        return "offcommodity-list";
    }

    @RequestMapping("myfavorite")
    public String myfavorite(){
        return "myfavorite";
    }

    @RequestMapping("mycommodity")
    public String mycommodity(){
        return "mycommodity";
    }

    @RequestMapping("tohome")
    public String tohome()
    {
        return "home";
    }

    @RequestMapping("toMymessage")
    public String toMymessage()
    {
        return "mymessage";
    }
}
