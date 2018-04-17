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

    @SelectProvider(type = StudentProvider.class, method = "getIdByNum")
    List<Integer> getStudentIdByNum(@Param("list") List<String> num);

    @Select("SELECT student_num FROM student")
    List<String> getStudentNum();

    @SelectProvider(type = StudentProvider.class, method = "getStudentListById")
    List<StudentDO> getStudentListByStudentId(@Param("list") List<Integer> studentId);

    @Select("SELECT * FROM student WHERE student_num like #{num}")
    List<StudentDO> getStudentListByStudentNum(@Param("num") String studentNum);

}
