package com.ninggc.gp.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.ninggc.gp.data.DataSample;
import com.ninggc.gp.data.User;
import com.ninggc.gp.service.UserService;
import com.ninggc.gp.tool.ModelPackage;
import com.ninggc.gp.tool.Result;
import com.ninggc.gp.tool.LayuiResult;
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
@RequestMapping(value = "/user")
public class UserController extends IController {
    int pageSize = 35;
    UserService userService = null;

    @Override
    public void initService(SqlSession session) throws IOException {
        userService = new UserService(session);
    }

    /**
     * 不支持个人注册，由管理员批量导入用户信息
     *
     * @param file 文本内获得用户信息
     * @return
     */
    @RequestMapping(value = "/register", method = {RequestMethod.POST, RequestMethod.GET})
    public String register(@ModelAttribute File file) {

        return "login";
    }

    /**
     * 返回分页用户列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public String list(@ModelAttribute User user, ModelMap map) {
        LayuiResult layuiResult = checkPrivilegeWithNotAllowed(user, "student");
        if (layuiResult != null) {
            return layuiResult.format();
        }

        int defaultIndex = 0;
        try (SqlSession session = openSession()) {
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

        try (SqlSession session = openSession()) {
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

        LayuiResult<Integer> layuiResult = operateData(new OperateHandler<Integer>() {
            @Override
            public Integer onOperate() {
                return userService.update(student);
            }
        }, new LayuiResult<Integer>());

//        try(SqlSession session = openSession()) {
//            initService(session);
//            int update = userService.update(student);
//            session.commit();
//            result.success(toJson(student));
//        } catch (IOException e) {
//            e.printStackTrace();
//            result.failed(e.getMessage());
//        }

        resultPreview(layuiResult);
        return layuiResult.format();
    }

    @ResponseBody
    @RequestMapping(value = "/action/insert")
    public String insert(@SessionAttribute User user, @ModelAttribute User addUser) {
        paramPreview(addUser);

        if (addUser == null || addUser.getAccount() == null || addUser.getPass_word() == null) {
            return LayuiResult.failed("请输入账号和密码", null).format();
        }

        LayuiResult<Integer> layuiResult = operateDate(new OperateHandler<Integer>() {
            @Override
            public Integer onOperate() throws IOException {
                return userService.insert(addUser);
            }
        });

        return layuiResult.format();
    }

    @ResponseBody
    @RequestMapping(value = "/action/delete")
    public String delete(@ModelAttribute ModelPackage modelPackage) {
        Result result = initResult();
        String account = modelPackage.getMsg();

        try (SqlSession session = openSession()) {
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
    public String list(@SessionAttribute User user) {
        LayuiResult checkLayuiResult = checkPrivilegeWithNotAllowed(user, "student");
        if (checkLayuiResult != null) {
            return checkLayuiResult.format();
        }

        LayuiResult<List<User>> layuiResult = operateData(new OperateHandler<List<User>>() {
            @Override
            public List<User> onOperate() {
                return userService.select(DataSample.getStudent());
            }
        }, new LayuiResult<List<User>>());

        return layuiResult.format();
    }

    @RequestMapping("/export")
    public void export(HttpServletResponse response) {
        LayuiResult<List<User>> layuiResult = operateData(new OperateHandler<List<User>>() {
            @Override
            public List<User> onOperate() {
                return userService.select(DataSample.getStudent());
            }
        }, new LayuiResult<List<User>>());

        List<User> list = layuiResult.getData();

        ExportParams params = new ExportParams();
        params.setType(ExcelType.XSSF);
        Workbook sheets = ExcelExportUtil.exportExcel(params, User.class, list);
        ExcelUtil.downloadExcel(response, sheets, "excel_export");
    }

    @RequestMapping("/test")
    public String test() {
        return "page/user/test";
    }

    @ResponseBody
    @RequestMapping("/layui/userinfo")
    public String userinfo(@SessionAttribute User user) {
        LayuiResult<User> layuiResult = operateDate(new OperateHandler<User>() {
            @Override
            public User onOperate() {
                return userService.selectOne(new User().setAccount(user.getAccount()));
            }
        });

//        过滤掉密码
        if (layuiResult.getData() != null) {
            layuiResult.getData().setPass_word("");
        }

        return layuiResult.format();
    }

    @ResponseBody
    @RequestMapping("/layui/update/password")
    public String updatePassword(@SessionAttribute User user, @RequestParam String old_password, @RequestParam String new_password) {
        if (!old_password.equals(user.getPass_word())) {
            return new LayuiResult<>().failed("密码输入错误").format();
        }

        LayuiResult<Integer> layuiResult = operateDate(new OperateHandler<Integer>() {
            @Override
            public Integer onOperate() {
                return userService.updatePassword(user.getAccount(), new_password);
            }
        });

        return layuiResult.format();
    }
}
