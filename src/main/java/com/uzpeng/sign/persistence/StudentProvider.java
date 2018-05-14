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
        statement.append("INSERT INTO student(student_num, name, class_info) VALUES");
        MessageFormat messageFormat = new MessageFormat("(#'{'list[{0}].num}, #'{'list[{0}].name}," +
                " #'{'list[{0}].class_info})");
        for (int i = 0; i < students.size(); i++) {
            statement.append(messageFormat.format(new Object[]{i}));
            if(i < students.size() - 1){
                statement.append(",");
            }
        }
        return statement.toString();
    }

    public String getIdByNum(Map map){
        List list = (List)map.get("list");

        String statement =  "SELECT id FROM student WHERE student_num IN ";
        MessageFormat messageFormat = new MessageFormat("#'{'list[{0}]}");

        StringBuilder statementBuilder = new StringBuilder();
        statementBuilder.append(statement);
        statementBuilder.append("(");
        for (int i = 0; i < list.size(); i++) {
            statementBuilder.append(messageFormat.format(new Object[]{i}));
            if(i < list.size() -1){
                statementBuilder.append(",");
            }
        }
        statementBuilder.append(")");
        return statementBuilder.toString();
    }

    public String getStudentListById(Map map){
        List list = (List)map.get("list");

        String statement =  "SELECT * FROM student WHERE id IN ";
        MessageFormat messageFormat = new MessageFormat("#'{'list[{0}]}");

        StringBuilder statementBuilder = new StringBuilder();
        statementBuilder.append(statement);
        statementBuilder.append("(");
        for (int i = 0; i < list.size(); i++) {
            statementBuilder.append(messageFormat.format(new Object[]{i}));
            if(i < list.size() -1){
                statementBuilder.append(",");
            }
        }
        statementBuilder.append(")");
        return statementBuilder.toString();
    }

    public String getStudentsByNum(Map map){
        List list = (List)map.get("list");

        String statement =  "SELECT * FROM student WHERE student_num IN ";
        MessageFormat messageFormat = new MessageFormat("#'{'list[{0}]}");

        StringBuilder statementBuilder = new StringBuilder();
        statementBuilder.append(statement);
        statementBuilder.append("(");
        for (int i = 0; i < list.size(); i++) {
            statementBuilder.append(messageFormat.format(new Object[]{i}));
            if(i < list.size() -1){
                statementBuilder.append(",");
            }
        }
        statementBuilder.append(")");
        return statementBuilder.toString();
    }


}
