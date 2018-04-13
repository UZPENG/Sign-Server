package com.uzpeng.sign.web.dto;

import java.util.List;

/**
 * @author serverliu on 2018/4/10.
 */
public class CourseDTO {
    private Integer teacherId;
    private String courseName;
    private String courseNum;
    private String semester;
    private Integer startWeek;
    private Integer endWeek;
    private List<CourseTimeDetail> time;

    public static class CourseTimeDetail{
        private Integer weekday;
        private Integer start;
        private Integer end;
        private String loc;

        public Integer getWeekday() {
            return weekday;
        }

        public void setWeekday(Integer weekday) {
            this.weekday = weekday;
        }

        public Integer getStart() {
            return start;
        }

        public void setStart(Integer start) {
            this.start = start;
        }

        public Integer getEnd() {
            return end;
        }

        public void setEnd(Integer end) {
            this.end = end;
        }

        public String getLoc() {
            return loc;
        }

        public void setLoc(String loc) {
            this.loc = loc;
        }
    }

    public Integer getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(Integer startWeek) {
        this.startWeek = startWeek;
    }

    public Integer getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(Integer endWeek) {
        this.endWeek = endWeek;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public List<CourseTimeDetail> getTime() {
        return time;
    }

    public void setTime(List<CourseTimeDetail> time) {
        this.time = time;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(String courseNum) {
        this.courseNum = courseNum;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
