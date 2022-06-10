package gd.mall.dao;

import gd.mall.po.Picture;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PictureDao {

    public int insertPicture(Picture picture);
}
