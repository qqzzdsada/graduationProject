package gd.mall.controller;

import com.sun.corba.se.spi.ior.ObjectKey;
import gd.mall.po.User;
import gd.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("dologin")
    public Map<String,Object> dologin(String userCode, String userPwd,HttpSession session)
    {
        Map<String,Object> json=new HashMap<String, Object>();
        User user=userService.findUser(userCode,userPwd);
        if(user!=null)
        {
            session.setAttribute("User",user);
            json.put("status",1);
        }
        else
            json.put("status",-1);

        return json;
    }

    @ResponseBody
    @RequestMapping("doregister")
    public Map<String,Object> doregister(User user)
    {
        Map<String,Object> json=new HashMap<>();
        User user2=userService.findUserByuserCode(user.getUserCode());
        if(user2!=null)
        {
            json.put("status",2);
            return json;
        }
        User user3=userService.findUserByuserNickname(user.getUserNickname());
        if(user3!=null)
        {
            json.put("status",3);
            return json;
        }
        int rows=userService.insertUser(user);
        if(rows==1)
            json.put("status",1);
        else
            json.put("status",-1);
        return json;
    }

    @ResponseBody
    @RequestMapping("loginInfo")
    public Map<String, Object> loginInfo(HttpServletRequest request)
    {
        Map<String, Object> json = new HashMap<String, Object>();
        HttpSession session=request.getSession();
        User user=(User) session.getAttribute("User");
        if(user!=null)
            json.put("user",user);
        return json;
    }

    @RequestMapping("loginout")
    public String loginout(HttpServletRequest request){
        HttpSession session=request.getSession();
        session.removeAttribute("User");
        return "index";
    }

    @ResponseBody
    @RequestMapping("findAllUser")
    public Map<String,Object> findAllUser(){
        Map<String,Object> json=new HashMap<>();
        List<User> userList = userService.findAllUser();
        if(userList!=null)
        {
            json.put("code",0);
            json.put("msg","查询成功");
            json.put("count",userList.size());
            json.put("data",userList);
        }
        else
            json.put("code",500);
        return json;
    }


    @RequestMapping("userinfo")
    public String userinfo(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        User user= (User) session.getAttribute("User");
        model.addAttribute("theUser",user);
        return "userinfo";
    }

    @ResponseBody
    @RequestMapping("deleteUser")
    public Map<String,Object> deleteUser(int userId)
    {
        Map<String,Object> json=new HashMap<>();
        int rows=userService.deleteUser(userId);
        if(rows==1)
            json.put("status",1);
        else
            json.put("status",-1);
        return json;
    }

    @ResponseBody
    @RequestMapping("updateUser")
    public Map<String, Object> updateUser(User user)
    {
        Map<String,Object> json=new HashMap<>();
        int rows = userService.updateUser(user);
        if(rows==1)
        {
            json.put("status",1);
            json.put("theUser",user);
        }
        else
            json.put("status",-1);
        return json;
    }

}
