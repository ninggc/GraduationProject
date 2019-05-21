package com.ninggc.gp.tool;

import com.ninggc.gp.controller.IController;

public class Result {
//    状态码，CODE对应值详见IController
    private int code;
//    结果，一般为json格式
    private String data;
//    消息
    private String msg;

    public void success(String data) {
        setCode(IController.CODE_SUCCESS);
        setData(data);
    }

    public void success(String data, String msg) {
        success(data);
        setMsg(msg);
    }

    public void failed(String msg) {
        setCode(IController.CODE_FAILED);
        setMsg(msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
