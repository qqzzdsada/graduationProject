package gd.mall.service;

import gd.mall.po.Commodity;

import java.util.List;

public interface CommodityService {

    public int insertCommodity(Commodity commodity);

    public Commodity findCommodityById(int commodityId);

    public List<Commodity> findcommodities();

    public List<Commodity> findCommodityByStatus(char commodityStatus);

    public List<Commodity> findmyCommodity(int userId);

    public List<Commodity> findSomecommodity(String keyword);

    public List<Commodity> findmyFavorite(int userId);

    public int updateCommodity(Commodity commodity);
}
