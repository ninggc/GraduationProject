package com.ninggc.gp.mapper;


import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.ninggc.gp.data.Progress;

public interface ProgressMapper {

    int insert(@Param("pojo") Progress pojo);

    int insertList(@Param("pojos") List< Progress> pojo);

    List<Progress> select(@Param("pojo") Progress pojo);

    int update(@Param("pojo") Progress pojo);

    int delete(@Param("id") int id);

}
