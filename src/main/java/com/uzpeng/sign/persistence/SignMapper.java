package com.uzpeng.sign.persistence;

import com.uzpeng.sign.domain.SignDO;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author uzpeng on 2018/4/17.
 */
public interface SignMapper {
    @InsertProvider(type = SignProvider.class, method = "insertAll")
    @Options(useGeneratedKeys = true)
    void insertSignList(@Param("list")List<SignDO> signDOS);

    @Select("SELECT * FROM course_sign WHERE course_id=#{courseId} ORDER BY week")
    List<SignDO> getSignRecord(@Param("courseId") Integer courseId);

    @Select("SELECT * FROM course_sign WHERE course_id=#{courseId} AND " +
            "course_time_id = #{courseTimeId} AND week=#{week}")
    List<SignDO> getSignRecordByTimeAndCourse(@Param("courseId") Integer courseId,
                                              @Param("courseTimeId")Integer courseTimeId,
                                              @Param("week")Integer week);

    @Select("SELECT id FROM course_sign WHERE course_id=#{course_id}")
    List<Integer> getSignIdByCourseId(@Param("course_id") Integer courId);
}
