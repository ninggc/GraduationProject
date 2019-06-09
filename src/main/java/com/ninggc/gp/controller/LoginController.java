package com.ninggc.gp.controller;

import com.ninggc.gp.data.User;
import com.ninggc.gp.mybatis.Factory;
import com.ninggc.gp.service.UserService;
import com.ninggc.gp.tool.LayuiResult;
import com.ninggc.gp.util.Log;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class LoginController extends IController {
    UserService service = null;

    @RequestMapping(value = "/register", method = {RequestMethod.POST, RequestMethod.GET})
    public String register(@ModelAttribute User user) {
        System.out.println(user.toJson());

        return "login";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String login(@ModelAttribute User user) {
        return "login";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public String login(@ModelAttribute User user, ModelMap map, HttpSession httpSession) {

        if (user == null || "".equals(user.getName())) {
            return "failed";
        }

        Log.debug(user.toJson());
        try (SqlSession session = Factory.openSession()) {
            service = new UserService(session);
            List<User> userList = service.select(new User().setAccount(user.getAccount()));
            if (userList == null || userList.size() == 0) {
                return "failed";
            }
            User currentUser = userList.get(0);
            if (currentUser.getPass_word().equals(user.getPass_word())) {
                Log.info("密码正确");
                map.addAttribute("user", currentUser);
                httpSession.setAttribute("user", currentUser);
                String preurl = (String) httpSession.getAttribute("preurl");
                if (preurl == null || "".equals(preurl)) {
                    return preurl;
                } else {
                    return "index";
                }
            } else {
                return "failed";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        } catch (NullPointerException e) {
            Log.info("没有匹配项");
            return "failed";
        }
    }


    @ResponseBody
    @RequestMapping("/logout")
    public String logout(@SessionAttribute User user, HttpSession httpSession) {
        LayuiResult<String> layuiResult = operateDate(new OperateHandler<String>() {
            @Override
            public String onOperate() throws IOException {
                httpSession.setAttribute("user", null);
                return "logout";
            }
        });

        return layuiResult.format();
    }

    @ResponseBody
    @RequestMapping("get")
    public String getSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return gson.toJson(user);
    }
}
