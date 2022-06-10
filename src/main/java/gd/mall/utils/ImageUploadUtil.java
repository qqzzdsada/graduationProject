package gd.mall.utils;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

//上传图片的工具类
public class ImageUploadUtil {

    //定义图片上传路径
    private static final String uploadPath="D:\\graduationProject\\src\\main\\resources\\static\\images\\";

    //定义图片的访问路径
    private static final String viewPath="images/";

    public static String upload(MultipartFile file,String filename)
    {

        //创建一个文件对象
        File image =new File(uploadPath,filename);

        try {
            //进行文件上传操作
            file.transferTo(image);
        } catch (IOException e) {
            return null;
        }

        return filename;
    }

}
