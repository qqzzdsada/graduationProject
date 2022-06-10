package gd.mall.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {

    private int messageId;

    private int fromId;

    private String fromName;

    private String fromAvatar;

    private int toId;

    private String toName;

    private String toAvatar;

    private String messageContent;

    private String sendTime;
}
