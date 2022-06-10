package gd.mall.service.Impl;

import gd.mall.dao.UserDao;
import gd.mall.po.User;
import gd.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findUser(String userCode, String userPwd) {
        return userDao.findUser(userCode,userPwd);
    }

    @Override
    public int insertUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public List<User> findAllUser() {
        return userDao.findAllUser();
    }

    @Override
    public User findUserByuserCode(String userCode) {
        return userDao.findUserByuserCode(userCode);
    }

    @Override
    public User findUserByuserNickname(String userNickname) {
        return userDao.findUserByuserNickname(userNickname);
    }

    @Override
    public User findUserByuserId(int userId) {
        return userDao.findUserByuserId(userId);
    }

    @Override
    public int deleteUser(int userId) {
        return userDao.deleteUser(userId);
    }

    @Override
    public int updateUser(User user) {
        return userDao.updateUser(user);
    }


}
