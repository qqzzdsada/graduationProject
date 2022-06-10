package gd.mall.utils;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

//�ϴ�ͼƬ�Ĺ�����
public class ImageUploadUtil {

    //����ͼƬ�ϴ�·��
    private static final String uploadPath="D:\\graduationProject\\src\\main\\resources\\static\\images\\";

    //����ͼƬ�ķ���·��
    private static final String viewPath="images/";

    public static String upload(MultipartFile file,String filename)
    {

        //����һ���ļ�����
        File image =new File(uploadPath,filename);

        try {
            //�����ļ��ϴ�����
            file.transferTo(image);
        } catch (IOException e) {
            return null;
        }

        return filename;
    }

}
