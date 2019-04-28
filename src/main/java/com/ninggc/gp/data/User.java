package com.ninggc.gp.data;

public class User extends IEntity {
    private String account;
    /**
     * 计算后的md5
     */
    private String pass_word;
    private String name;
    private String addition;

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
}
