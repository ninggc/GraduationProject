package com.ninggc.gp;

import com.ninggc.gp.data.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainApplicationTests {

	public static ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void contextLoads() {
//		String s = "[{\"sort\": \"53\", \"user_id\": \"2\", \"name\": \"衣服\", \"price\": \"50\", \"note\": \"一件衣服\", \"img2\": \"1\", \"img3\": \"1\", \"mail\": \"110100\", \"img1\": \"1\", \"id\": 2}, {\"sort\": \"52\", \"user_id\": \"2\", \"name\": \"书本\", \"price\": \"10\", \"note\": \"这是一本书\", \"img2\": \"1\", \"img3\": \"1\", \"mail\": \"110100\", \"img1\": \"1\", \"id\": 1}]";
//		System.out.println(s);
//		List<String> list =  new Gson().fromJson(s, new TypeToken<List<String>>(){}.getType());
//		System.out.println(list.size());

//		ClassPathXmlApplicationContext：从类路径中加载配置文件
//		FileSystemXmlApplicationContext：从文件系统中来加载配置文件
//		ConfigurableApplicationContext：扩展自ApplicationContext，新增加了两个方法reflesh、close
		User user = context.getBean("ning", User.class);
		System.out.println(user.toJson());
	}

	@Test
	public void showBeans() {
		String[] names = context.getBeanDefinitionNames();
		for (String s :
				names) {
			System.out.println(s);
		}
	}

}
