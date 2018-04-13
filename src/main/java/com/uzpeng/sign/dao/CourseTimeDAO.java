package com.uzpeng.sign.dao;

import com.uzpeng.sign.domain.CourseTimeDO;
import com.uzpeng.sign.persistence.CourseTimeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author serverliu on 2018/4/11.
 */
@Repository
public class CourseTimeDAO {
    @Autowired
    private CourseTimeMapper mapper;

    public void addCourseTimeList(List<CourseTimeDO> courseTimeDOList){
        mapper.addCourseTimeList(courseTimeDOList);
    }
}
