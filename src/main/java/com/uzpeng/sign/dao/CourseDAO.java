package com.uzpeng.sign.dao;

import com.uzpeng.sign.dao.bo.CourseBO;
import com.uzpeng.sign.dao.bo.CourseListBO;
import com.uzpeng.sign.dao.bo.SemesterBO;
import com.uzpeng.sign.domain.CourseDO;
import com.uzpeng.sign.domain.CourseTimeDO;
import com.uzpeng.sign.persistence.CourseMapper;
import com.uzpeng.sign.util.DateUtil;
import com.uzpeng.sign.util.ObjectTranslateUtil;
import com.uzpeng.sign.web.dto.CourseDTO;
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
    private CourseTimeDAO courseTimeDAO;
    @Autowired
    private SemesterDAO semesterDAO;

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
            List<CourseTimeDO> courseTimeDOList = courseTimeDAO.getCourseTimeByCourseId(courseDO.getId());
            SemesterBO semesterBO = semesterDAO.getSemesterById(courseDO.getSemester());

            CourseBO courseBO = ObjectTranslateUtil.courseDOToCourseBO(courseDO, courseTimeDOList, semesterBO);

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

    public CourseBO getCourseById(Integer id){
        CourseDO  courseDO = courseMapper.getCourseById(id);
        List<CourseTimeDO> courseTimeDOList = courseTimeDAO.getCourseTimeByCourseId(courseDO.getId());
        SemesterBO semesterBO = semesterDAO.getSemesterById(courseDO.getSemester());

        return ObjectTranslateUtil.courseDOToCourseBO(courseDO, courseTimeDOList, semesterBO);
    }

    public CourseListBO getCourseByName(String name){
        List<CourseDO> courseDOList = courseMapper.getCourseByName(name);

        List<CourseBO> currentCourseList = new ArrayList<>();
        List<CourseBO> historyCourseList = new ArrayList<>();

        for (CourseDO courseDO :
                courseDOList) {
            List<CourseTimeDO> courseTimeDOList = courseTimeDAO.getCourseTimeByCourseId(courseDO.getId());
            SemesterBO semesterBO = semesterDAO.getSemesterById(courseDO.getSemester());

            CourseBO courseBO = ObjectTranslateUtil.courseDOToCourseBO(courseDO, courseTimeDOList, semesterBO);

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

    public void deleteCourseById(Integer id){
        courseMapper.deleteCourse(id);
    }

    public void updateCourse(CourseDTO courseDTO){
        courseMapper.updateCourse(ObjectTranslateUtil.courseDTOToCourseDO(courseDTO));
    }
}
