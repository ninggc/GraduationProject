package com.ninggc.gp.service;

import com.ninggc.gp.controller.IController;
import com.ninggc.gp.tool.LayuiResult;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

public class RoleHasUserServiceTest extends IController {

    RoleHasUserService roleHasUserService = null;

    @Override
    protected void initService(SqlSession session) throws IOException {
        super.initService(session);
        roleHasUserService = new RoleHasUserService(session);
    }

    @Test
    public void insert() {
//        LayuiResult<Integer> layuiResult = operateDate(new OperateHandler<Integer>() {
//            @Override
//            public Integer onOperate() throws IOException, SQLIntegrityConstraintViolationException {
//                return roleHasUserService.insert(1, "1503");
//            }
//        });
//        layuiResult.format();
    }

    @Test
    public void select() {
        LayuiResult<List<Map<String, Object>>> mapLayuiResult = operateDate(new OperateHandler<List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> onOperate() throws IOException, SQLIntegrityConstraintViolationException {
                return roleHasUserService.select(1, null);
            }
        });

        mapLayuiResult.format();
    }

    @Test
    public void delete() {
//        LayuiResult<Integer> layuiResult = operateDate(new OperateHandler<Integer>() {
//            @Override
//            public Integer onOperate() throws IOException, SQLIntegrityConstraintViolationException {
//                return roleHasUserService.delete(0, null);
//            }
//        });
//
//        layuiResult.format();
    }
}