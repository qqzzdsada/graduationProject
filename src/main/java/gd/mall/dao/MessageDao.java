package gd.mall.dao;

import gd.mall.po.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageDao {

    public int insertMessage(Message message);

    public List<Message> findMessage(@Param("fromName") String fromName, @Param("toName") String toName);

    public List<Message> findMessageByuserNickname(String userNickname);
}
