package com.uzpeng.sign.dao;

import com.uzpeng.sign.dao.bo.CourseBO;
import com.uzpeng.sign.dao.bo.CourseListBO;
import com.uzpeng.sign.domain.CourseDO;
import com.uzpeng.sign.domain.CourseTimeDO;
import com.uzpeng.sign.persistence.CourseMapper;
import com.uzpeng.sign.persistence.CourseTimeMapper;
import com.uzpeng.sign.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author serverliu on 2018/4/10.
 */
@Repository
public class CourseDAO {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseTimeMapper courseTimeMapper;

    public int addCourse(CourseDO courseDO){
        courseMapper.insertCourse(courseDO);

        return courseDO.getId();
    }

    public CourseListBO getCourseList(Integer teacherId){
        List<CourseBO> currentCourseList = new ArrayList<>();
        List<CourseBO> historyCourseList = new ArrayList<>();

        List<CourseDO> courseDOList = courseMapper.getCourseByTeacherId(teacherId);
        for (CourseDO courseDO :
                courseDOList) {
            List<CourseTimeDO> courseTimeDOList = courseTimeMapper.getCourseTimeByCourseId(courseDO.getId());

            CourseBO courseBO = new CourseBO();
            courseBO.setCourseId(courseDO.getId());
            courseBO.setCourseName(courseDO.getName());
            courseBO.setCourseNum(courseDO.getCourseNum());
            courseBO.setSemester(String.valueOf(courseDO.getSemester()));
            courseBO.setStartWeek(courseDO.getStartWeek());
            courseBO.setEndWeek(courseDO.getEndWeek());
            courseBO.setTime(new ArrayList<>());
            courseBO.setTeacherId(courseDO.getTeacherId());

            for (CourseTimeDO courseTimeDO :
                    courseTimeDOList) {
                CourseBO.CourseTimeDetail timeDetail = new CourseBO.CourseTimeDetail();

                timeDetail.setStart(courseTimeDO.getCourseSectionStart());
                timeDetail.setEnd(courseTimeDO.getCourseSectionEnd());
                timeDetail.setWeekday(courseTimeDO.getCourseWeekday());
                timeDetail.setLoc(courseTimeDO.getLoc());

                courseBO.getTime().add(timeDetail);
            }
            if(DateUtil.isHistoryCourse(courseDO.getSemester())) {
                historyCourseList.add(courseBO);
            } else {
                currentCourseList.add(courseBO);
            }
        }

        CourseListBO courseVO = new CourseListBO();
        courseVO.setCurrentCourseList(currentCourseList);
        courseVO.setHistoryCourseList(historyCourseList);
        return courseVO;
    }
}
