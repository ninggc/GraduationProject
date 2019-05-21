package com.ninggc.gp.controller;

import com.ninggc.gp.data.User;
import com.ninggc.gp.util.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Controller
@RequestMapping("/test/")
public class UtilController extends IController {

    @RequestMapping(value = {"/debug"}, method = RequestMethod.POST)
    public String error(@RequestParam String msg, ModelMap map) {

        msg = msg == null ? "没有错误信息" : msg;
        map.addAttribute("msg", msg);
        Log.mapperRequest("/debug");
        return "debug";
    }

    @RequestMapping(value = "testSession")
    public String testSession(@SessionAttribute User user, HttpSession session) {
        String json = "";
        json += gson.toJson(user);

        return json;
    }

    @ResponseBody
    @RequestMapping("showSession")
    public String show(HttpSession session) {
        StringBuilder json = new StringBuilder();
        Enumeration<String> e = session.getAttributeNames();

        while (e.hasMoreElements()) {
            String s = e.nextElement();
            String s1 = s + " == " + session.getAttribute(s) + "  \n";
            json.append(s1);
        }
        return String.valueOf(json);
    }

    @ResponseBody
    @RequestMapping("/info")
    public String info(@ModelAttribute User user) {
        String s = gson.toJson(user);
        Log.debug(s);
        return s;
    }

//    @ResponseBody
//    @RequestMapping("set")
//    public String setSession(HttpSession session) {
//        session.setAttribute("id", 123);
//        return "set";
//    }
//
//    @ResponseBody
//    @RequestMapping("get")
//    public String getSession(HttpSession session) {
//        return (String) session.getAttribute("id");
//    }
}
