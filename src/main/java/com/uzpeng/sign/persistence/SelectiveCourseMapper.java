package com.uzpeng.sign.persistence;

import com.uzpeng.sign.domain.SelectiveCourseDO;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author serverliu on 2018/4/11.
 */
public interface SelectiveCourseMapper {
    @InsertProvider(type =SelectiveCourseProvider.class, method = "addSelectiveCourseList")
    void addSelectiveCourseList(@Param("list")List<SelectiveCourseDO> list);
}
