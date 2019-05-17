package com.ninggc.gp.controller;

import com.ninggc.gp.data.CheckUnit;
import com.ninggc.gp.data.Progress;
import com.ninggc.gp.data.User;
import com.ninggc.gp.data.UtilPass;
import com.ninggc.gp.service.CheckUnitService;
import com.ninggc.gp.service.ProgressService;
import com.ninggc.gp.tool.Result;
import com.ninggc.gp.util.Log;
import com.ninggc.gp.util.Printer;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RequestMapping("/progress")
@Controller
public class ProgressController implements IController {
    ProgressService progressService = null;
    CheckUnitService checkUnitService = null;

    @Override
    public void initService(SqlSession session) throws IOException {
        progressService = new ProgressService(session);
        checkUnitService = new CheckUnitService(session);
    }

    @RequestMapping(value = "/showList")
    public String show(@SessionAttribute User user, ModelMap map) {
        try(SqlSession session = openSession()) {
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

        try(SqlSession session = openSession()) {
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
    @RequestMapping(value = "/checkUnit")
    public String passUnit(@ModelAttribute Progress progress, @RequestParam int unit_id, @RequestParam byte pass) {
        try(SqlSession session = openSession()) {
           initService(session);
            Progress selectOne = progressService.selectOne(progress);
            if (progress != null) {
                Map<Integer, Byte> data = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "json";
    }


    /**
     *
     * @param progress
     * @param utilPass
     * @return
     */
    @ResponseBody
//    @RequestMapping("/progress/update")
    @RequestMapping("/update")
    public String update(@ModelAttribute Progress progress, @ModelAttribute UtilPass utilPass) {

        Log.debug(gson.toJson(progress));
        Log.debug(gson.toJson(utilPass));

        Result result = initResult();
        try (SqlSession session = openSession()) {
            initService(session);

//            从数据库获取进度
            Progress selectOne = progressService.selectOne(new Progress().setAccount(progress.getAccount()).setProcess_id(progress.getProcess_id()));
            Log.debug(gson.toJson(selectOne));

//            将数据库中新增的unit添加到progress
            Map<Integer, Byte> map = synchronize(selectOne);
//            更新当前请求
            map.replace(utilPass.getUnit_id(), utilPass.getPass());
            selectOne.setData(gson.toJson(map));

//            写回到数据库
            progressService.update(selectOne);
            session.commit();
            result.setCode(CODE_SUCCESS);

        } catch (IOException e) {
            e.printStackTrace();
            Log.error("更新失败");
            result.setCode(CODE_FAILED);
        }
        return gson.toJson(result);
    }

//            从数据库获取同步所有的unit id（未写回数据库）
    private Map<Integer, Byte> synchronize(Progress progress) {
        int process_id = progress.getProcess_id();

        Set<Integer> allUnitId = getAllUnitId(checkUnitService, process_id);

        Map<Integer, Byte> map = progress.parseFromData();
        if (map.keySet().size() != allUnitId.size()) {
//                比较两个set获取处理不同的元素
            allUnitId.removeAll(map.keySet());
//                将data中没有的id加入进去
            for (int v : allUnitId) {
                map.put(v, (byte) 0);
            }
        }
        Log.debug(gson.toJson(map));
        return map;
    }

    //        从数据库获取所有unit id
    private Set<Integer> getAllUnitId(CheckUnitService service, int id) {
        List<CheckUnit> selectByProcessId = checkUnitService.selectByProcessId(id);
        Set<Integer> unitIdList = new HashSet<>();
        for (CheckUnit v :
                selectByProcessId) {
            unitIdList.add(v.getId());
        }
        return unitIdList;
    }

}
