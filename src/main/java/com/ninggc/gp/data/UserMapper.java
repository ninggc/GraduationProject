package com.ninggc.gp.data;

import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("select * from user where account = #{account}")
    User selectUser(int account);
}
