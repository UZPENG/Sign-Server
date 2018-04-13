package com.uzpeng.sign.dao;

import com.uzpeng.sign.dao.vo.CourseVO;
import com.uzpeng.sign.domain.CourseDO;
import com.uzpeng.sign.domain.CourseTimeDO;
import com.uzpeng.sign.persistence.CourseMapper;
import com.uzpeng.sign.persistence.CourseTimeMapper;
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
    private CourseTimeMapper courseTimeMapper;

    public int addCourse(CourseDO courseDO){
        courseMapper.insertCourse(courseDO);

        return courseDO.getId();
    }

    public CourseVO getCourseList(Integer teacherId){
        List<CourseDTO> courseDTOList = new ArrayList<>();

        List<CourseDO> courseDOList = courseMapper.getCourseByTeacherId(teacherId);
        for (CourseDO courseDO :
                courseDOList) {
            List<CourseTimeDO> courseTimeDOList = courseTimeMapper.getCourseTimeByCourseId(courseDO.getId());

            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setCourseName(courseDO.getName());
            courseDTO.setCourseNum(courseDO.getCourseNum());
            courseDTO.setSemester(String.valueOf(courseDO.getSemester()));
            courseDTO.setStartWeek(courseDO.getStartWeek());
            courseDTO.setEndWeek(courseDO.getEndWeek());

            for (CourseTimeDO courseTimeDO :
                    courseTimeDOList) {
                CourseDTO.CourseTimeDetail timeDetail = new CourseDTO.CourseTimeDetail();

                timeDetail.setStart(courseTimeDO.getCourseSectionStart());
                timeDetail.setEnd(courseTimeDO.getCourseSectionEnd());
                timeDetail.setWeekday(courseTimeDO.getCourseWeekday());
                timeDetail.setLoc(courseTimeDO.getLoc());

                courseDTO.getTime().add(timeDetail);
            }
            courseDTOList.add(courseDTO);
        }

        CourseVO courseVO = new CourseVO();
        courseVO.setCourseList(courseDTOList);
        return courseVO;
    }
}
