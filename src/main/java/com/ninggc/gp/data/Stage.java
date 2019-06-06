package com.ninggc.gp.data;

import java.util.List;

public class Stage extends IEntity {
    private Integer id;
    private Integer process_id;
    private Short sequence;
    private String name;
    private Byte pass;
    private List<CheckUnit> unitList;

    public Integer getId() {
        return id;
    }

    public Stage setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getProcess_id() {
        return process_id;
    }

    public Stage setProcess_id(Integer process_id) {
        this.process_id = process_id;
        return this;
    }

    public Short getSequence() {
        return sequence;
    }

    public void setSequence(Short sequence) {
        this.sequence = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getPass() {
        return pass;
    }

    public void setPass(Byte pass) {
        this.pass = pass;
    }

    public List<CheckUnit> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<CheckUnit> unitList) {
        this.unitList = unitList;
    }
}

