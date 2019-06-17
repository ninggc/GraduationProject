package com.ninggc.gp.service;

import com.ninggc.gp.controller.IController;
import com.ninggc.gp.data.File;
import com.ninggc.gp.mapper.ITest;
import com.ninggc.gp.tool.LayuiResult;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class FileServiceTest extends IController implements ITest {

    FileService fileService = null;

    @Test
    public void insert() {
//        LayuiResult<File> layuiResult = operateDate(new OperateHandler<File>() {
//            @Override
//            public File onOperate() {
//                File pojo = new File();
//                pojo.setAccount("1503130115");
//                pojo.setProcess_id(1);
//                int insert = fileService.insert(pojo);
//                return pojo;
//            }
//        });
//
//        layuiResult.format();
    }

    @Test
    public void insertList() {
    }

    @Test
    public void select() {
    }

    @Test
    public void update() {
    }

    @Override
    public void initService(SqlSession session) {
        fileService = new FileService(session);
    }
}