package gd.mall.service;

import gd.mall.po.Favorite;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FavoriteService {

    public int addFavorite(Favorite favorite);

    public List<Favorite> findFavoriteById(int userId);

    public Favorite checkFavorite(int userId,int commodityId);

    public int cancelFavorite(int userId, int commodityId);

}
