package com.uzpeng.sign.dao;

import com.uzpeng.sign.domain.UserDO;
import com.uzpeng.sign.persistence.UserMapper;
import com.uzpeng.sign.util.CryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author serverliu on 2018/3/29.
 */
@Repository
public class UserDAO {
    @Autowired
    private UserMapper userMapper;

    public UserDO getUserInfo(int id){
        return  userMapper.getUserInfo(id);
    }

    public boolean checkEmailValid(String email){
        return userMapper.checkEmailValid(email) == null;
    }

    public void insertUser(UserDO userDO){
         userMapper.addUser(userDO);
    }

    public void updateUserName(String username, int id){
        userMapper.updateUserName(username, id);
    }

    public void updatePassword(String newPassword, int id){
        userMapper.updatePassword(newPassword, id);
    }

    public boolean checkUserAndPassword(String name, String password){
        String storedPassword = userMapper.checkUserAndPassword(name);

        return CryptoUtil.match(password, storedPassword);
    }

    public void insertUserList(List<UserDO> userDOList){
        userMapper.insertUserList(userDOList);
    }
}
