package com.ninggc.gp.data;

public class File {
    private Integer id;
    private Integer process_id;
    private String filename;
    private Short version;
    private String md5;

    private String account;
    private String location;

    public Integer getId() {
        return id;
    }

    public File setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getProcess_id() {
        return process_id;
    }

    public void setProcess_id(Integer process_id) {
        this.process_id = process_id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
