package com.ninggc.gp.mapper;

import com.ninggc.gp.data.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface RoleHasUserMapper {

    int insert(@Param("role_id") Integer role_id, @Param("account") String account);

//    int insertList(@Param("pojos") List<Role> pojo);

    List<Map<String, Object>> select(@Param("role_id") Integer role_id, @Param("account") String account);

//    List<Role> selectWithUser(@Param("account") String account);
//
//    List<Role> selectWithProcess(@Param("process_id") Integer process_id);
//
//    int update(@Param("pojo") Role pojo);

    int delete(@Param("role_id") Integer role_id, @Param("account") String account);

}
