package gd.mall.dao;

import gd.mall.po.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommodityDao {

    public int insertCommodity(Commodity commodity);

    public Commodity findCommodityById(int commodityId);

    public List<Commodity> findcommodities();

    public List<Commodity> findCommodityByStatus(char commodityStatus);

    public List<Commodity> findmyCommodity(int userId);

    public List<Commodity> findSomecommodity(@Param("keyword") String keyword);

    public List<Commodity> findmyFavorite(int userId);

    public int updateCommodity(Commodity commodity);
}
