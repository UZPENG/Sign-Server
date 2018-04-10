package com.uzpeng.sign.service;

import com.uzpeng.sign.dao.StudentDAO;
import com.uzpeng.sign.domain.StudentDO;
import com.uzpeng.sign.excpetion.InvalidFileException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author serverliu on 2018/4/7.
 */
@Service
public class StudentService {
    private static final String XLS_FORMAT = "xls";
    private static final String XLSX_FORMAT = "xlsx";
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    @Autowired
    private StudentDAO studentDAO;

    public boolean insertStudentsByFile(InputStream excelFileStream, String fiLeName){
        List<StudentDO> studentDOList = parseExcelFile(excelFileStream, fiLeName);
        if(studentDOList !=null) {
            studentDAO.insertStudents(studentDOList);
            return true;
        } else {
            return false;
        }
    }

    private List<StudentDO> parseExcelFile(InputStream excelFileStream, String filename) throws InvalidFileException{
        logger.info("filename is "+filename);
        Workbook workbook;
        try{
            if(filename.endsWith(XLS_FORMAT)){
                workbook = new HSSFWorkbook(excelFileStream);
            } else if (filename.endsWith(XLSX_FORMAT)){
                workbook = new XSSFWorkbook(excelFileStream);
            } else {
                throw new InvalidFileException();
            }
            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getLastRowNum();
            List<StudentDO> studentDOList = new ArrayList<>();
            for (int i = 1; i < rowCount; i++) {
                //todo 需要添加单元格判断
                Row currentRow = sheet.getRow(i);
                if(currentRow == null){
                    break;
                }
                Cell studentNumCell = currentRow.getCell(0);
                Cell studentNameCell = currentRow.getCell(1);

                if (studentNumCell != null && studentNameCell != null){
                    String studentNum = String.valueOf((int)studentNumCell.getNumericCellValue());
                    String studentName = studentNameCell.getStringCellValue();
                    StudentDO tmpStudentDO = new StudentDO();
                    tmpStudentDO.setName(studentName);
                    tmpStudentDO.setNum(studentNum);
                    studentDOList.add(tmpStudentDO);
                } else {
                    break;
                }
            }
            return studentDOList;
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}