package com.ninggc.gp.data;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.ninggc.gp.data.CheckUnit;

public interface CheckUnitMapper {

    int insert(@Param("pojo") CheckUnit pojo);

    int insertList(@Param("pojos") List< CheckUnit> pojo);

    List<CheckUnit> select(@Param("pojo") CheckUnit pojo);

    int update(@Param("pojo") CheckUnit pojo);

}
