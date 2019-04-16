package com.ninggc.gp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DemoMapper {

    @Select("select name from User where name = #{name}")
    String findName(@Param("name") String name);
}
