package com.ninggc.gp.data;

import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

public class Progress extends IEntity {
    private Integer id;
    private String account;
    private Integer process_id;
    private String data;

    private String msg;
    private String files;
    private Integer current_sequence;
    private Byte finish;

    public Map<Integer, UtilPass> parseFromData() {
        Map<Integer, UtilPass> map = gson.fromJson(data, new TypeToken<Map<Integer, UtilPass>>() {}.getType());
        return map == null ? new HashMap<>() : map;
    }

    public Integer getId() {
        return id;
    }

    public Progress setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public Progress setAccount(String account) {
        this.account = account;
        return this;
    }

    public Integer getProcess_id() {
        return process_id;
    }

    public Progress setProcess_id(Integer process_id) {
        this.process_id = process_id;
        return this;
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

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public Integer getCurrent_sequence() {
        return current_sequence;
    }

    public void setCurrent_sequence(Integer current_sequence) {
        this.current_sequence = current_sequence;
    }

    public Byte getFinish() {
        return finish;
    }

    public void setFinish(Byte finish) {
        this.finish = finish;
    }

    public void sequenceIncrease(int addition) {
        if (current_sequence != null) {
            this.current_sequence += addition;
        } else {
            throw new RuntimeException("progress的current_sequence为空？？？");
        }
    }
}
