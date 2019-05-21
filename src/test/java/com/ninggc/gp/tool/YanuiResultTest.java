package com.ninggc.gp.tool;

import com.google.gson.Gson;
import com.ninggc.gp.data.User;
import org.junit.Test;

import java.util.ArrayList;

import static com.ninggc.gp.Constant.online;

public class YanuiResultTest {

    @Test
    public void toResult() {

        if (online) {
            return;
        }

//        {
//            "status": 0,
//                "message": "",
//                "total": 180,
//                "data": {
//            "item": [{}, {}]
//        }
        YanuiResult<User> result = new YanuiResult<>();

//        result.setStatus(200);
//        result.setMsg("");
//        result.setTotal(10);
//        ArrayList<User> users = new ArrayList<>();
//        users.add(new User().setAccount("1"));
//        users.add(new User().setAccount("2"));
//        result.setDataByList(users);

        System.out.println(new Gson().toJson(result));
    }
}