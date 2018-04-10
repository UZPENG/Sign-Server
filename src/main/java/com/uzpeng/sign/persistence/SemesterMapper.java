package com.uzpeng.sign.persistence;

import com.uzpeng.sign.domain.SemesterDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author serverliu on 2018/4/10.
 */
public interface SemesterMapper {
    @Insert("INSERT INTO semester VALUES (#{semester.id}, #{semester.startTime}, #{semester.endTime})")
    void addSemester(@Param("semester") SemesterDO semesterDO);

    @Select("SELECT * FROM semester")
    SemesterDO getSemester();
}
