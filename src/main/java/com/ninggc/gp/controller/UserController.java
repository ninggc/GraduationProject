package com.ninggc.gp.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.google.gson.reflect.TypeToken;
import com.ninggc.gp.data.DataSample;
import com.ninggc.gp.data.User;
import com.ninggc.gp.service.UserService;
import com.ninggc.gp.tool.ModelPackage;
import com.ninggc.gp.tool.Result;
import com.ninggc.gp.tool.YanuiResult;
import com.ninggc.gp.util.ExcelUtil;
import com.ninggc.gp.util.Log;
import org.apache.ibatis.session.SqlSession;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "user")
public class UserController extends IController {
    int pageSize = 35;
    UserService userService = null;

    @Override
    public void initService(SqlSession session) throws IOException {
        userService = new UserService(session);
    }

    /**
     * 不支持个人注册，由管理员批量导入用户信息
     * @param file 文本内获得用户信息
     * @return
     */
    @RequestMapping(value = "/register", method = {RequestMethod.POST, RequestMethod.GET})
    public String register(@ModelAttribute File file) {

        return "login";
    }

    /**
     * 返回分页用户列表
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public String list(@ModelAttribute User user, ModelMap map) {
        int defaultIndex = 0;
        try(SqlSession session = openSession()) {
            initService(session);

            List<User> list = userService.selectWithLimit(DataSample.getStudent(), defaultIndex, pageSize);

            map.addAttribute("list", list);
            map.addAttribute("index", defaultIndex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "student_manage";
    }

    @ResponseBody
    @RequestMapping(value = "/action/nextPage", method = RequestMethod.GET)
    public String next(@ModelAttribute ModelPackage modelPackage) {
        Result result = initResult();
        int index = modelPackage.getNumber() + pageSize;

        try(SqlSession session = openSession()) {
            initService(session);
            List<User> list = userService.selectWithLimit(DataSample.getStudent(), index, pageSize);
            result.success(toJson(list));

        } catch (IOException e) {
            e.printStackTrace();
            result.failed(e.getMessage());
        }

        Log.debug(toJson(result));
        return toJson(result);
    }

    @ResponseBody
    @RequestMapping(value = "/action/update")
    public String update(@RequestBody User student) {
        paramPreview(student);
        Result result = initResult();

        result = operateData(new OperateHandler<Integer>() {
            @Override
            public Integer onOperate() {
                return userService.update(student);
            }
        });

//        try(SqlSession session = openSession()) {
//            initService(session);
//            int update = userService.update(student);
//            session.commit();
//            result.success(toJson(student));
//        } catch (IOException e) {
//            e.printStackTrace();
//            result.failed(e.getMessage());
//        }

        resultPreview(result);
        return toJson(result);
    }

    @ResponseBody
    @RequestMapping(value = "/action/delete")
    public String delete(@ModelAttribute ModelPackage modelPackage) {
        Result result = initResult();
        String account = modelPackage.getMsg();

        try(SqlSession session = openSession()) {
            initService(session);
            int delete = userService.delete(account);
            session.commit();
            result.success(toJson(delete));
        } catch (IOException e) {
            e.printStackTrace();
            result.failed(e.getMessage());
        }

        Log.debug(toJson(result));
        return toJson(result);
    }

    @RequestMapping("/page/manage")
    public String manage() {
        return "page/user/manage.html";
    }

    @ResponseBody
    @RequestMapping("/action/list")
    public String list() {
        final List<User>[] users = new List[]{null};

        Result result = operateData(new OperateHandler<List<User>>() {
            @Override
            public List<User> onOperate() {
                List<User> select = userService.select(DataSample.getStudent());
                users[0] = select;
                return select;
            }
        });
        YanuiResult<User> yanuiResult = new YanuiResult<>();
        yanuiResult.success(users[0].size(), users[0]);
        return yanuiResult.format();
    }

    @RequestMapping("/export")
    public void export(HttpServletResponse response) {
        Result result = operateData(new OperateHandler<List<User>>() {
            @Override
            public List<User> onOperate() {
                return userService.select(DataSample.getStudent());
            }
        });

        List<User> list = gson.fromJson(result.getData(), new TypeToken<List<User>>(){}.getType());

        ExportParams params = new ExportParams();
        params.setType(ExcelType.XSSF);
        Workbook sheets = ExcelExportUtil.exportExcel(params, User.class, list);
        ExcelUtil.downloadExcel(response, sheets, "excel_export");
    }

    @RequestMapping("/test")
    public String test() {
        return "page/user/test";
    }
}
