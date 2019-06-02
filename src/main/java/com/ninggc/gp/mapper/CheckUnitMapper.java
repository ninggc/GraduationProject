package com.ninggc.gp.mapper;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.ninggc.gp.data.CheckUnit;
import org.springframework.context.annotation.Configuration;

@Configuration
public interface CheckUnitMapper {

    int insert(@Param("pojo") CheckUnit pojo);

    int insertList(@Param("pojos") List< CheckUnit> pojo);

    List<CheckUnit> select(@Param("pojo") CheckUnit pojo);

    List<CheckUnit> selectByAccount(@Param("teacher_account") String teacher_account);

    int update(@Param("pojo") CheckUnit pojo);
}
