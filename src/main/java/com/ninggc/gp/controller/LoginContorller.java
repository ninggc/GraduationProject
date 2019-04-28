package com.ninggc.gp.controller;

import com.ninggc.gp.data.User;
import com.ninggc.gp.mybatis.Factory;
import com.ninggc.gp.service.UserService;
import com.ninggc.gp.util.Log;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class LoginContorller {
    UserService service = null;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute User user) {
        System.out.println(user.toJson());

        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute User user) {
        Log.debug(user.toJson());

        try (SqlSession session = Factory.openSession()) {
            service = new UserService(session);
            List<User> userList = service.select(new User().setAccount(user.getAccount()));
            if (userList == null || userList.size() == 0) {
                return "failed";
            }
            User select = userList.get(0);
            if (user.getPass_word().equals(select.getPass_word())) {
                Log.info("密码正确");
                return "success";
            } else {
                return "failed";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            Log.info("没有匹配项");
//            e.printStackTrace();
        }

        return "login";
    }

    @RequestMapping(value = "success")
    @ResponseBody
    public String success(@RequestAttribute String msg) {
        return "登陆成功";
    }

    @RequestMapping(value = "failed")
    @ResponseBody
    public String failed(@RequestAttribute String msg) {
        return "登陆失败";
    }
}
