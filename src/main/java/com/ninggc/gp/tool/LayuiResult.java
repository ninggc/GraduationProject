package com.ninggc.gp.tool;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ninggc.gp.controller.IController;
import com.ninggc.gp.data.User;
import com.ninggc.gp.util.Constant;
import com.ninggc.gp.util.Log;
import com.ninggc.gp.util.Printer;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 返还到前台的数据格式
 * @param <T> 数据格式
 */
public class LayuiResult<T> {
    private static int CODE_SUCCESS = 0;
    private static int CODE_FAILED = 1;
    private transient Gson gson = Constant.gson;

    private int code;
    private String msg;
    private int count;
    private T data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void success(int count, T t) {
        setCode(CODE_SUCCESS);
        setMsg("");
        setCount(count);
        setData(t);
    }

    public LayuiResult failed(String msg) {
        setCode(CODE_FAILED);
        setMsg(msg);
        setCount(0);
        setData(null);
        return this;
    }

    @Override
    public String toString() {
        return Printer.gson.toJson(this);
    }

    /**
     * 调整为Yanui要求的格式
     * @return Yanui result format
     */
    public String format() {
        String s = toString();
        Log.info("format预览" + s);
        return s;
    }
}
