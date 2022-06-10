package gd.mall.ws;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import gd.mall.po.Message;
import gd.mall.po.User;
import gd.mall.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ServerEndpoint(value = "/chat",configurator = GetHttpSessionConfigurator.class)
@Component
public class ChatEndpoint {


    public static MessageService messageService;

    //用来存储每一个客户端对象对应的ChatEndpoint对象
    private static Map<String,ChatEndpoint> onlineUsers=new ConcurrentHashMap<>();

    //声明Session对象,通过该对象可以发送消息给指定的用户
    private Session session;

    //声明一个HttpSession对象,我们之前在HttpSession对象存储了user
    private HttpSession httpSession;


    //连接建立时被调用
    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        //将局部的session对象赋值给成员session
        this.session=session;
        //获取httpSession对象
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession=httpSession;
        //从httpSession对象中获取用户昵称
        User user = (User) httpSession.getAttribute("User");
        String userNickname=user.getUserNickname();
        //将当前对象存储到集合中
        onlineUsers.put(userNickname,this);
        System.out.println(onlineUsers);
    }

    //接收到客户端发送的数据时被调用
    @OnMessage
    public void onMessage(Session session,String message){
        //接收到的数据message是JSON字符串,解封装获取数据
        JSONObject obj = JSONUtil.parseObj(message);
        String toName = obj.getStr("toName"); // toName表示发送给哪个用户
        String messageContent = obj.getStr("messageContent"); // 发送的消息文本
        String sendTime =obj.getStr("sendTime");
        String toAvatar =obj.getStr("toAvatar");
        String str =obj.getStr("toId");
        int toId=Integer.parseInt(str);
        //获取当前登录的用户
        User user = (User) httpSession.getAttribute("User");
        int fromId =user.getUserId();
        String fromName=user.getUserNickname();
        String fromAvatar=user.getUserAvatar();
        //数据封装进一个实体类中
        Message mes = new Message(0,fromId,fromName,fromAvatar,toId,toName,toAvatar,messageContent,sendTime);
        //将数据存储进数据库
        int row = messageService.insertMessage(mes);
        //确认接收数据的用户是否在线,在线才发
        ChatEndpoint chatEndpoint=onlineUsers.getOrDefault(toName,null);
        if(chatEndpoint!=null)
        {
            //数据封装
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("fromName", fromName);
            jsonObject.set("messageContent", messageContent);
            jsonObject.set("sendTime", sendTime);
            //数据转换成JSON字符串并发送
            this.sendMessage(chatEndpoint.session,jsonObject.toString());
        }
    }

    //发送数据
    private void sendMessage(Session session,String message)
    {
        try{
            session.getAsyncRemote().sendText(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //连接关闭时被调用
    @OnClose
    public void onClose(Session session){

    }

}
