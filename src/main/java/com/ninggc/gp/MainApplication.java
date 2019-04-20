package com.ninggc.gp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication(scanBasePackages = {"com.ninggc.gp.data", "com.ninggc.gp.mapper"})
@Controller
//SpringBootApplication包含@Configuration,@EnableAutoConfiguration,@ComponentScan三个注解
public class MainApplication implements CommandLineRunner {

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
		SpringApplication.run(MainApplication.class, args);

		ApplicationContext ctx = new ClassPathXmlApplicationContext("bean.xml");

	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("ninggc");
//		System.out.println(demoMapper.findName("ninggc"));
	}
}
