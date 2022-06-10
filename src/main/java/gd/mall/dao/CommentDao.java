package gd.mall.dao;

import gd.mall.po.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDao {

    public int insertComment(Comment comment);

    public List<Comment> findCommentBycommodityId(int commodityId);
}
