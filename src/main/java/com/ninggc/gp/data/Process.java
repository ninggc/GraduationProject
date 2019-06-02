package com.ninggc.gp.data;

import java.util.List;

public class Process extends IEntity {
    private Integer id;
    private Integer progress_id;
    private String name;
    private String start_time;
    private String description;
//    msg和files是需要填写的信息字段和需要提交的文件名，json格式存储
    private String msg;
    private String files;

    private List<Stage> stageList;

    public Integer getId() {
        return id;
    }

    public Process setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getProgress_id() {
        return progress_id;
    }

    public void setProgress_id(Integer progress_id) {
        this.progress_id = progress_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Stage> getStageList() {
        return stageList;
    }

    public void setStageList(List<Stage> stageList) {
        this.stageList = stageList;
    }
}
