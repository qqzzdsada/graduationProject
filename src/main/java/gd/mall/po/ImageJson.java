package gd.mall.po;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ImageJson {

    private Integer code;

    private String msg;

    private Map<String,String> data;
}
