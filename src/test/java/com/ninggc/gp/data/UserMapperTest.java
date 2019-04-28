package com.ninggc.gp.data;

import com.google.gson.Gson;
import com.ninggc.gp.mapper.RoleMapper;
import com.ninggc.gp.mybatis.Factory;
import com.ninggc.gp.service.UserService;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class UserMapperTest {

    private UserService service = null;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void selectUser() throws IOException {
        try (SqlSession session = Factory.openSession()) {
            service = new UserService(session);
            User user = new User();
            user.setAccount("123");
            List<User> list = service.select(user);
            if (list == null || list.size() == 0) {
                System.out.println("没有匹配项");
                return;
            }
            User u = list.get(0);
            session.commit();
            System.out.println(new Gson().toJson(u));
        }
    }

    @After
    public void tearDown() throws Exception {
    }
}