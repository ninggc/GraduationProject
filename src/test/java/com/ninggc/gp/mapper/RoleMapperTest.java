package com.ninggc.gp.mapper;

import com.ninggc.gp.controller.IController;
import com.ninggc.gp.data.Role;
import com.ninggc.gp.mybatis.Factory;
import com.ninggc.gp.service.RoleService;
import com.ninggc.gp.tool.LayuiResult;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleMapperTest extends IController implements ITest {

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
//        Role bean = MainApplicationTests.context.getBean(Role.class);
//        System.out.println(bean.toJson());
//        try (SqlSession session = factory.openSession()) {
//            roleService = new RoleService(session);
//            int i = roleService.insert(bean);
//            session.commit();
//            System.out.println(i);
//        }
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
    public void selectWithUser() throws IOException {
        LayuiResult<List<Role>> listLayuiResult = operateDate(new OperateHandler<List<Role>>() {
            @Override
            public List<Role> onOperate() throws IOException, SQLIntegrityConstraintViolationException {
                return roleService.selectWithUser("15031301");
            }
        });
        listLayuiResult.format();
    }

    @Test
    public void update() {
//        Role bean = MainApplicationTests.context.getBean(Role.class);
//        bean.setDescription("update");
//        bean.setId(7);
//        System.out.println(bean.toJson());
//        try (SqlSession session = factory.openSession()) {
//            roleService = new RoleService(session);
//            int i = roleService.update(bean);
//            session.commit();
//            System.out.println(i);
//        }
    }

    @Test
    public void delete() {
//        Role bean = MainApplicationTests.context.getBean(Role.class);
//        bean.setDescription("update");
//        bean.setId(6);
//        System.out.println(bean.toJson());
//        try (SqlSession session = factory.openSession()) {
//            roleService = new RoleService(session);
//            int i = roleService.delete(bean.getId());
//            session.commit();
//            System.out.println(i);
//        }
    }

    @Override
    public void initService(SqlSession session) {
        roleService = new RoleService(session);
    }
}