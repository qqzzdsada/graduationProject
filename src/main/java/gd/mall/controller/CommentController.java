package gd.mall.controller;

import gd.mall.po.Comment;
import gd.mall.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @ResponseBody
    @RequestMapping("insertComment")
    public Map<String,Object> insertComment(Comment comment){
        Map<String,Object> json=new HashMap<>();
        int row = commentService.insertComment(comment);
        if(row==1)
            json.put("status",1);
        else
            json.put("status",-1);
        return json;
    }

    @ResponseBody
    @RequestMapping("findCommentBycommodityId")
    public List<Comment> findCommentBycommodityId(int commodityId)
    {
        return commentService.findCommentBycommodityId(commodityId);
    }
}
