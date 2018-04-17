package com.uzpeng.sign.persistence;

import com.uzpeng.sign.domain.SemesterDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author serverliu on 2018/4/10.
 */
public interface SemesterMapper {
    @Insert("INSERT INTO semester(teacher_id,name,start_time,end_time) VALUES (#{semester.teacher_id}, #{semester.name}, #{semester.start_time}," +
            " #{semester.end_time})")
    void addSemester(@Param("semester") SemesterDO semesterDO);

    @Select("SELECT * FROM semester WHERE teacher_id=#{teacher_id}")
    List<SemesterDO> getSemester(@Param("teacher_id") Integer teacherId);

    @Select("SELECT * FROM semester WHERE id=#{id} AND teacher_id=#{teacher_id}")
    SemesterDO getSemesterById(@Param("id") Integer id, @Param("teacher_id") Integer teacherId);

    @Update("Update semester SET name=#{semester.name}, start_time=#{semester.start_time}, end_time=#{semester.end_time} " +
            "WHERE id=#{semester.id} AND teacher_id=#{semester.teacher_id}")
    void updateSemester(@Param("semester") SemesterDO semesterDO);

    @Delete("DELETE FROM semester WHERE id=#{id}")
    void deleteSemester(@Param("id") Integer semesterId, @Param("teacher_id") Integer teacherId);
}
