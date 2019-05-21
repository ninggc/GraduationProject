package com.ninggc.gp.tool;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ninggc.gp.controller.IController;
import com.ninggc.gp.data.User;
import com.ninggc.gp.util.Constant;
import com.ninggc.gp.util.Printer;

import java.lang.reflect.Type;
import java.util.List;

public class YanuiResult<T> {
    private static int CODE_SUCCESS = 0;
    private static int CODE_FAILED = 1;
    private transient Gson gson = Constant.gson;

    private int code;
    private String msg;
    private int count;
    private T entity;
    private List<T> data;


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

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void success(int count, List<T> list) {
        setCode(CODE_SUCCESS);
        setMsg("");
        setCount(count);
        setData(list);
    }

    public void success(String json) {
        setCode(CODE_SUCCESS);
        setMsg("");
        try {
            List<T> data = gson.fromJson(json, new TypeToken<List<T>>() {}.getType());
            setCount(data.size());
            setData(data);
        } catch (IllegalStateException ignored) {
            ignored.printStackTrace();
        }
    }

    public void success(String json, Type type) {
        setCode(CODE_SUCCESS);
        setMsg("");
        try {
            setEntity(gson.fromJson(json, type));
//            List<T> data = gson.fromJson(json, new TypeToken<List<T>>() {}.getType());
//            setCount(data.size());
//            setData(data);
        } catch (IllegalStateException ignored) {
            ignored.printStackTrace();
        }
    }

    public void filed() {
        setCode(CODE_FAILED);
        setMsg("");
        setCount(0);
        setData(null);
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
        return toString();
    }
}
