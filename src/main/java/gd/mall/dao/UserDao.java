package gd.mall.dao;


import gd.mall.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {

    public User findUser(@Param("userCode") String userCode, @Param("userPwd") String userPwd);

    public int insertUser(User user);

    public List<User> findAllUser();

    public User findUserByuserCode(String userCode);

    public User findUserByuserNickname(String userNickname);

    public User findUserByuserId(int userId);

    public int deleteUser(int userId);

    public int updateUser(User user);
}
