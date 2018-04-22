package com.uzpeng.sign.persistence;

import com.uzpeng.sign.domain.SignDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author uzpeng on 2018/4/17.
 */
public interface SignMapper {
    @InsertProvider(type = SignProvider.class, method = "insertAll")
    @Options(useGeneratedKeys = true)
    void insertSignList(@Param("list")List<SignDO> signDOS);

    @Select("SELECT * FROM course_sign WHERE course_id=#{courseId} ORDER BY week")
    List<SignDO> getSign(@Param("courseId") Integer courseId);

    @Select("SELECT * FROM course_sign WHERE id=#{id} ORDER BY week")
    SignDO getSignById(@Param("id") Integer signId);

    @Select("SELECT * FROM course_sign WHERE course_id=#{courseId} AND " +
            "course_time_id = #{courseTimeId} AND week=#{week}")
    SignDO getSignByTimeAndCourse(@Param("courseId") Integer courseId,
                                  @Param("courseTimeId")Integer courseTimeId,
                                  @Param("week")Integer week);

    @Select("SELECT id FROM course_sign WHERE course_id=#{course_id}")
    List<Integer> getSignIdByCourseId(@Param("course_id") Integer courId);

    @Select("SELECT COUNT(*) FROM course_sign WHERE course_time_id=#{time} AND week=#{week}")
    Integer checkExistSign(@Param("time") Integer timeId, @Param("week")Integer week);

    @Delete("DELETE FROM course_sign WHERE course_id=#{id}")
    void deleteSignByCourseId(@Param("id") Integer courseId);

    @Select("SELECT state FROM course_sign WHERE id=#{id}")
    Integer getStateById(@Param("id") Integer signId);

    @Select("UPDATE course_sign SET state=#{state} WHERE id=#{id}")
    void updateStateById(@Param("id") Integer signId, @Param("state") Integer state);
}
