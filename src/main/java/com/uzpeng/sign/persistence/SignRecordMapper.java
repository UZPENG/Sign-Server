package com.uzpeng.sign.persistence;

import com.uzpeng.sign.domain.SignRecordDO;
import com.uzpeng.sign.web.dto.SignRecordDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author uzpeng on 2018/4/16.
 */
public interface SignRecordMapper {

    @InsertProvider(type = SignRecordProvider.class, method = "insertAll")
    void insertSignRecordList(@Param("list")List<SignRecordDO> signRecordDOS);

    @UpdateProvider(type = SignRecordProvider.class, method = "updateAll")
    void updateSignRecord(@Param("list")List<SignRecordDO> signRecordDO);

    @Update("UPDATE course_sign_record SET longitude=#{sign.longitude}, latitude=#{sign.latitude}," +
            "accuracy=#{sign.accuracy}, state=#{state} WHERE course_sign_id=#{sign.signId} AND student_id=#{studentId}")
    void sign(@Param("sign") SignRecordDTO signRecordDTO, @Param("studentId")Integer studentId,
              @Param("state") Integer state);

    @Select("SELECT * FROM course_sign_record WHERE course_sign_id=#{id}")
    List<SignRecordDO> getSignRecord(@Param("id") Integer signId);

    @Select("SELECT * FROM course_sign_record WHERE student_id=#{id}")
    List<SignRecordDO> getSignRecordByStudentId(@Param("id") Integer studentId);

    @SelectProvider(type = SignRecordProvider.class, method = "getBySignIds")
    List<SignRecordDO> getSignRecordBySignId(@Param("list") List<Integer> signIds);

    @Select("SELECT week FROM course_sign_record WHERE course_id=#{courseId} GROUP BY week")
    List<Integer> getWeeks(@Param("courseId") Integer courseId);

    @Select("SELECT COUNT(*) FROM course_sign_record WHERE course_sign_id=#{signId} AND state=#{state}")
    Integer getSignCountBySignId(@Param("signId") Integer signId, @Param("state")Integer state);

    @DeleteProvider(type = SignRecordProvider.class, method = "deleteBySignIds")
    void deleteBySignIdList(@Param("list") List<Integer> signIds);

    @DeleteProvider(type = SignRecordProvider.class, method = "deleteBySignIdsAndStudentId")
    void deleteBySignIdListAndStudentId(@Param("list") List<Integer> signIds, @Param("studentId") Integer studentId);
}
