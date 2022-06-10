package gd.mall.service.Impl;

import gd.mall.dao.FavoriteDao;
import gd.mall.po.Favorite;
import gd.mall.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteDao favoriteDao;

    @Override
    public int addFavorite(Favorite favorite) {
        return favoriteDao.addFavorite(favorite);
    }

    @Override
    public List<Favorite> findFavoriteById(int userId) {
        return favoriteDao.findFavoriteById(userId);
    }

    @Override
    public Favorite checkFavorite(int userId, int commodityId) {
        return favoriteDao.checkFavorite(userId,commodityId);
    }

    @Override
    public int cancelFavorite(int userId, int commodityId) {
        return favoriteDao.cancelFavorite(userId,commodityId);
    }


}
