package com.ninggc.gp.controller;

import com.ninggc.gp.data.Process;
import com.ninggc.gp.data.Stage;
import com.ninggc.gp.data.User;
import com.ninggc.gp.service.CheckUnitService;
import com.ninggc.gp.service.ProcessService;
import com.ninggc.gp.service.StageService;
import com.ninggc.gp.util.Log;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/process")
public class ProcessController implements IController {
    private ProcessService processService = null;
    private StageService stageService = null;
    private CheckUnitService checkUnitService = null;

    @Override
    public void initService(SqlSession session) throws IOException {
            processService = new ProcessService(session);
            stageService = new StageService(session);
            checkUnitService = new CheckUnitService(session);
    }

    @RequestMapping("/page/create")
    public String createProcess(ModelMap map) {
//        if (process_id != null) {
//            try (SqlSession session = openSession()) {
//                initService(session);
//                Process process = processService.selectOne(new Process().setId(process_id));
//                List<Stage> stageList = stageService.select(new Stage().setProcess_id(process.getId()));
//                map.addAttribute("process", process);
//                map.addAttribute("stageList", stageList);
//            } catch (IOException e) {
//                Log.error("初始化失败");
//                e.printStackTrace();
//            }
//        }
            return "createProcess";
    }

    @RequestMapping(value = "/action/create", method = RequestMethod.POST)
    @ResponseBody
    public String create(@SessionAttribute User user, @ModelAttribute Process process) {
        try(SqlSession session = openSession()) {
            initService(session);
            processService.insert(process);
            session.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gson.toJson(process);
    }

    @ResponseBody
    @RequestMapping("/action/addStage")
    public String addStage(@ModelAttribute Stage stage) {
        Log.debug(toJson(stage));

        try(SqlSession session = openSession()) {
            initService(session);
            //pass是过时字段，此处避免stage无赋值导致的sql异常
            stage.setPass((byte) 0);
            stageService.insert(stage);
            session.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return toJson(stage);
    }

    @ResponseBody
    @RequestMapping("/action/addUnit")
    public String addUnit(@RequestParam int process_id, ModelMap map) {
        return "createProcess";
    }


    @RequestMapping("/page/finish")
    @ResponseBody
    public String finish(ModelMap map) {
        return "finish";
    }

    @RequestMapping("/page/edit")
    public String edit(@RequestParam int process_id, ModelMap map) {
        return "createProcess";
    }

    @ResponseBody
    @RequestMapping("/showProcess")
    public String showProcess(@RequestAttribute int id) {
        String result = "";
        try(SqlSession session = openSession()) {
            initService(session);
            Process process = processService.selectOne(new Process().setId(id));
            List<Stage> stages = stageService.select(new Stage().setProcess_id(process.getId()));
            process.setStageList(stages);
            result = process.toJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}
