package gd.mall.controller;

import gd.mall.po.Favorite;
import gd.mall.po.User;
import gd.mall.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @ResponseBody
    @RequestMapping("collect")
    public Map<String,Object> collect(Favorite favorite)
    {
        Map<String,Object> json=new HashMap<>();
        int rows = favoriteService.addFavorite(favorite);
        if(rows==1)
            json.put("status",1);
        else
            json.put("status",-1);
        return json;
    }

    @ResponseBody
    @RequestMapping("favoriteInfo")
    public Map<String,Object> favoriteInfo(int userId,int commodityId){
        Map<String,Object> json=new HashMap<>();
        Favorite f = favoriteService.checkFavorite(userId,commodityId);
        if(f!=null)
            json.put("status",1);
        else
            json.put("status",-1);
        return json;
    }

    @ResponseBody
    @RequestMapping("cancelFavorite")
    public Map<String,Object> cancelFavorite(int userId,int commodityId){
        Map<String,Object> json=new HashMap<>();
        int rows = favoriteService.cancelFavorite(userId, commodityId);
        if(rows==1)
            json.put("status",1);
        else
            json.put("status",-1);
        return json;
    }
}
