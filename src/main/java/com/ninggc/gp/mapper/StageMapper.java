package com.ninggc.gp.mapper;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.ninggc.gp.data.Stage;

public interface StageMapper {

    int insert(@Param("pojo") Stage pojo);

    int insertList(@Param("pojos") List< Stage> pojo);

    List<Stage> select(@Param("pojo") Stage pojo);

    int update(@Param("pojo") Stage pojo);

    int delete(@Param("id") int id);
}
