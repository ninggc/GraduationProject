package com.ninggc.gp.data;

import com.ninggc.gp.mapper.RoleMapper;
import com.ninggc.gp.mybatis.Factory;
import com.ninggc.gp.service.RoleService;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleMapperTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void insert() {
    }

    @Test
    public void insertList() {
    }

    @Test
    public void select() throws IOException {
        SqlSession session = Factory.openSession();
        Role pojo = new Role();
        pojo.setName("管理");
        try {
            RoleMapper roleMapper = session.getMapper(RoleMapper.class);
            List<Role> roles = new RoleService().select(pojo);
//            List<Role> roles = roleMapper.select(pojo);
            for (Role r : roles) {
                System.out.println(r.toJson());
            }
        } finally {
            session.close();
        }
    }

    @Test
    public void update() {
    }
}