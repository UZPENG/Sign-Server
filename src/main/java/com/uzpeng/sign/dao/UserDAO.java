package com.uzpeng.sign.dao;

import com.uzpeng.sign.domain.RoleDO;
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

    public Integer checkUserAndPassword(String name, String password){
        String storedPassword = userMapper.checkUserAndPassword(name);

        boolean isCorrect = CryptoUtil.match(password, storedPassword);
        if(isCorrect) {
            return userMapper.getIdByName(name);
        }else {
            return null;
        }
    }

    public void insertUserList(List<UserDO> userDOList){
        userMapper.insertUserList(userDOList);
    }

    public RoleDO getRole(int id){
        return userMapper.getRole(id);
    }

    public Integer getRoleIdByOpenId(String openId){
        return userMapper.getRoleIdByOpenId(openId);
    }

    public void updatePassword(Integer id, String newPassword){
        userMapper.updatePassword(id, CryptoUtil.encodePassword(newPassword));
    }
    public UserDO getUserByOpenId(String openId, String oldPassword){
        UserDO userDO = userMapper.getUserByOpenId(openId);
        if(CryptoUtil.match(oldPassword, userDO.getPassword())){
            return userDO;
        } else {
            return null;
        }
    }
}
