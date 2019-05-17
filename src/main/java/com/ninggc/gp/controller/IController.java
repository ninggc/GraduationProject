package com.ninggc.gp.controller;

import com.google.gson.Gson;
import com.ninggc.gp.mybatis.Factory;
import com.ninggc.gp.tool.Result;
import com.ninggc.gp.util.Constant;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

public interface IController {

    int CODE_SUCCESS = 200;
    int CODE_FAILED = 500;

    static Gson gson = Constant.gson;

//    default Map<String, String> initResult() {
//        return new HashMap<>();
//    }

    default SqlSession openSession() throws IOException {
        return Factory.openSession();
    }

    default void initService(SqlSession session) throws IOException {
//        如果Controller有多个Service，可以在这里同意初始化
    };

    default String getDebugLocation() {
        return this.getClass().getSimpleName() + ": ";
    }

    default String toJson(Object o) {
        return gson.toJson(o);
    }

    default Result initResult() {
        return new Result();
    }
}
