package com.uzpeng.sign.persistence;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @author serverliu on 2018/4/7.
 */
public class StudentProvider {
    public String insertAll(Map list) throws Exception{
        List students;
        if( !(list.get("list") instanceof  List) ){
           throw new Exception();
        }

        students = (List) list.get("list");
        StringBuilder statement = new StringBuilder();
        statement.append("INSERT INTO student(student_num, name) VALUES");
        MessageFormat messageFormat = new MessageFormat("(#'{'list[{0}].num}, #'{'list[{0}].name})");
        for (int i = 0; i < students.size(); i++) {
            statement.append(messageFormat.format(new Object[]{i}));
            if(i < students.size() - 1){
                statement.append(",");
            }
        }
        return statement.toString();
    }
}
