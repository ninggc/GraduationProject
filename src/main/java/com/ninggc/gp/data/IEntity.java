package com.ninggc.gp.data;

import com.google.gson.Gson;

abstract class IEntity {

    transient Gson gson = new Gson();

    public String toJson() {
        return gson.toJson(this);
    }
}
