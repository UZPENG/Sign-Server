package com.uzpeng.sign.dao;

import com.uzpeng.sign.bo.CourseBO;
import com.uzpeng.sign.bo.CourseListBO;
import com.uzpeng.sign.bo.SemesterBO;
import com.uzpeng.sign.domain.CourseDO;
import com.uzpeng.sign.domain.CourseTimeDO;
import com.uzpeng.sign.persistence.CourseMapper;
import com.uzpeng.sign.util.DateUtil;
import com.uzpeng.sign.util.ObjectTranslateUtil;
import com.uzpeng.sign.web.dto.CourseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author serverliu on 2018/4/10.
 */
@Repository
public class CourseDAO {
    private static final Logger logger = LoggerFactory.getLogger(CourseDAO.class);

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseTimeDAO courseTimeDAO;
    @Autowired
    private SemesterDAO semesterDAO;
    @Autowired
    private SelectiveCourseDAO selectiveCourseDAO;
    @Autowired
    private SignDAO signDAO;

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
            SemesterBO semesterBO = semesterDAO.getSemesterById(courseDO.getSemester(), courseDO.getTeacherId());

            int studentCount = selectiveCourseDAO.getStudentCount(courseDO.getId());
            CourseBO courseBO = ObjectTranslateUtil.courseDOToCourseBO(courseDO, courseTimeDOList, semesterBO);
            courseBO.setStudentAmount(studentCount);

            LocalDateTime endTime = LocalDateTime.parse(semesterBO.getEndTime());
            if(LocalDateTime.now().isAfter(endTime)) {
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
        int studentCount = selectiveCourseDAO.getStudentCount(id);
        List<CourseTimeDO> courseTimeDOList = courseTimeDAO.getCourseTimeByCourseId(courseDO.getId());
        SemesterBO semesterBO = semesterDAO.getSemesterById(courseDO.getSemester(), courseDO.getTeacherId());

        CourseBO courseBO = ObjectTranslateUtil.courseDOToCourseBO(courseDO, courseTimeDOList, semesterBO);
        courseBO.setStudentAmount(studentCount);
        return courseBO;
    }

    public CourseListBO getCourseByName(String name){
        String builder = "%" +
                name +
                "%";
        List<CourseDO> courseDOList = courseMapper.getCourseByName(builder);

        List<CourseBO> currentCourseList = new ArrayList<>();
        List<CourseBO> historyCourseList = new ArrayList<>();

        for (CourseDO courseDO :
                courseDOList) {
            List<CourseTimeDO> courseTimeDOList = courseTimeDAO.getCourseTimeByCourseId(courseDO.getId());
            SemesterBO semesterBO = semesterDAO.getSemesterById(courseDO.getSemester(), courseDO.getTeacherId());

            int studentCount = selectiveCourseDAO.getStudentCount(courseDO.getId());
            CourseBO courseBO = ObjectTranslateUtil.courseDOToCourseBO(courseDO, courseTimeDOList, semesterBO);
            courseBO.setStudentAmount(studentCount);

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
        selectiveCourseDAO.removeCourse(id);
        signDAO.deleteSignRecord(id);
        courseTimeDAO.deleteCourseTime(id);
        courseMapper.deleteCourse(id);
    }

    public void updateCourse(CourseDTO courseDTO){
        logger.info(courseDTO.getSemester());
        courseMapper.updateCourse(ObjectTranslateUtil.courseDTOToCourseDO(courseDTO));
    }

    public void deleteCourseBySemester(Integer semesterId){
        List<CourseDO> courseDOs = courseMapper.getCourseBySemesterId(semesterId);
        for (CourseDO courseDO :
                courseDOs) {
         deleteCourseById(courseDO.getId());
        }
    }
}
