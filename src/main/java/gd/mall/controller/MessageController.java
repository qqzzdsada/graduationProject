package gd.mall.controller;

import gd.mall.po.Message;
import gd.mall.po.User;
import gd.mall.service.MessageService;
import gd.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class MessageController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @RequestMapping("toChat")
    public String toChat(int sellerId, Model model)
    {
        User seller = userService.findUserByuserId(sellerId);
        model.addAttribute("seller",seller);
        return "chat";
    }

    @RequestMapping("findMessage")
    @ResponseBody
    public List<Message> findMessage(HttpServletRequest request,String toName)
    {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");
        String fromName = user.getUserNickname();
        return messageService.findMessage(fromName,toName);
    }

    @ResponseBody
    @RequestMapping("myMessage")
    public List<User> myMessage(String userNickname)
    {
        List<User> userList =new LinkedList<>();
        List<Message> list = messageService.findMessageByuserNickname(userNickname);
        for(Message mes :list)
        {
            User user=new User();
            //如果当前用户是发送消息的人,那么接收消息的人就放入联系人列表
            if(mes.getFromName().equals(userNickname))
            {
                user.setUserId(mes.getToId());
                user.setUserNickname(mes.getToName());
                user.setUserAvatar(mes.getToAvatar());
            }
            else
            {
                user.setUserId(mes.getFromId());
                user.setUserNickname(mes.getFromName());
                user.setUserAvatar(mes.getFromAvatar());
            }
            userList.add(user);
        }
        return userList;
    }


}
