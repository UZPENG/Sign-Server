package com.uzpeng.sign.persistence;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @author serverliu on 2018/4/11.
 */
public class CourseTimeProvider {

    public String insertCourseTimeList(Map map){
        //todo 类型检测
        List list = (List)map.get("list");

        String statement =  "INSERT INTO course_time(course_id, course_weekday, course_section_start," +
                "course_section_end, loc) VALUES";
        MessageFormat messageFormat = new MessageFormat("(#'{'list[{0}].courseId}, #'{'list[{0}].courseWeekday}," +
                " #'{'list[{0}].courseSectionStart}, #'{'list[{0}].courseSectionEnd}, #'{'list[{0}].loc})");

        StringBuilder statementBuilder = new StringBuilder();
        statementBuilder.append(statement);
        for (int i = 0; i < list.size(); i++) {
            statementBuilder.append(messageFormat.format(new Object[]{i}));
            if(i < list.size() -1){
                statementBuilder.append(",");
            }
        }

        return statementBuilder.toString();
    }
}
