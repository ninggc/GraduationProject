package com.ninggc.gp.data;

import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

public class Progress extends IEntity {
    private Integer id;
    private String account;
    private Integer process_id;
    private String data;

    public Map<Integer, Byte> parseFromData() {
        Map<Integer, Byte> map = gson.fromJson(data, new TypeToken<Map<Integer, Byte>>() {}.getType());
        return map == null ? new HashMap<>() : map;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
