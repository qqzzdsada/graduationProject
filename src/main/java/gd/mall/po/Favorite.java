package gd.mall.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Favorite {

    private int favoriteId;

    private int userId;

    private int commodityId;

}
