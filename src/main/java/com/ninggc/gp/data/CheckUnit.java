package com.ninggc.gp.data;

public class CheckUnit extends IEntity {
    private Integer id;
    private Integer process_id;
    private Integer stage_id;
    private String name;
    private Byte pass;
    private String description;

    private String progress_description;
    private String roleName;

    private Integer role_id;

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

    public CheckUnit setStage_id(Integer stage_id) {
        this.stage_id = stage_id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getProgress_description() {
        return progress_description;
    }

    public void setProgress_description(String progress_description) {
        this.progress_description = progress_description;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }
}
