package com.ninggc.gp.mapper;


import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

import com.ninggc.gp.data.Progress;

public interface ProgressMapper {

    int insert(@Param("pojo") Progress pojo);

    int insertList(@Param("pojos") List< Progress> pojo);

    List<Progress> select(@Param("pojo") Progress pojo);

    List<Map<String, Object>> selectByTeacher(@Param("account") String account);

    int update(@Param("pojo") Progress pojo);

    int delete(@Param("id") int id);

//    List<Map<String, Object>> review(@Param("teacher_account") int teacher_account);

}
