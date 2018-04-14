package com.uzpeng.sign.util;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * @author serverliu on 2018/4/13.
 */
public class DateUtil {

    public static boolean isHistoryCourse(Integer semester){
        int year = semester / 10;
        int num = semester % 10;

        int month = num == 1 ? 3 : 8;
        LocalDate courseTime = LocalDate.of(year+1, month, 1);

        return  LocalDate.now().isAfter(courseTime);
    }

    public static String semesterIdToName(Integer semesterId){
        MessageFormat messageFormat = new MessageFormat("{0}-{1} 第{2}学期");

        int year = semesterId / 10;
        int num = semesterId % 10;

        String startYear= String.valueOf(year);
        String endYear= String.valueOf(year+1);
        String semesterNum = num == 1 ? "一" : "二";

        messageFormat.format(new Object[]{startYear, endYear, semesterNum});

        return messageFormat.toString();
    }

    public static Integer semesterNameToId(String name){
        String pattern = "[1-9][0-9]+3-[1-9][0-9]+3\\w第[1,2]学期";

        //todo
        return null;
    }

}
