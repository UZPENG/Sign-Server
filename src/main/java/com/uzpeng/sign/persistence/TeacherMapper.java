package com.uzpeng.sign.persistence;

import com.uzpeng.sign.domain.TeacherDO;
import org.apache.ibatis.annotations.*;

/**
 * @author serverliu on 2018/4/10.
 */
public interface TeacherMapper {
    @Insert("INSERT INTO teacher(name) VALUES (#{teacher.name}")
    void addTeacher(@Param("teacher")TeacherDO teacherDO);

    @Update("UPDATE ON teacher set name=#{teacher.name},card_num=#{teacher.cardNum},tel_number=#{teacher.telNumber}," +
            "office_hour=#{teacher.officeHour}, office_loc=#{teacher.officeLoc},note=#{teacher.note},")
    void updateTeacher(@Param("teacher") TeacherDO teacherDO);

    @Select("SELECT * FROM teacher WHERE id=#{id}")
    TeacherDO getTeacher(@Param("id") int id);

    @Delete("DELETE FROM teacher where id=#{id}")
    void deleteTeacher(@Param("id") int id);
}
