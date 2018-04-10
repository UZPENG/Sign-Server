package com.uzpeng.sign.persistence;

import com.uzpeng.sign.domain.UserDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author serverliu on 2018/3/29.
 */
public interface UserMapper {
    @Select("SELECT * FROM user WHERE id = #{id}")
    UserDO getUserInfo(@Param("id") int id);

    @Update("UPDATE user SET password = #{password} WHERE id=#{id}")
    void updatePassword(@Param("password") String password, @Param("id")int id);

    @Update("UPDATE user SET username = #{username} WHERE id=#{id}")
    void updateUserName(@Param("username") String username, @Param("id")int id);

    @Select("SELECT * FROM user WHERE email=#{email}")
    UserDO checkEmailValid(@Param("email") String email);

    @Insert("INSERT INTO user(name, password, register_time, email) VALUES(#{user.name}, #{user.password}," +
            " #{user.registerTime,jdbcType=TIMESTAMP}, #{user.email})")
    void addUser(@Param("user")UserDO userDO);

    @Select("SELECT password FROM user WHERE name=#{name}")
    String checkUserAndPassword(@Param("name")String name);

    @InsertProvider(type = UserProvider.class, method = "insertUserList")
    void insertUserList(@Param("list")List<UserDO> userDOList);
}
