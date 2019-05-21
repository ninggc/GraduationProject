package com.ninggc.gp.data;

import com.google.gson.Gson;
import com.ninggc.gp.util.Printer;

abstract class IEntity {

    static transient Gson gson = Printer.gson;

    @Deprecated
    public String toJson() {
        return gson.toJson(this);
    }

    public static String toJson(Object o) {
        return gson.toJson(o);
    }
}
