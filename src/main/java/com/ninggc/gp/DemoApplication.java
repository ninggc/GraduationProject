package com.ninggc.gp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
//@EnableAutoConfiguration
public class DemoApplication implements CommandLineRunner {

//	private final DemoMapper demoMapper;

//	public DemoApplication(DemoMapper demoMapper) {
//		this.demoMapper = demoMapper;
//	}

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "hello";
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("ninggc");
//		System.out.println(demoMapper.findName("ninggc"));
	}
}
