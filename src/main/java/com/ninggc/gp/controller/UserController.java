package com.ninggc.gp.controller;

import com.ninggc.gp.data.DataSample;
import com.ninggc.gp.data.User;
import com.ninggc.gp.service.UserService;
import com.ninggc.gp.tool.ModelPackage;
import com.ninggc.gp.tool.Result;
import com.ninggc.gp.util.Log;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "user")
public class UserController implements IController {
    int pageSize = 35;
    UserService userService = null;

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
    @RequestMapping(value = "/update")
    public String update(@ModelAttribute User student) {
        Result result = initResult();

        try(SqlSession session = openSession()) {
            initService(session);
            int update = userService.update(student);
            session.commit();
            result.success(toJson(student));
        } catch (IOException e) {
            e.printStackTrace();
            result.failed(e.getMessage());
        }

        System.out.println(toJson(result));
        return toJson(result);
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public String delete(@ModelAttribute ModelPackage modelPackage) {
        Result result = initResult();
        String account = modelPackage.getMsg();

        try(SqlSession session = openSession()) {
            initService(session);
            int update = userService.delete(account);
            session.commit();
            result.success(toJson(update));
        } catch (IOException e) {
            e.printStackTrace();
            result.failed(e.getMessage());
        }

        System.out.println(toJson(result));
        return toJson(result);
    }

    @Override
    public void initService(SqlSession session) throws IOException {
        userService = new UserService(session);
    }
}
