package com.ninggc.gp.mapper;

import org.apache.ibatis.session.SqlSession;

import javax.annotation.Resource;

public class MapperFactory {
    @Resource(name = "sqlSession")
    public SqlSession session;
}
