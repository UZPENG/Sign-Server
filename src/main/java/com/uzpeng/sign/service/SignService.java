package com.uzpeng.sign.service;

import com.uzpeng.sign.bo.DownloadSignRecordBOList;
import com.uzpeng.sign.bo.SignRecordListBO;
import com.uzpeng.sign.bo.SignRecordTimeListBO;
import com.uzpeng.sign.bo.StudentSignRecordBO;
import com.uzpeng.sign.config.StatusConfig;
import com.uzpeng.sign.dao.CourseDAO;
import com.uzpeng.sign.dao.SignDAO;
import com.uzpeng.sign.domain.SignRecordDO;
import com.uzpeng.sign.exception.DuplicateDataException;
import com.uzpeng.sign.util.StatisticsTool;
import com.uzpeng.sign.web.dto.CreateSignRecordDTO;
import com.uzpeng.sign.web.dto.SignRecordDTO;
import com.uzpeng.sign.web.dto.UpdateSignRecordDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author uzpeng on 2018/4/16.
 */
@Service
public class SignService {
    private final Logger logger = LoggerFactory.getLogger(SignService.class);
    @Autowired
    private SignDAO signDAO;

    public void createSign(CreateSignRecordDTO signRecordDTO) throws DuplicateDataException {
        signDAO.createSign(signRecordDTO.getCourseTimeId(), signRecordDTO.getWeek());
    }

    public SignRecordListBO getSignRecordByParam(Integer courseId, Integer time, Integer week, String num){
        return signDAO.getSignRecordByTime(courseId, time, week, num);
    }

    public SignRecordTimeListBO getSignWeek(Integer courseId){
        return signDAO.getRecordWeek(courseId);
    }

    public void updateSignState(UpdateSignRecordDTO updateSignRecordDTO){
        signDAO.updateSignRecordStatus(Collections.singletonList(updateSignRecordDTO));
    }

    public void doingSign(SignRecordDTO signRecordDTO, int studentId){
        signDAO.sign(signRecordDTO, studentId);
    }

    public Integer getSignState(Integer signId){
        return signDAO.getSignState(signId);
    }

    public void updateSignState(Integer signId, Integer state){
        signDAO.updateSignState(signId, state);
    }

    public void evaluateSignResult(Integer signId){
        CopyOnWriteArrayList<SignRecordDO> signRecordDOs = new CopyOnWriteArrayList<>();
        signRecordDOs.addAll(signDAO.getSignRecordBySignId(signId));

        if(signRecordDOs.size() == 0){
            return;
        }

        StatisticsTool.pickAbnormalPoint(signRecordDOs);

        List<UpdateSignRecordDTO> newDataList = new ArrayList<>();
        for (SignRecordDO oldData :
             signRecordDOs) {
            UpdateSignRecordDTO newData = new UpdateSignRecordDTO();
            logger.info("state: "+oldData.getState()+",id: "+oldData.getId());
            newData.setState(oldData.getState());
            newData.setId(oldData.getId());

            newDataList.add(newData);
        }
        signDAO.updateSignRecordStatus(newDataList);
    }


    public byte[] downloadSignAllRecord(Integer courseId) throws IOException{
        DownloadSignRecordBOList downloadSignRecordBOList = signDAO.getAllSignRecord(courseId);
        return writeToExcel(downloadSignRecordBOList.getDownloadSignRecordLists());
    }

    private byte[] writeToExcel(List<List<StudentSignRecordBO>> list) throws IOException{
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        int[] columnWidth=new int[]{255*15,255*20,255*10,255*15};
        String[] studentInfoTitle = {"学号","班级","姓名","课程"};
        sheet.setColumnWidth(0,columnWidth[0]);
        sheet.setColumnWidth(1,columnWidth[1]);
        sheet.setColumnWidth(2,columnWidth[2]);
        sheet.setColumnWidth(3,columnWidth[3]);
        Row titleRow = sheet.createRow(0);
        for (int i = 0; i < studentInfoTitle.length; i++) {
            Cell cell = titleRow.createCell(i);
            cell.setCellValue(studentInfoTitle[i]);
        }
        List<String> timeTitleList = new ArrayList<>();
        List<StudentSignRecordBO> tmpList = list.get(0);
        for (StudentSignRecordBO tmp :
                tmpList) {
            timeTitleList.add(tmp.getTime());
        }

        for (int i = 0; i < tmpList.size(); i++) {
            titleRow.createCell(studentInfoTitle.length+i).setCellValue(timeTitleList.get(i));
            sheet.setColumnWidth(studentInfoTitle.length+i,255*30);
        }

        for (int i = 0; i < list.size(); i++) {
            List<StudentSignRecordBO> recordBOs = list.get(i);
            Row currentRow = sheet.createRow(i+1);

            currentRow.createCell(0).setCellValue(recordBOs.get(0).getStudentNum());
            currentRow.createCell(1).setCellValue(recordBOs.get(0).getClassInfo());
            currentRow.createCell(2).setCellValue(recordBOs.get(0).getStudentName());
            currentRow.createCell(3).setCellValue(recordBOs.get(0).getCourse());

            for (int j = 0; j < recordBOs.size(); j++) {
                StudentSignRecordBO record = recordBOs.get(j);
                String state = record.getState().equals(StatusConfig.RECORD_SUCCESS) ? "成功" : "失败";
                currentRow.createCell(studentInfoTitle.length+j).setCellValue(state);
            }
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);

        return outputStream.toByteArray();
    }

}
