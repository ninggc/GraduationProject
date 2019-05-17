package com.ninggc.gp.mapper;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.ninggc.gp.data.User;

public interface UserMapper {

    int insert(@Param("pojo") User pojo);

    int insertList(@Param("pojos") List< User> pojo);

    List<User> select(@Param("pojo") User pojo);

    List<User> selectWithLimit(@Param("pojo") User pojo, int index, int size);

    int update(@Param("pojo") User pojo);

    int delete(@Param("account") String account);
}
