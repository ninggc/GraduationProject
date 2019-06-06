package com.ninggc.gp.mapper;

import com.ninggc.gp.data.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoleMapper {

    int insert(@Param("pojo") Role pojo);

    int insertList(@Param("pojos") List< Role> pojo);

    List<Role> select(@Param("pojo") Role pojo);

    List<Role> selectWithUser(@Param("account") String account);

    List<Role> selectWithProcess(@Param("process_id") Integer process_id);

    int update(@Param("pojo") Role pojo);

    int delete(@Param("id") int id);

}
