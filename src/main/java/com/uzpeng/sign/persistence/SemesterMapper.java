package com.uzpeng.sign.persistence;

import com.uzpeng.sign.domain.SemesterDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author serverliu on 2018/4/10.
 */
public interface SemesterMapper {
    @Insert("INSERT INTO semester VALUES (#{semester.id}, #{semester.name}, #{semester.start_time}," +
            " #{semester.end_time})")
    void addSemester(@Param("semester") SemesterDO semesterDO);

    @Select("SELECT * FROM semester")
    List<SemesterDO> getSemester();

    @Select("SELECT * FROM semester WHERE id=#{id}")
    SemesterDO getSemesterById(@Param("id") Integer id);

    @Update("Update semester SET name=#{semester.name},start_time=#{semester.start_time}, end_time=#{semester.end_time} " +
            "WHERE id=#{semester.id}")
    void updateSemester(@Param("semester") SemesterDO semesterDO);

    @Delete("DELETE FROM semester WHERE id=#{id}")
    void deleteSemester(@Param("id") Integer semesterId);
}
