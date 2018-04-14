package com.uzpeng.sign.persistence;

import com.uzpeng.sign.domain.CourseDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author serverliu on 2018/4/10.
 */
public interface CourseMapper {
    @Insert("INSERT INTO course(course_num, name, semester, teacher_id, start_week, end_week)" +
            "VALUES(#{course.course_num}, #{course.name},#{course.semester},#{course.teacher_id}," +
            "#{course.start_week}, #{course.end_week})")
    @Options(useGeneratedKeys = true, keyProperty = "course.id")
    void insertCourse(@Param("course")CourseDO courseDO);

    @Select("SELECT * FROM course WHERE teacher_id=#{id}")
    List<CourseDO> getCourseByTeacherId(@Param("id") int teacherId);

    @Update("UPDATE ON course SET course_num=#{course.course_num}, name=#{course.name},semester=#{course.semester}," +
            "teacher_id=#{course.teacher_id},start_week=#{course.start_week},end_week#{course.end_week}")
    void updateCourse(@Param("course")CourseDO courseDO);

    @Delete("DELETE FROM course WHERE id = #{id}")
    void deleteCourse(@Param("id")int id);

    @Select("SELECT * FROM course WHERE name like='%#{courseName}%'")
    List<CourseDO> getCourseCourseName(@Param("id") String courseName);
}
