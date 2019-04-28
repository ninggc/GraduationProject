package com.ninggc.gp.controller;

import com.ninggc.gp.data.Process;
import com.ninggc.gp.data.Stage;
import com.ninggc.gp.mybatis.Factory;
import com.ninggc.gp.service.ProcessService;
import com.ninggc.gp.service.StageService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

public class ProcessController implements IController {
    private ProcessService processService = null;
    private StageService stageService = null;

    @ResponseBody
    @RequestMapping("/showProcess")
    public String showProcess(@RequestAttribute int id) {
        String result = "";

        try {
            initService();
            Process process = processService.selectOne(new Process().setId(id));
            List<Stage> stages = stageService.select(new Stage().setProcess_id(process.getId()));
            process.setStageList(stages);
            result = process.toJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void initService() throws IOException {
        try(SqlSession session = Factory.openSession()) {
            processService = new ProcessService(session);
            stageService = new StageService(session);
        }
    }
}
