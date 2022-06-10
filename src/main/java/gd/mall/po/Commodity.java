package gd.mall.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Commodity {

    private int commodityId;

    private String commodityDesc;

    private char commodityLoss;//商品损耗情况

    private Integer commodityPrice;

    private char commodityStatus;

    private int sellerId;

    private int buyerId;

    private String sellerNickname;

    private String commodityPicture;

}
