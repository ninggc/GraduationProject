package com.ninggc.gp.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Printer {
    public static Gson gson = new GsonBuilder().setDateFormat("yy-MM-dd hh:mm:ss").create();

    public static String toJson(Object o) {
        return gson.toJson(o);
    }

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd hh:mm:ss");

    public static String format(Timestamp timestamp) {
        return simpleDateFormat.format(timestamp);
    }


    public static void print(Timestamp timestamp) {
        System.out.println(format(timestamp));
    }
}
