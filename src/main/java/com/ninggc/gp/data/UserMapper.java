package com.ninggc.gp.data;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.ninggc.gp.data.User;

public interface UserMapper {

    int insert(@Param("pojo") User pojo);

    int insertList(@Param("pojos") List< User> pojo);

    List<User> select(@Param("pojo") User pojo);

    int update(@Param("pojo") User pojo);

}
