package gd.mall.service;

import gd.mall.po.Comment;
import gd.mall.po.Commodity;

import java.util.List;

public interface CommentService {

    public int insertComment(Comment comment);

    public List<Comment> findCommentBycommodityId(int commodityId);

}
