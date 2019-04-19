package com.ninggc.gp.mapper;

import com.ninggc.gp.data.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

public interface RoleMapper {

    int insert(@Param("pojo") Role pojo);

    int insertList(@Param("pojos") List< Role> pojo);

    List<Role> select(@Param("pojo") Role pojo);

    int update(@Param("pojo") Role pojo);

}
