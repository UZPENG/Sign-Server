package com.uzpeng.sign.persistence;

import com.uzpeng.sign.domain.CourseDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author serverliu on 2018/4/10.
 */
public interface CourseMapper {
    @Insert("INSERT INTO course(course_num, name, semester, teacher_id, start_week, end_week)" +
            "VALUES(#{course.courseNum}, #{course.name},#{course.semester},#{course.teacherId}," +
            "#{course.startWeek}, #{course.endWeek})")
    @Options(useGeneratedKeys = true, keyProperty = "course.id")
    void insertCourse(@Param("course")CourseDO courseDO);

    @Select("SELECT * FROM course WHERE teacher_id=#[id]")
    List<CourseDO> getCourseByTeacherId(@Param("id") int teacherId);

    @Update("UPDATE ON course SET course_num=#{course.courseNum}, name=#{course.name},semester=#{course.semester}," +
            "teacher_id=#{course.teacherId},start_week=#{course.startWeek},end_week#{course.endWeek}")
    void updateCourse(@Param("course")CourseDO courseDO);

    @Delete("DELETE FROM course WHERE id = #{id}")
    void deleteCourse(@Param("id")int id);

}
