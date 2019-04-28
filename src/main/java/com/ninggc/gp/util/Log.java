package com.ninggc.gp.util;

public class Log {
    private static final String PROGECT_NAME = "gp";

    public static void print(String msg) {
        System.out.println(msg);
    }

    public static String debug(String msg) {
        print(msg);
        return msg;
    }

    public static String info(String msg) {
        String x = PROGECT_NAME + " - INFO: " + msg;
        print(x);
        return msg;
    }

    public static String warning(String msg) {
        String x = PROGECT_NAME + " - WARNING: " + msg;
        print(x);
        return msg;
    }

    public static String error(String msg) {
        String x = PROGECT_NAME + " - ERROR: " + msg;
        print(x);
        return msg;
    }
}
