package com.ninggc.gp.data;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.ninggc.gp.data.File;

public interface FileMapper {

    int insert(@Param("pojo") File pojo);

    int insertList(@Param("pojos") List< File> pojo);

    List<File> select(@Param("pojo") File pojo);

    int update(@Param("pojo") File pojo);

}
