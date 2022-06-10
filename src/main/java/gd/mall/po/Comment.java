package gd.mall.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private int commentId;

    private int userId;

    private String userNickname;

    private String userAvatar;

    private int commodityId;

    private String commentContent;

    private String commentTime;

    private int commentLike;

    private int parentId;

    List<Comment> child;

}
