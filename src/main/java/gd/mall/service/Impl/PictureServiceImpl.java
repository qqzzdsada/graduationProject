package gd.mall.service.Impl;

import gd.mall.dao.PictureDao;
import gd.mall.po.Picture;
import gd.mall.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureDao pictureDao;

    @Override
    public int insertPicture(Picture picture) {
        return pictureDao.insertPicture(picture);
    }
}
