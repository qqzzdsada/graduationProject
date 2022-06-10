package gd.mall.service.Impl;

import gd.mall.dao.CommentDao;
import gd.mall.po.Comment;
import gd.mall.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentDao commentDao;

    @Override
    public int insertComment(Comment comment) {
        return commentDao.insertComment(comment);
    }

    @Override
    public List<Comment> findCommentBycommodityId(int commodityId) {
        return commentDao.findCommentBycommodityId(commodityId);
    }
}
