package com.uzpeng.sign.persistence;

import com.uzpeng.sign.domain.StudentDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author serverliu on 2018/4/7.
 */
public interface StudentMapper {
    @InsertProvider(type = StudentProvider.class, method = "insertAll")
    @Options(useGeneratedKeys=true)
    void insertStudentList(@Param("list")List<StudentDO> students);

    @Insert("INSERT INTO student(name, student_num) VALUES(#{student.name}, #{student.num})")
    void insertStudent(@Param("student") StudentDO studentDO);

    @Select("SELECT * FROM  student WHERE id=#{id}")
    StudentDO getStudent(@Param("id") int id);
}
