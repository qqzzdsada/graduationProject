package gd.mall.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    private int userId;

    private String userCode;

    private String userPwd;

    private String userNickname;

    private String userAvatar;

}
