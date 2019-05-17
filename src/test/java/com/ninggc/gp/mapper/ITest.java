package com.ninggc.gp.mapper;

import com.google.gson.Gson;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

public interface ITest {
    Gson gson = new Gson();

    void initService(SqlSession session);
}
