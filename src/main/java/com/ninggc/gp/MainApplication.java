package com.ninggc.gp;

import com.ninggc.gp.data.User;
import com.ninggc.gp.util.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(scanBasePackages = {"com.ninggc.gp.data", "com.ninggc.gp.mapper", "com.ninggc.gp.controller"})
@Controller
//@EnableCaching
//SpringBootApplication包含@Configuration,@EnableAutoConfiguration,@ComponentScan三个注解
public class MainApplication implements CommandLineRunner, WebMvcConfigurer {

//	private final DemoMapper demoMapper;

//	public DemoApplication(DemoMapper demoMapper) {
//		this.demoMapper = demoMapper;
//	}

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);

		ApplicationContext ctx = new ClassPathXmlApplicationContext("bean.xml");

	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration ir = registry.addInterceptor(new LoginInterceptor());
		ir.addPathPatterns("/**");
		ir.excludePathPatterns("/login", "/css/**", "/img/**", "/js/**", "/mail/**", "/scss/**", "/vendor/**");
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
	public String home(@SessionAttribute User user) {
		if (user == null) {
			return "login";
		}

		return "idx";
	}

	@GetMapping("/idx.html")
	public String idx(@SessionAttribute User user) {
		if (user == null) {
			return "login";
		}

		return "idx";
	}
//
//	@GetMapping("/index.html")
//	public String index() {
//		System.out.println("index.html");
//		return "idx";
//	}

	@RequestMapping(value = {"about","about.html"})
	public String about() {
		return "about";
	}


//	@RequestMapping("/test")
//	public String test(ModelMap model) {
//		model.addAttribute("key", "auto_another_deploy");
//		return "test.html";
//	}

}
