package gd.mall.service.Impl;

import gd.mall.dao.CommodityDao;
import gd.mall.po.Commodity;
import gd.mall.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    private CommodityDao commodityDao;

    @Override
    public int insertCommodity(Commodity commodity) {
        return commodityDao.insertCommodity(commodity);
    }

    @Override
    public Commodity findCommodityById(int commodityId) {
        return commodityDao.findCommodityById(commodityId);
    }

    @Override
    public List<Commodity> findcommodities() {
        return commodityDao.findcommodities();
    }

    @Override
    public List<Commodity> findCommodityByStatus(char commodityStatus) {
        return commodityDao.findCommodityByStatus(commodityStatus);
    }

    @Override
    public List<Commodity> findmyCommodity(int userId) {
        return commodityDao.findmyCommodity(userId);
    }

    @Override
    public List<Commodity> findSomecommodity(String keyword) {
        return commodityDao.findSomecommodity(keyword);
    }

    @Override
    public List<Commodity> findmyFavorite(int userId) {
        return commodityDao.findmyFavorite(userId);
    }

    @Override
    public int updateCommodity(Commodity commodity) {
        return commodityDao.updateCommodity(commodity);
    }


}
