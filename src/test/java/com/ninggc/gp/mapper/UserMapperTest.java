package com.ninggc.gp.mapper;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.google.gson.Gson;
import com.ninggc.gp.data.User;
import com.ninggc.gp.mapper.RoleMapper;
import com.ninggc.gp.mybatis.Factory;
import com.ninggc.gp.service.UserService;
import com.ninggc.gp.util.Printer;
import org.apache.ibatis.session.SqlSession;
import org.apache.poi.ss.usermodel.Workbook;
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
            user.setAccount("1503130115");
            List<User> list = service.select(user);
            if (list == null || list.size() == 0) {
                System.out.println("没有匹配项");
                return;
            }
            User u = list.get(0);
            session.commit();


            System.out.println(new Gson().toJson(u));

            Printer.print(u.getUpdate_time());
        }
    }

    @Test
    public void select() throws IOException {
    }

    @Test
    public void selectUserWithLimit() throws IOException {
        try (SqlSession session = Factory.openSession()) {
            service = new UserService(session);
            User user = new User();
            List<User> list = service.selectWithLimit(user, 0, 1);
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