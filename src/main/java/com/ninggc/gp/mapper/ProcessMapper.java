package com.ninggc.gp.mapper;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

import com.ninggc.gp.data.Process;

public interface  ProcessMapper {

    int insert(@Param("pojo") Process pojo);

    int insertList(@Param("pojos") List< Process> pojo);

    List<Process> select(@Param("pojo") Process pojo);

    int update(@Param("pojo") Process pojo);

    List<Process> selectAllByUser(String account);
}
