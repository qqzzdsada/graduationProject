package gd.mall.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import gd.mall.po.Commodity;
import gd.mall.po.ImageJson;
import gd.mall.po.Picture;
import gd.mall.po.User;
import gd.mall.service.CommodityService;
import gd.mall.service.PictureService;
import gd.mall.utils.ImageUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private PictureService pictureService;

    @RequestMapping(value = "dosell",method = {RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> dosell(Commodity commodity,String pictures)
    {

        int commodityId=commodity.getCommodityId();
        JSONArray jsonArray = JSONUtil.parseArray(pictures);
        List<String> list=jsonArray.toList(String.class);
        if(list.size()>0)
        {
            commodity.setCommodityPicture(list.get(0));
            //将商品图片存储进数据库中
            for(String str : list)
            {
                Picture picture=new Picture(0,commodityId,str);
                int r = pictureService.insertPicture(picture);
            }
        }
        else
            commodity.setCommodityPicture("noPicture.png");
        commodity.setCommodityStatus('1');
        Map<String,Object> json=new HashMap<>();
        int rows=commodityService.insertCommodity(commodity);
        if(rows==1)
            json.put("status",1);
        else
            json.put("status",-1);
        return json;
    }

    @ResponseBody
    @RequestMapping("uploadImage")
    public ImageJson uploadImage(MultipartFile file, HttpServletRequest request)
    {
        //获取上传文件的名称
        String filename =file.getOriginalFilename();

        //为了保证图片在服务器中名字的唯一性,我们要用UUID来对filename进行改写
        //String uuid= UUID.randomUUID().toString().replace("-","");
        //将生成的uuid与filename进行拼接
        //String newFilename=uuid+"-"+filename;
        String newFilename=UUID.randomUUID().toString().replace("-","")+filename.substring(filename.length() - 4);

        //调用图片上传的工具类
        ImageJson imageJson=new ImageJson();

        String imagePath= ImageUploadUtil.upload(file,newFilename);
        if(imagePath!=null)
        {
            imageJson.setCode(0);
            imageJson.setMsg("上传成功");
            Map<String,String> map=new HashMap<String,String>();
            map.put("src",imagePath);
            imageJson.setData(map);
        }
        else
        {
            imageJson.setCode(-1);
            imageJson.setMsg("上传失败,请稍后重试");
        }

        return imageJson;
    }

    @RequestMapping("tocommoditydetail")
    public String tocommoditydetail(int commodityId, Model model){
        Commodity commodity = commodityService.findCommodityById(commodityId);
        model.addAttribute("theCommodity",commodity);
        return "commoditydetail";
    }

    @ResponseBody
    @RequestMapping("findcommodities")
    public List<Commodity> findcommodities(){
        return commodityService.findcommodities();
    }

    @ResponseBody
    @RequestMapping("findCommodityByStatus")
    public Map<String,Object> findCommodityByStatus(char status)
    {
        Map<String,Object> json=new HashMap<>();
        List<Commodity> commodityList = commodityService.findCommodityByStatus(status);
        if(commodityList!=null)
        {
            json.put("code",0);
            json.put("msg","查询成功");
            json.put("count",commodityList.size());
            json.put("data",commodityList);
        }
        else
            json.put("code",500);
        return json;
    }

    @ResponseBody
    @RequestMapping("findmyCommodity")
    public Map<String,Object> findmyCommodity(HttpServletRequest request){
        Map<String,Object> json=new HashMap<>();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");
        int userId=user.getUserId();
        List<Commodity> commodityList = commodityService.findmyCommodity(userId);
        if(commodityList!=null)
        {
            json.put("code",0);
            json.put("msg","查询成功");
            json.put("count",commodityList.size());
            json.put("data",commodityList);
        }
        else
            json.put("code",500);

        return json;
    }

    @ResponseBody
    @RequestMapping("findSomecommodity")
    public List<Commodity> findSomecommodity(String keyword)
    {
        return commodityService.findSomecommodity(keyword);
    }

    @ResponseBody
    @RequestMapping("findmyFavorite")
    public Map<String,Object> findmyFavorite(HttpServletRequest request){
        Map<String,Object> json=new HashMap<>();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");
        int userId=user.getUserId();
        List<Commodity> commodityList = commodityService.findmyFavorite(userId);
        if(commodityList!=null)
        {
            json.put("code",0);
            json.put("msg","查询成功");
            json.put("count",commodityList.size());
            json.put("data",commodityList);
        }
        else
            json.put("code",500);

        return json;
    }

}
