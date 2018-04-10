package com.uzpeng.sign.dao;

import com.uzpeng.sign.domain.CourseDO;
import com.uzpeng.sign.persistence.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author serverliu on 2018/4/10.
 */
@Repository
public class CourseDAO {
    @Autowired
    private CourseMapper courseMapper;

    public void addCourse(CourseDO courseDO){
        courseMapper.insertCourse(courseDO);
    }

}
