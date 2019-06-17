package com.ninggc.gp.mapper;

import com.google.gson.reflect.TypeToken;
import com.ninggc.gp.controller.IController;
import com.ninggc.gp.data.CheckUnit;
import com.ninggc.gp.data.Progress;
import com.ninggc.gp.data.Stage;
import com.ninggc.gp.data.UtilPass;
import com.ninggc.gp.mybatis.Factory;
import com.ninggc.gp.service.CheckUnitService;
import com.ninggc.gp.service.ProgressService;
import com.ninggc.gp.service.StageService;
import com.ninggc.gp.tool.LayuiResult;
import com.ninggc.gp.util.Constant;
import com.ninggc.gp.util.Log;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class ProgressMapperTest extends IController implements ITest {

    SqlSessionFactory factory = null;
    ProgressService progressService = null;
    StageService stageService = null;
    CheckUnitService checkUnitService = null;

    @Before
    public void setUp() throws Exception {
        factory = Factory.getSqlSessionFactory();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void select() throws IOException {
        SqlSession session = Factory.openSession();

        initService(session);

        Progress progress = progressService.selectOne(new Progress().setAccount("1503130115"));

        System.out.println(progress.toJson());

//        Map<Integer, Byte> map = gson.fromJson(progress.getData(), new TypeToken<Map<Integer, Byte>>(){}.getType());
//
//        System.out.println(gson.toJson(map.get(1)));

        session.close();
    }

    @Test
    public void update() throws IOException {
//        UtilPass utilPass = new UtilPass();
//        utilPass.setProcess_id(1);
//        utilPass.setStage_id(1);
//        utilPass.setUnit_id(1);
//        utilPass.setPass((byte) 1);
//        utilPass.setDescription("测试描述");
//
//
//        LayuiResult<Integer> layuiResult = operateDate(new IController.OperateHandler<Integer>() {
//            @Override
//            public Integer onOperate() throws IOException {
////                从数据库获取进度
//                Progress progress = new Progress().setAccount("15031301");
//                progress.setProcess_id(utilPass.getProcess_id());
//                Progress selectOne = progressService.selectOne(progress);
//                Log.debug(gson.toJson(selectOne));
//
////              将数据库中新增的unit添加到progress
//                Map<Integer, UtilPass> map = synchronize(selectOne);
////              更新当前请求
//                map.replace(utilPass.getUnit_id(), utilPass);
//
////                获取当前stage的sequence
////                验证 如果当前sequence的unit全部通过审核，则将current_sequence的值加一
//                Stage stage = stageService.selectOne(new Stage().setId(utilPass.getStage_id()));
//                List<CheckUnit> checkUnits = checkUnitService.selectByStageId(utilPass.getStage_id());
//
//                int sequenceAddition = 1;
//                for (CheckUnit u : checkUnits) {
////                    验证当前stage的所有unit是否全部通过审核
//                    Byte pass = map.get(u.getId()).getPass();
//                    if (!pass.equals(Constant.PASS_YES)) {
//                        sequenceAddition = 0;
//                    }
//                }
//                selectOne.sequenceIncrease(sequenceAddition);
//
//                String data = gson.toJson(map);
//                selectOne.setData(data.replace("\\", ""));
//
//
////            写回到数据库
//                return progressService.update(selectOne);
//            }
//        });
//
//        layuiResult.format();
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

    @Test
    public void selectByTeacher() throws IOException {
        LayuiResult<List<Map<String, Object>>> layuiResult = operateDate(new OperateHandler<List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> onOperate() {
                List<Map<String, Object>> selectByTeacher = progressService.selectByTeacher("15031301");

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
                        list.add(map);
                    } else if (stageSequence < currentSequence) {
//                        throw new RuntimeException("遗漏之前的阶段");
                    } else if (stageSequence > currentSequence) {
//                        throw new RuntimeException("未进行到该阶段");
                    }
                }

                for (Map m :
                        selectByTeacher) {
                    System.out.println(gson.toJson(m));
                }

                System.out.println("---下面是筛选---");

                for (Map m :
                        list) {
                    System.out.println(gson.toJson(m));
                }

                return list;
            }
        });


    }

    @Override
    public void initService(SqlSession session) {
        progressService = new ProgressService(session);
        stageService = new StageService(session);
        checkUnitService = new CheckUnitService(session);
    }
}