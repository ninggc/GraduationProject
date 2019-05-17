package com.ninggc.gp.data;

import com.google.gson.Gson;

abstract class IEntity {

    static transient Gson gson = new Gson();

    @Deprecated
    public String toJson() {
        return gson.toJson(this);
    }

    public static String toJson(Object o) {
        return gson.toJson(o);
    }
}
