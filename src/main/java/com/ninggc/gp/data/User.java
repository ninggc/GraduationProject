package com.ninggc.gp.data;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.sql.Timestamp;

public class User extends IEntity {
    @Excel(name = "账号", width = 20)
    private String account;
    /**
     * 计算后的md5
     */
    @Excel(name = "密码")
    private transient String pass_word;
    @Excel(name = "姓名")
    private String name;
    /**
     * "student"
     * "teacher"
     * "manager"
     */
    @Excel(name = "身份")
    private String addition;
    private transient Byte visible;
    @Excel(name = "更新时间", format =  "yy-MM-dd hh:mm:ss", width = 40)
    private Timestamp update_time;

//    public User(){};
//
//    public User(String account, String name) {
//        this.account = account;
//        this.name = name;
//    }


    public String getAccount() {
        return account;
    }

    public User setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getPass_word() {
        return pass_word;
    }

    public void setPass_word(String pass_word) {
        this.pass_word = pass_word;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }

    public Byte getVisible() {
        return visible;
    }

    public void setVisible(Byte visible) {
        this.visible = visible;
    }

    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }
}
