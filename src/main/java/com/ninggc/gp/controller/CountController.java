package com.ninggc.gp.controller;

import com.ninggc.gp.service.ProcessService;
import com.ninggc.gp.service.ProgressService;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

public class CountController implements IController {
    ProcessService processService = null;
    ProgressService progressService = null;

    @Override
    public void initService(SqlSession session) throws IOException {
        processService = new ProcessService(session);
        progressService = new ProgressService(session);
    }


}
