package com.uzpeng.sign.bo;

import java.util.List;

/**
 * @author serverliu on 2018/4/12.
 */
public class CourseListBO {
    private List<CourseBO> currentCourseList;
    private List<CourseBO> historyCourseList;

    public List<CourseBO> getHistoryCourseList() {
        return historyCourseList;
    }

    public void setHistoryCourseList(List<CourseBO> historyCourseList) {
        this.historyCourseList = historyCourseList;
    }

    public List<CourseBO> getCurrentCourseList() {
        return currentCourseList;
    }

    public void setCurrentCourseList(List<CourseBO> currentCourseList) {
        this.currentCourseList = currentCourseList;
    }
}
