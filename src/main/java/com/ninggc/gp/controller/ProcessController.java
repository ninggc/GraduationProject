package com.ninggc.gp.controller;

import com.google.gson.reflect.TypeToken;
import com.ninggc.gp.data.*;
import com.ninggc.gp.data.Process;
import com.ninggc.gp.service.CheckUnitService;
import com.ninggc.gp.service.ProcessService;
import com.ninggc.gp.service.ProgressService;
import com.ninggc.gp.service.StageService;
import com.ninggc.gp.tool.LayuiResult;
import com.ninggc.gp.util.Log;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value = "/process")
public class ProcessController extends IController {
    private ProcessService processService = null;
    private ProgressService progressService = null;
    private StageService stageService = null;
    private CheckUnitService checkUnitService = null;

    @Override
    public void initService(SqlSession session) throws IOException {
            processService = new ProcessService(session);
            progressService = new ProgressService(session);
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

        LayuiResult<Stage> layuiResult = operateDate(new OperateHandler<Stage>() {
            @Override
            public Stage onOperate() throws IOException {
                List<Stage> select = stageService.select(new Stage().setProcess_id(stage.getProcess_id()));
                stage.setSequence((short) (select.size() + 1));
                stageService.insert(stage);
                return stage;
            }
        });

        return layuiResult.format();
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

    @ResponseBody
    @RequestMapping("/layui/list")
//    public String list(@SessionAttribute User user) {
    public String layuiList(@SessionAttribute User user) {
        String account = user.getAccount();
        paramPreview(account);

        LayuiResult<List<Process>> layuiResult = operateDate(new OperateHandler<List<Process>>() {
            @Override
            public List<Process> onOperate() {
                return processService.select(new Process());
            }
        });

        resultPreview(layuiResult);
        return layuiResult.format();
    }

    /**
     * 筛选出用户申请的列表
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/layui/list/user")
//    public String list(@SessionAttribute User user) {
    public String layuiListStudent(@SessionAttribute User user) {
        String account = user.getAccount();
        paramPreview(account);

        LayuiResult<List<Process>> layuiResult = operateDate(new OperateHandler<List<Process>>() {
            @Override
            public List<Process> onOperate() {
                return processService.selectAllByUser(user.getAccount());
            }
        });

        resultPreview(layuiResult);
        return layuiResult.format();
    }

    @RequestMapping("/apply")
    /**
     * id 申请的process id
     */
    public String apply(@SessionAttribute User user, @RequestParam int id, ModelMap map) {
        paramPreview(user);

        LayuiResult<Process> layuiResult = operateDate(new OperateHandler<Process>() {
            @Override
            public Process onOperate() {
                return processService.selectOne(new Process().setId(id));
            }
        });

        Process data = layuiResult.getData();
        map.addAttribute("process", data);
        map.addAttribute("msg", (List<String>) gson.fromJson(data.getMsg(), new TypeToken<List<String>>(){}.getType()));
        map.addAttribute("files", (List<String>) gson.fromJson(data.getFiles(), new TypeToken<List<String>>(){}.getType()));
        return "apply";
    }

    @ResponseBody
    @RequestMapping("/action/apply")
    /**
     * id 申请的process id
     */
    public String formApply(@SessionAttribute User user, @ModelAttribute Progress progress, HttpServletRequest request) {
        paramPreview(progress);

        if (progress.getProcess_id() == null) {
            return new LayuiResult<>().failed("程序异常，缺少process_id").format();
        }

        LayuiResult<Integer> layuiResult = operateDate(new OperateHandler<Integer>() {
            @Override
            public Integer onOperate() {
                Progress pojo = progress;
                pojo.setAccount(user.getAccount());

                pojo.setData(initProgressData(checkUnitService, progress.getProcess_id()));

                return progressService.insert(pojo);
            }
        });

        return layuiResult.format();
    }

    private String initProgressData(CheckUnitService unitService, int process_id) {
        Map<Integer, UtilPass> map = new HashMap<>();
        Set<Integer> allUnitId = getAllUnitId(unitService, process_id);
        for (Integer id : allUnitId) {
            UtilPass utilPass = new UtilPass().setPass((byte) 0);
            utilPass.setDescription("无");
            map.put(id, utilPass);
        }
        return toJson(map);
    }

    //        从数据库获取所有unit id
    private Set<Integer> getAllUnitId(CheckUnitService service, int process_id) {
        List<CheckUnit> selectByProcessId = checkUnitService.selectByProcessId(process_id);
        Set<Integer> unitIdList = new HashSet<>();
        for (CheckUnit v : selectByProcessId) {
            unitIdList.add(v.getId());
        }
        return unitIdList;
    }

    @ResponseBody
    @RequestMapping("/layui/list/stage")
//    public String list(@SessionAttribute User user) {
    public String layuiListStage(@SessionAttribute User user, @RequestParam int process_id) {
        paramPreview(process_id);

        LayuiResult layuiResult = operateData(new OperateHandler<List<Stage>>() {
            @Override
            public List<Stage> onOperate() {
                return stageService.select(new Stage().setProcess_id(process_id));
            }
        }, new LayuiResult<List<Stage>>());

        resultPreview(layuiResult);
        return layuiResult.format();
    }

    @ResponseBody
    @RequestMapping("/layui/list/unit")
//    public String list(@SessionAttribute User user) {
    public String layuiListUnit(@SessionAttribute User user, @RequestParam int stage_id, @RequestParam int process_id) {
        paramPreview(stage_id);

        LayuiResult<List<CheckUnit>> listLayuiResult = operateData(new OperateHandler<List<CheckUnit>>() {
            @Override
            public List<CheckUnit> onOperate() {
                return checkUnitService.select(new CheckUnit().setStage_id(stage_id));
            }
        }, new LayuiResult<>());

        Progress progress = operateData(new OperateHandler<Progress>() {
            @Override
            public Progress onOperate() {
                return progressService.selectOne(new Progress().setAccount(user.getAccount()).setProcess_id(process_id));
            }
        }, new LayuiResult<>()).getData();
//        该用户在该审批的进度
        Map<Integer, UtilPass> map = parseProgress(progress);
        for (int i = 0; i < listLayuiResult.getData().size(); i++) {
            Integer unit_id = listLayuiResult.getData().get(i).getId();
            try {
                UtilPass utilPass = map.get(unit_id);
                listLayuiResult.getData().get(i).setPass(utilPass.getPass());
                listLayuiResult.getData().get(i).setProgress_description(utilPass.getDescription());
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        resultPreview(listLayuiResult);
        return listLayuiResult.format();
    }

// TODO: 2019/5/21 cache
    public Progress getProgress(SqlSession session, String account, int process_id) {
        LayuiResult<Progress> layuiResult = operateData(new OperateHandler<Progress>() {
            @Override
            public Progress onOperate() {
                return progressService.selectOne(new Progress().setAccount(account).setProcess_id(process_id));
            }
        }, new LayuiResult<>());
        return layuiResult.getData();
    }

    public Map<Integer, UtilPass> parseProgress(Progress progress) {
        return gson.fromJson(progress.getData(), new TypeToken<Map<Integer, UtilPass>>(){}.getType());
    }
}
