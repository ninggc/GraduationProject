package com.ninggc.gp.data;

import com.google.gson.Gson;
import com.ninggc.gp.mapper.RoleMapper;
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
        SqlSession session = Factory.openSession();
        try {
            RoleMapper mapper = session.getMapper(RoleMapper.class);
            Role role = new Role();
            role.setName("test");
            int i = mapper.insert(role);
            session.commit();
            System.out.println(new Gson().toJson(i));
        } finally {
            session.close();
        }
    }

    @After
    public void tearDown() throws Exception {
    }
}