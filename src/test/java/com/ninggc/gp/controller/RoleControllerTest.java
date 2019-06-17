package com.ninggc.gp.controller;

import com.ninggc.gp.data.Role;
import com.ninggc.gp.service.RoleService;
import com.ninggc.gp.tool.LayuiResult;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static com.ninggc.gp.tool.LayuiResult.LAYUI_CODE_SUCCESS;
import static org.junit.Assert.*;

public class RoleControllerTest extends IController {
    RoleService roleService = null;

    @Override
    protected void initService(SqlSession session) throws IOException {
        super.initService(session);
        roleService = new RoleService(session);
    }

    @Test
    public void initService() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void select() {
        LayuiResult<List<Role>> listLayuiResult = operateDate(new OperateHandler<List<Role>>() {
            @Override
            public List<Role> onOperate() throws IOException {
                return roleService.select(new Role());
            }
        });
        listLayuiResult.format();
        for (Role r : listLayuiResult.getData()) {
            assertNotNull(r);
        }
//        Assert.assertEquals(CODE_SUCCESS, listLayuiResult.getCode());
//        Assert.assertNull(listLayuiResult.getMsg());
//        Assert.assertEquals(4, listLayuiResult.getData().size());
    }

    @Test
    public void selectOne() {
    }

    @Test
    public void selectWithUser() {
    }

    @Test
    public void selectWithProcess() {
        LayuiResult<List<Role>> listLayuiResult = operateDate(new OperateHandler<List<Role>>() {
            @Override
            public List<Role> onOperate() throws IOException {
                return roleService.selectWithProcess(0);
            }
        });

        listLayuiResult.format();
        assertEquals(LAYUI_CODE_SUCCESS, listLayuiResult.getCode());
    }

    @Test
    public void delete() {
    }
}