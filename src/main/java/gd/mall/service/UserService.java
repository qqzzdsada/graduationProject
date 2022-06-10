package gd.mall.service;

import gd.mall.po.User;

import java.util.List;

public interface UserService {

    public User findUser(String userCode, String userPwd);

    public int insertUser(User user);

    public List<User> findAllUser();

    public User findUserByuserCode(String userCode);

    public User findUserByuserNickname(String userNickname);

    public User findUserByuserId(int userId);

    public int deleteUser(int userId);

    public int updateUser(User user);
}
