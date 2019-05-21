package com.ninggc.gp.data;

public class CheckUnit extends IEntity {
    private Integer id;
    private Integer process_id;
    private Integer stage_id;
    private String name;
    private Byte pass;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProcess_id() {
        return process_id;
    }

    public CheckUnit setProcess_id(Integer process_id) {
        this.process_id = process_id;
        return this;
    }

    public Integer getStage_id() {
        return stage_id;
    }

    public void setStage_id(Integer stage_id) {
        this.stage_id = stage_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPass(Byte pass) {
        this.pass = pass;
    }

    public byte getPass() {
        return pass;
    }

    public void setPass(byte pass) {
        this.pass = pass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}