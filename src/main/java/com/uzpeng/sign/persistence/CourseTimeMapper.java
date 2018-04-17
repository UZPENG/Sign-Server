package com.uzpeng.sign.persistence;

import com.uzpeng.sign.domain.CourseTimeDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author serverliu on 2018/4/11.
 */
public interface CourseTimeMapper {
    @InsertProvider(type = CourseTimeProvider.class, method = "insertCourseTimeList")
    @Options(useGeneratedKeys = true)
    void addCourseTimeList(@Param("list")List<CourseTimeDO> c);

    @Select("SELECT * FROM course_time WHERE course_id=#{id}")
    List<CourseTimeDO> getCourseTimeByCourseId(@Param("id") int courseId);

    @Select("SELECT * FROM course_time WHERE id=#{id}")
    CourseTimeDO getCourseTimeById(@Param("id") Integer courseTimeId);

    @Delete("DELETE FROM course_time WHERE course_id=#{id} ")
    void deleteCourseTime(@Param("id") Integer id);
}
