package com.ninggc.gp.mapper;

import com.google.gson.Gson;
import com.ninggc.gp.controller.IController;
import com.ninggc.gp.data.Role;
import com.ninggc.gp.data.User;
import com.ninggc.gp.mybatis.Factory;
import com.ninggc.gp.service.UserService;
import com.ninggc.gp.tool.LayuiResult;
import com.ninggc.gp.util.Printer;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

public class UserMapperTest extends IController {

    private UserService userService = null;

    @Override
    protected void initService(SqlSession session) throws IOException {
        super.initService(session);
        userService = new UserService(session);
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void selectUser() throws IOException {
        try (SqlSession session = Factory.openSession()) {
            userService = new UserService(session);
            User user = new User();
            user.setAccount("1503130115");
            List<User> list = userService.select(user);
            if (list == null || list.size() == 0) {
                System.out.println("没有匹配项");
                return;
            }
            User u = list.get(0);
            session.commit();


            System.out.println(new Gson().toJson(u));

            System.out.println(Printer.format(u.getUpdate_time()));
        }
    }

    @Test
    public void select() throws IOException {
    }

    @Test
    public void selectCount() throws IOException {
        LayuiResult<Integer> layuiResult = operateDate(new OperateHandler<Integer>() {
            @Override
            public Integer onOperate() throws IOException, SQLIntegrityConstraintViolationException {
                return userService.selectCount(null);
            }
        });

        layuiResult.format();
    }

    @Test
    public void selectWithRole() throws IOException {
        LayuiResult<List<Map<String, Object>>> layuiResult = operateDate(new OperateHandler<List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> onOperate() throws IOException, SQLIntegrityConstraintViolationException {
                Role role = new Role();
                role.setId(1);
                return userService.selectWithRole(role);
            }
        });

        layuiResult.format();
    }

    @Test
    public void selectUserWithLimit() throws IOException {
        try (SqlSession session = Factory.openSession()) {
            userService = new UserService(session);
            User user = new User();
            List<User> list = userService.selectWithLimit(user, 0, 1);
            if (list == null || list.size() == 0) {
                System.out.println("没有匹配项");
                return;
            }

            System.out.println(new Gson().toJson(list));
        }
    }

    @After
    public void tearDown() throws Exception {
    }
}