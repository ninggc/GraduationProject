package com.ninggc.gp.util.AboutExcel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.ninggc.gp.data.User;

import java.io.Serializable;

public class ExcelUser implements Serializable {
    @Excel(name = "账号", width = 20)
    private String account;
    /**
     * 计算后的md5
     */
    @Excel(name = "密码")
    private String pass_word;
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

    public ExcelUser(){};

    public ExcelUser(User user) {
        this.account = user.getAccount();
        this.name = user.getName();
        this.pass_word = user.getPass_word();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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
}
