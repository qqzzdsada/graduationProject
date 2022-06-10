package gd.mall.service.Impl;

import gd.mall.dao.MessageDao;
import gd.mall.po.Message;
import gd.mall.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Override
    public int insertMessage(Message message) {
        return messageDao.insertMessage(message);
    }

    @Override
    public List<Message> findMessage(String fromName, String toName) {
        return messageDao.findMessage(fromName,toName);
    }

    @Override
    public List<Message> findMessageByuserNickname(String userNickname) {
        return messageDao.findMessageByuserNickname(userNickname);
    }
}
