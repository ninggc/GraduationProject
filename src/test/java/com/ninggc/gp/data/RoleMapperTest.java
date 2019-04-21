package com.ninggc.gp.data;

import com.ninggc.gp.MainApplicationTests;
import com.ninggc.gp.mapper.RoleMapper;
import com.ninggc.gp.mybatis.Factory;
import com.ninggc.gp.service.RoleService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleMapperTest {

    SqlSessionFactory factory = null;
    RoleService roleService = null;

    @Before
    public void setUp() throws Exception {
        factory = Factory.getSqlSessionFactory();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void insert() {
        Role bean = MainApplicationTests.context.getBean(Role.class);
        System.out.println(bean.toJson());
        try (SqlSession session = factory.openSession()) {
            roleService = new RoleService(session);
            int i = roleService.insert(bean);
            session.commit();
            System.out.println(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            List<Role> roles = new RoleService(session).select(pojo);
//            RoleMapper roleMapper = session.getMapper(RoleMapper.class);
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
        Role bean = MainApplicationTests.context.getBean(Role.class);
        bean.setDescription("update");
        bean.setId(7);
        System.out.println(bean.toJson());
        try (SqlSession session = factory.openSession()) {
            roleService = new RoleService(session);
            int i = roleService.update(bean);
            session.commit();
            System.out.println(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delete() {
        Role bean = MainApplicationTests.context.getBean(Role.class);
        bean.setDescription("update");
        bean.setId(6);
        System.out.println(bean.toJson());
        try (SqlSession session = factory.openSession()) {
            roleService = new RoleService(session);
            int i = roleService.delete(bean.getId());
            session.commit();
            System.out.println(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}