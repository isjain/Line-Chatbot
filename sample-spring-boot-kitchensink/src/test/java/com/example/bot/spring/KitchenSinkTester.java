package com.example.bot.spring;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.io.ByteStreams;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.MessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.LineBotMessages;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import com.example.bot.spring.DatabaseEngine;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = { KitchenSinkTester.class, UserInputDatabaseEngine.class })
public class KitchenSinkTester {
	@Autowired
	private UserInputDatabaseEngine databaseEngine;

	private RecommendationDatabaseEngine recomDB;

//	@Test
//	public void testNotFound() throws Exception {
//		boolean thrown = false;
//		try {
//			
//			this.databaseEngine.search("no");
//		} catch (Exception e) {
//			thrown = true;
//		}
//		assertThat(thrown).isEqualTo(true);
//	}

//	
//	@Test
//	public void testFound2() throws Exception {
//		boolean thrown = false;
//		String userID = "testing";
//		User new_user = new User(userID);
//		new_user.setWeight(62);
//		new_user.setHeight(173);
//		new_user.setAge(21);
//		new_user.setRestrictions("chicken,beef");
//		new_user.setGender("male");
//		new_user.setLostGainPerWeek(0);
//		new_user.setGymFrequency(2);
//		new_user.setName("Gregor");
////		new_user.setWaterReminder(2);
//		String inputData = "noodles and soup,chicken with rice,beef buns,green salad,apples";
//		Dish[] recommended_dishes = null;
//		Dish[] dishes2 = null;
//		Dish[] final_dishes= null;
//		Recommendation recommend;
//		try {
////			result = this.databaseEngine.search("Hi");
//			String[] menu = inputData.split(",");
//        	List<Dish> dishes = new ArrayList<Dish>();
//        	for (String str: menu)
//        	{
//        		dishes.add(new Dish(str));
//        	}
//        		dishes2 = dishes.toArray(new Dish[dishes.size()]);
//        		final_dishes = recomDB.findCaloricContent(dishes2);
//        	
//        	recommend = new Recommendation(new_user, dishes2);
//        	recommended_dishes = recommend.getRecommendedDishes();
//			
//		} catch (Exception e) {
//			thrown = true;
//		}
//		assertThat(recommended_dishes.length).isEqualTo("3");
////		assertThat(recommended_dishes[0].getName()).isEqualTo("apples");
////		assertThat(recommended_dishes[1].getName()).isEqualTo("noodles and soup");
////		assertThat(recommended_dishes[2].getName()).isEqualTo("green salad");
//	}
	
//	@Test
//	public void testFound2() throws Exception {
//		boolean thrown = false;
//		String result = null;
//		try {
//			result = this.databaseEngine.search("Hi");
//		} catch (Exception e) {
//			thrown = true;
//		}
//		assertThat(!thrown).isEqualTo(true);

//		assertThat(result).isEqualTo("Hey, how things going?");
//	}
	
	@Test
	public void testFound() throws Exception {
		boolean thrown = false;
		User result = null;
		String userId = "test";
		User new_user = new User(userId);
		try {
			
			this.databaseEngine.CreateNewUser(new_user);
			this.databaseEngine.updateWeight(userId, 65);
			this.databaseEngine.updateHeight(userId, 172);
			this.databaseEngine.updateGymFrequency(userId, 4);
			this.databaseEngine.updateLoseGain(userId, 5);
			this.databaseEngine.updateAge(userId, 21);
//			this.databaseEngine.updateWaterReminder(userId, 3);
			this.databaseEngine.updateUserName(userId, "abcd");
			this.databaseEngine.updateGender(userId, "male");
			this.databaseEngine.updateReqCalDay(userId);
			
		
			result = this.databaseEngine.getUserRecord("heylo");
			result.setBMR();
			result.setCalDay();
			result.setBMI();
			
			float bmr1=this.databaseEngine.calcBMI("test");
			float bmr2=this.databaseEngine.calcBMR("test");
			this.databaseEngine.updateCalperDay("heylo","200");
			this.databaseEngine.setBMI("test");
			this.databaseEngine.setBMR("test");
			this.databaseEngine.updateRestrictions("test","chicken");
			this.databaseEngine.searchUser("heylo");
			double db = this.databaseEngine.getRequiredCalories("heylo");
			
		} catch (Exception e) {
			thrown = true;
		}
		assertThat(result.getWeight()).isEqualTo("65");
		assertThat(result.getHeight()).isEqualTo("172");
		assertThat(result.getGymFrequency()).isEqualTo("4");
		assertThat(result.getLoseGainPerWeek()).isEqualTo("5");
		assertThat(result.getAge()).isEqualTo("21");
//		assertThat(result.getWaterReminder()).isEqualTo("3");
		assertThat(result.getName()).isEqualTo("abcd");
		assertThat(result.getGender()).isEqualTo("male");
//		assertThat(result.getGender()).isEqualTo("male");
		
//		assertThat(result.getCalDay()).isEqualTo("210");
		
		
	//}

}
	
