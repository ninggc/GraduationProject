package com.ninggc.bs;

import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class Sample {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "hello";
    }

    public static void main(String[] args) throws Exception{
        SpringApplication.run(Sample.class, args);
    }
}
