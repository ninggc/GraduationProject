package com.ninggc.gp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication(scanBasePackages = {"com.ninggc.gp.data", "com.ninggc.gp.mapper"})
@Controller
//SpringBootApplication包含@Configuration,@EnableAutoConfiguration,@ComponentScan三个注解
public class MainApplication implements CommandLineRunner {

//	private final DemoMapper demoMapper;

//	public DemoApplication(DemoMapper demoMapper) {
//		this.demoMapper = demoMapper;
//	}

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);

		ApplicationContext ctx = new ClassPathXmlApplicationContext("bean.xml");

	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("ninggc");
//		System.out.println(demoMapper.findName("ninggc"));
	}

	//	@RequestMapping("/")
//    public ModelAndView home() {
//		return new ModelAndView("/idx.html");
//	}
	@GetMapping("/")
	public String home() {
		return "idx";
	}

	@GetMapping("/idx.html")
	public String idx() {
		return "idx";
	}

	@GetMapping("/index.html")
	public String index() {
		return "idx";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}

	@RequestMapping("/test")
	public String test(ModelMap model) {
		model.addAttribute("test", "test_value");
		model.addAttribute("key", "test_value");
		return "test.html";
	}

}
