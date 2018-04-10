package com.uzpeng.sign.util;

import com.uzpeng.sign.domain.TeacherDO;
import com.uzpeng.sign.domain.UserDO;
import com.uzpeng.sign.web.dto.RegisterDTO;
import com.uzpeng.sign.web.dto.TeacherDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

/**
 * @author serverliu on 2018/4/6.
 */
public class ObjectTranslateUtil {

    public static UserDO registerDTOToUserDO(RegisterDTO registerDTO){
        UserDO userDO = new UserDO();

        userDO.setName(registerDTO.getUsername());
        userDO.setPassword(CryptoUtil.encodePassword(registerDTO.getPassword()));
        userDO.setEmail(registerDTO.getEmail());
        userDO.setRegisterTime(LocalDateTime.now());

        return userDO;
    }

    public static TeacherDO teacherDTOToTeacherDO(TeacherDTO teacherDTO){
        TeacherDO teacherDO = new TeacherDO();
        teacherDO.setCardNum(teacherDTO.getNum());
        teacherDO.setName(teacherDTO.getName());

        return teacherDO;
    }


}
