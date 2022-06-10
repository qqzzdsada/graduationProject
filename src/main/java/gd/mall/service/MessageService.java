package gd.mall.service;


import gd.mall.po.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageService {

    public int insertMessage(Message message);

    public List<Message> findMessage(String fromName,String toName);

    public List<Message> findMessageByuserNickname(String userNickname);
}
