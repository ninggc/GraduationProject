package com.ninggc.gp.data;

public class UtilPass {
    private Integer process_id;
    private Integer stage_id;
    private Integer unit_id;
    private Byte pass;
    private String description;

    public Integer getProcess_id() {
        return process_id;
    }

    public void setProcess_id(Integer process_id) {
        this.process_id = process_id;
    }

    public Integer getStage_id() {
        return stage_id;
    }

    public void setStage_id(Integer stage_id) {
        this.stage_id = stage_id;
    }

    public Integer getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(Integer unit_id) {
        this.unit_id = unit_id;
    }

    public Byte getPass() {
        return pass;
    }

    public UtilPass setPass(Byte pass) {
        this.pass = pass;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
