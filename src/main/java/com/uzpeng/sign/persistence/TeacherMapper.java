package com.uzpeng.sign.persistence;

import com.uzpeng.sign.domain.TeacherDO;
import org.apache.ibatis.annotations.*;

/**
 * @author serverliu on 2018/4/10.
 */
public interface TeacherMapper {
    @Insert("INSERT INTO teacher(name) VALUES (#{teacher.name}")
    void addTeacher(@Param("teacher")TeacherDO teacherDO);

    @Update("UPDATE ON teacher set name=#{teacher.name},card_num=#{teacher.card_num},tel_number=#{teacher.tel_number}," +
            "office_hour=#{teacher.office_hour}, office_loc=#{teacher.office_loc},note=#{teacher.note},")
    void updateTeacher(@Param("teacher") TeacherDO teacherDO);

    @Select("SELECT * FROM teacher WHERE id=#{id}")
    TeacherDO getTeacher(@Param("id") int id);

    @Delete("DELETE FROM teacher where id=#{id}")
    void deleteTeacher(@Param("id") int id);
}
