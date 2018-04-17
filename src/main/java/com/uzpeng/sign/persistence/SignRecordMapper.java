package com.uzpeng.sign.persistence;

import com.uzpeng.sign.domain.SignRecordDO;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author uzpeng on 2018/4/16.
 */
public interface SignRecordMapper {

    @InsertProvider(type = SignRecordProvider.class, method = "insertAll")
    void insertSignRecordList(@Param("list")List<SignRecordDO> signRecordDOS);

    @Update("Update course_sign_record SET state=#{state} WHERE id=#{id}")
    void updateSignRecord(@Param("id")Integer id, @Param("state")Integer state);

    @Update("UPDATE course_sign_record SET longitude=#{sign.longitude}, latitude=#{sign.latitude}," +
            "state=#{sign.state} WHERE id=#{sign.id}")
    void sign(@Param("sign") SignRecordDO signRecordDO);

    @Select("SELECT * FROM course_sign_record WHERE course_sign_id=#{id}")
    List<SignRecordDO> getSignRecord(@Param("id") Integer signId);

    @InsertProvider(type = SignRecordProvider.class, method = "getBySignIds")
    List<SignRecordDO> getSignRecordBySignId(@Param("list") List<Integer> signIds);

    @Select("SELECT week FROM course_sign_record WHERE course_id=#{courseId} GROUP BY week")
    List<Integer> getWeeks(@Param("courseId") Integer courseId);

    @Select("SELECT COUNT(*) FROM course_sign_record WHERE course_sign_id=#{signId} AND state=#{state}")
    Integer getSignCountBySignId(@Param("signId") Integer signId, @Param("state")Integer state);

}
