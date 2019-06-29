package com.ninggc.gp.controller;

import com.google.gson.reflect.TypeToken;
import com.ninggc.gp.data.Process;
import com.ninggc.gp.data.*;
import com.ninggc.gp.service.CheckUnitService;
import com.ninggc.gp.service.ProcessService;
import com.ninggc.gp.service.ProgressService;
import com.ninggc.gp.service.StageService;
import com.ninggc.gp.tool.LayuiResult;
import com.ninggc.gp.util.Constant;
import com.ninggc.gp.util.Log;
import com.ninggc.gp.util.Printer;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

@RequestMapping("/progress")
@Controller
public class ProgressController extends IController {
    ProgressService progressService = null;
    ProcessService processService = null;
    StageService stageService = null;
    CheckUnitService checkUnitService = null;

    @Override
    public void initService(SqlSession session) throws IOException {
        progressService = new ProgressService(session);
        processService = new ProcessService(session);
        stageService = new StageService(session);
        checkUnitService = new CheckUnitService(session);
    }

    @RequestMapping(value = "/list")
    public String list(@SessionAttribute User user, ModelMap map) {
        try (SqlSession session = openSession()) {
            initService(session);
            List<Progress> list = progressService.select(new Progress().setAccount(user.getAccount()));
            Log.debug(getDebugLocation() + gson.toJson(list));
            map.addAttribute("list", list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "progressIndex";
    }

    @RequestMapping(value = "/show")
    public String show(@SessionAttribute User user, @RequestParam int process_id, ModelMap map) {
        if (user == null) {
            return "/login";
        }

        try (SqlSession session = openSession()) {
            initService(session);
            Progress progress = progressService.selectOne(new Progress().setAccount(user.getAccount()).setProcess_id(process_id));
            map.addAttribute("test", Printer.toJson(progress));
            map.addAttribute("progress", progress);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "progress";
    }

    @ResponseBody
    @RequestMapping(value = "/detail/stage")
    public String DetailStageInProcess(@SessionAttribute User user, @RequestParam int process_id) {
        if (user == null) {
            return "/login";
        }

        paramPreview(process_id);

        LayuiResult<Process> layuiResult = operateDate(new OperateHandler<Process>() {
            @Override
            public Process onOperate() throws IOException {
                Process process = processService.selectOne(new Process().setId(process_id));
                List<Stage> stages = stageService.select(new Stage().setProcess_id(process_id));

                process.setStageList(stages);
                return process;
            }
        });

        return layuiResult.format();
    }

//    @ResponseBody
//    @RequestMapping(value = "/detail/stage/student")
//    public String DetailStageInStudent(@SessionAttribute User user, @RequestParam int process_id) {
//        if (user == null) {
//            return "/login";
//        }
//
//        paramPreview(process_id);
//
//
//        LayuiResult<Process> layuiResult = operateData(new OperateHandler<Process>() {
//            @Override
//            public Process onOperate() {
////                Progress progress = progressService.selectOne(new Progress().setProcess_id(process_id));
//                Process process = processService.selectOne(new Process().setId(process_id));
//                List<Stage> stages = stageService.select(new Stage().setProcess_id(process_id));
//
//                process.setStageList(stages);
//                return process;
//            }
//        }, new LayuiResult<Process>());
//
//        return layuiResult.format();
//    }

    @ResponseBody
    @RequestMapping(value = "/checkUnit")
    public String passUnit(@ModelAttribute Progress progress, @RequestParam int unit_id, @RequestParam byte pass) {
        try (SqlSession session = openSession()) {
            initService(session);
            Progress selectOne = progressService.selectOne(progress);
            if (progress != null) {
                Map<Integer, UtilPass> data = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "json";
    }


    /**
     * @param utilPass
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    public String update(@SessionAttribute User user, @ModelAttribute UtilPass utilPass, @RequestParam String progress_account) {

        paramPreview(utilPass);

        LayuiResult<Integer> layuiResult = operateDate(new OperateHandler<Integer>() {
            @Override
            public Integer onOperate() throws IOException {
//                从数据库获取进度
                Progress progress = new Progress().setAccount(progress_account);
                progress.setProcess_id(utilPass.getProcess_id());
                Progress selectOne = progressService.selectOne(progress);
                Log.debug(gson.toJson(selectOne));

//                解析进度列表
                Map<Integer, UtilPass> map = selectOne.parseFromData();
//              更新当前请求,如果有，就替换，没有，就新增
                if (map.containsKey(utilPass.getUnit_id())) {
                    map.replace(utilPass.getUnit_id(), utilPass);
                } else {
//              将数据库中新增的unit添加到progress
                    map.put(utilPass.getUnit_id(), utilPass);
                }

//                获取当前stage的sequence并验证，如果当前sequence的unit全部通过审核，则将current_sequence的值加一
                Stage stage = stageService.selectOne(new Stage().setId(utilPass.getStage_id()));
                List<CheckUnit> checkUnits = checkUnitService.selectByStageId(utilPass.getStage_id());
//                记录是否进度到下一阶段
                int sequenceAddition = 1;
                for (CheckUnit u : checkUnits) {
//                    验证当前stage的所有unit是否全部通过审核
                    Byte pass = map.get(u.getId()).getPass();
                    if (!pass.equals(Constant.PASS_YES)) {
                        sequenceAddition = 0;
                    }
                }
                selectOne.sequenceIncrease(sequenceAddition);

//                检索审批是否全部结果，如果全部结束，进度的finish值设为1

//                这里可能并不需要，怀疑json的字符串可能有问题，但是去掉也可以
                String data = gson.toJson(map);
                selectOne.setData(data.replace("\\", ""));


//            写回到数据库
                return progressService.update(selectOne);
            }
        });


        return layuiResult.format();
    }

    //            从数据库获取同步所有的unit id（未写回数据库）
    private Map<Integer, UtilPass> synchronize(Progress progress) {
        int process_id = progress.getProcess_id();

        Set<Integer> allUnitId = getAllUnitId(checkUnitService, process_id);

        Map<Integer, UtilPass> map = progress.parseFromData();
        if (map.keySet().size() != allUnitId.size()) {
//                比较两个set获取处理不同的元素
            allUnitId.removeAll(map.keySet());
//                将data中没有的id加入进去
            for (int v : allUnitId) {
                map.put(v, new UtilPass().setPass((byte) 0));
            }
        }
        Log.debug(gson.toJson(map));
        return map;
    }

    //        从数据库获取所有unit id
    private Set<Integer> getAllUnitId(CheckUnitService service, int process_id) {
        List<CheckUnit> selectByProcessId = checkUnitService.selectByProcessId(process_id);
        Set<Integer> unitIdList = new HashSet<>();
        for (CheckUnit v :
                selectByProcessId) {
            unitIdList.add(v.getId());
        }
        return unitIdList;
    }

    @ResponseBody
    @RequestMapping(value = "/layui/select/one")
    public String select(@SessionAttribute User user, @RequestParam int progress_id) {
        paramPreview(progress_id);

        LayuiResult<Progress> layuiResult = operateDate(new OperateHandler<Progress>() {
            @Override
            public Progress onOperate() throws IOException {
                return progressService.selectOne(new Progress().setId(progress_id));
            }
        });

        return layuiResult.format();
    }

    @ResponseBody
    @RequestMapping(value = "/layui/select/checkExist")
    public String selectByAccountAndProcess(@SessionAttribute User user, @ModelAttribute Progress progress) {
        paramPreview(progress);
        if (progress.getProcess_id() == null) {
            return new LayuiResult().failed("进程编号为空").format();
        }

        progress.setAccount(user.getAccount());
        LayuiResult<Progress> layuiResult = operateDate(new OperateHandler<Progress>() {
            @Override
            public Progress onOperate() throws IOException {
                return progressService.selectOne(progress);
            }
        });

        if (layuiResult.getData() != null && layuiResult.getData().getId() != null) {
            layuiResult.setCount(1);
        }
        return layuiResult.format();
    }

    @ResponseBody
    @RequestMapping(value = "/layui/delete")
    public String delete(@SessionAttribute User user, @RequestParam int progress_id) {
        paramPreview(progress_id);

        LayuiResult<Integer> layuiResult = operateDate(new OperateHandler<Integer>() {
            @Override
            public Integer onOperate() throws IOException {
                return progressService.delete(progress_id);
            }
        });

        return layuiResult.format();
    }

    /**
     *
     * @param user
     * @return 根据教师account返回需要审核的unit list
     */
    @ResponseBody
    @RequestMapping(value = "/layui/review")
    public String review(@SessionAttribute User user) {
        paramPreview(user);

        LayuiResult layuiResult1 = checkPrivilegeWithNotAllowed(user, "student");
        if (layuiResult1 != null) {
            return layuiResult1.format();
        }

        LayuiResult<List<Map<String, Object>>> layuiResult = operateDate(new OperateHandler<List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> onOperate() {
                List<Map<String, Object>> selectByTeacher = progressService.selectByTeacher(user.getAccount());

                resultPreview(selectByTeacher);

                List<Map<String, Object>> list = new ArrayList<>();
                Type type = new TypeToken<Map<Integer, UtilPass>>() {}.getType();
                // TODO: 2019/6/4 非常耗时
//                如果需要审核的unit的前一个stage未完成，则过滤
//                根据table progress中的current_sequence确定当前的stage
                for (Map<String, Object> map : selectByTeacher) {

//                    如果当前审批已经通过（pass=1）则跳过，（pass=0 || pass=2）则加入
                    Progress progress = new Progress();
                    progress.setData((String) map.get("data"));
                    int unit_id = (int) map.get("unit_id");
                    UtilPass utilPass = progress.parseFromData().get(unit_id);
                    Byte pass = utilPass.getPass();
                    if (pass == 1) {
                        continue;
                    } else if (pass == 2)  {
                        map.put("unit_pass_description", "审核不通过：" + utilPass.getDescription());
                    } else if (pass == 0) {
                        map.put("unit_pass_description", "未审核：");
                    }

                    int currentSequence = (int) map.get("current_sequence");
                    int stageSequence = (int) map.get("stage_sequence");
                    if (stageSequence == currentSequence) {
                        // TODO: 2019/6/23 从数据中查出角色信息
                        map.put("role_name", "审批角色1");

                        list.add(map);
                    } else if (stageSequence < currentSequence) {
//                        throw new RuntimeException("遗漏之前的阶段");
                    } else if (stageSequence > currentSequence) {
//                        throw new RuntimeException("未进行到该阶段");
                    }
                }

                return list;
            }
        });

        return layuiResult.format();
    }

    List<Map<String, Object>> filtReviewList(List<Map<String, Object>> selectByTeacher) throws RuntimeException {
        List<Map<String, Object>> list = new ArrayList<>();
        Type type = new TypeToken<Map<Integer, UtilPass>>() {}.getType();
        // TODO: 2019/6/4 非常耗时
//                如果需要审核的unit的前一个stage未完成，则过滤
//                根据table progress中的current_sequence确定当前的stage
        for (Map<String, Object> map : selectByTeacher) {
            int currentSequence = (int) map.get("current_sequence");
            int stageSequence = (int) map.get("stage_sequence");
            if (stageSequence == currentSequence) {
                list.add(map);
            } else if (stageSequence < currentSequence) {
                throw new RuntimeException("遗漏之前的阶段");
            } else if (stageSequence > currentSequence) {
                throw new RuntimeException("未进行到该阶段");
            }
        }

        return list;
    }

}
