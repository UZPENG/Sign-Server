package com.uzpeng.sign.bo;

import java.util.List;

/**
 * @author uzpeng on 2018/4/21.
 */
public class DownloadSignRecordBOList {
    private List<List<StudentSignRecordBO>> downloadSignRecordLists;

    public List<List<StudentSignRecordBO>> getDownloadSignRecordLists() {
        return downloadSignRecordLists;
    }

    public void setDownloadSignRecordLists(List<List<StudentSignRecordBO>> downloadSignRecordLists) {
        this.downloadSignRecordLists = downloadSignRecordLists;
    }

}
