package com.ninggc.gp;

import com.ninggc.gp.data.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainApplicationTests {

	@Test
	public void contextLoads() {
		System.out.println("hello");
//		String s = "[{\"sort\": \"53\", \"user_id\": \"2\", \"name\": \"衣服\", \"price\": \"50\", \"note\": \"一件衣服\", \"img2\": \"1\", \"img3\": \"1\", \"mail\": \"110100\", \"img1\": \"1\", \"id\": 2}, {\"sort\": \"52\", \"user_id\": \"2\", \"name\": \"书本\", \"price\": \"10\", \"note\": \"这是一本书\", \"img2\": \"1\", \"img3\": \"1\", \"mail\": \"110100\", \"img1\": \"1\", \"id\": 1}]";
//		System.out.println(s);
//		List<String> list =  new Gson().fromJson(s, new TypeToken<List<String>>(){}.getType());
//		System.out.println(list.size());

		ApplicationContext ctx = new ClassPathXmlApplicationContext("bean.xml");
		User user = ctx.getBean("ning", User.class);
		System.out.println(user.toJson());
	}

}
