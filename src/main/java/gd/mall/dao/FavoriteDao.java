package gd.mall.dao;

import gd.mall.po.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FavoriteDao {

    public int addFavorite(Favorite favorite);

    public List<Favorite> findFavoriteById(int userId);

    public Favorite checkFavorite(@Param("userId") int userId,@Param("commodityId") int commodityId);

    public int cancelFavorite(@Param("userId") int userId,@Param("commodityId") int commodityId);
}
