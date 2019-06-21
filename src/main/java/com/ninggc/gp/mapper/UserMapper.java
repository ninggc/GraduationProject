package com.ninggc.gp.mapper;

import com.ninggc.gp.data.Role;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

import com.ninggc.gp.data.User;

public interface UserMapper {

    int insert(@Param("pojo") User pojo);

    int insertList(@Param("pojos") List< User> pojo) throws SQLIntegrityConstraintViolationException;

    List<User> select(@Param("pojo") User pojo);

    int selectCount(@Param("addition") String addition);

    List<User> selectWithLimit(@Param("pojo") User pojo, int page, int size);

    List<Map<String, Object>> selectWithRole(@Param("role") Role role);

    int update(@Param("pojo") User pojo);

    int delete(@Param("account") String account);

}