	@Test
	public void testFound1() throws Exception {
		boolean thrown = false;
		String result="";
		String userId = "test";
		User new_user = new User(userId);
//		KitchenSinkController ks;
		
		
		try {
			JsonTest jst = new JsonTest();
			Dish[] dishes2 = jst.getJSONlistweb("https://api.myjson.com/bins/d4t4b");
			
			this.databaseEngine.CreateNewUser(new_user);
			this.databaseEngine.updateWeight(userId, 65);
			this.databaseEngine.updateHeight(userId, 172);
			this.databaseEngine.updateGymFrequency(userId, 4);
			this.databaseEngine.updateLoseGain(userId, 5);
			this.databaseEngine.updateAge(userId, 21);
//			this.databaseEngine.updateWaterReminder(userId, 3);
			this.databaseEngine.updateUserName(userId, "abcd");
			this.databaseEngine.updateGender(userId, "male");
			this.databaseEngine.updateReqCalDay(userId);
			
			result = dishes2[0].getName();
			
		} catch (Exception e) {
			thrown = true;
		}
		assertThat(result).isNotEqualTo("");
		
	//}

}
	
	@Test
	public void testRecommendation() throws Exception 
	{	
		RecommendationDatabaseEngine recomDB=new RecommendationDatabaseEngine();
		boolean thrown = false;
		String userID = "sklo";
		User c_user = new User(userID);
		Dish[] result_recom=null;
		Dish[] veg_result=null;
		c_user.setHeight(173);
		c_user.setWeight(62);
		c_user.setGender("male");
		c_user.setGymFrequency(2);
		c_user.setAge(21);
		c_user.setCalDay(2000.4);
		c_user.setRestrictions("chicken,beef");
		Dish[] final_dishes = null;
		String[] menu= {"chicken with rice","noodles","apple","banana"};
		List<Dish> dis = new ArrayList<Dish>();
		for(String str: menu)
		{
			dis.add(new Dish(str));
		}
		Dish[] dis2 = dis.toArray(new Dish[dis.size()]);			
		
		final_dishes = recomDB.findCaloricContent(dis2);
			
			
		
		
		Recommendation rec = new Recommendation(c_user,final_dishes);
		result_recom = rec.getRecommendedDishes();
		veg_result = rec.getVegRecommendedDishes();
//		assert rec==null;
//		System.out.println(final_dishes[0].getName());

//		assert result_recom[0].getName().equals("banana");
		

	
	}
	
	@Test
	public void translateTest() {
		Translator tr = new Translator();
		try {
		tr.translate("en", "fr", "hi");
		
		
	}
		catch (Exception e) {
		}
		
	}
	
//	@Test
//	public void handletext() throws Exception {
//		Event evnt;
//		TextMessageContent content= new TextMessageContent("hi", "hi");
//		KitchenSinkController ks = new KitchenSinkController();
//		ks.handleTextContent("hi", evnt, content);
//		assert(true);
//	}
	
	

}
