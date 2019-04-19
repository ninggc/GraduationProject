package com.ninggc.gp.data;

import com.google.gson.Gson;
import com.ninggc.gp.mybatis.Factory;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class UserMapperTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void selectUser() throws IOException {
//        SqlSession session = Factory.openSession();
//        try {
//            UserMapper userMapper  = session.getMapper(UserMapper.class);
//            User user = userMapper.findByAccount("1503130115");
//            System.out.println(new Gson().toJson(user));
//        } finally {
//            session.close();
//        }
    }

    @After
    public void tearDown() throws Exception {
    }
}