
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
@SpringBootTest(classes = { KitchenSinkTester.class, UserInputDatabaseEngine.class, RecommendationDatabaseEngine.class })
public class KitchenSinkTester {
	@Autowired
	private UserInputDatabaseEngine databaseEngine;

	private RecommendationDatabaseEngine recomDB;



	
//	//test for recommendation
////	@Test
////	public void testRecom() throws Exception {
////		boolean thrown = false;
////		String userId = "heylo";
////		User new_user = new User(userId);
////		Dish[] result2=null;
////		User curr_user=null;
////		//test recommendation
////		String inputData = "chicken with rice,apple,banana,noodles";
////    		String[] menu = inputData.split(",");
////		List<Dish> dishes1 = new ArrayList<Dish>();
////		Dish[] dishes2;
////		Dish[] final_dishes;
////		Recommendation recommend2;
////		
////    		try {
////			this.databaseEngine.CreateNewUser(new_user);
////			this.databaseEngine.updateWeight(userId, 65);
////			this.databaseEngine.updateHeight(userId, 172);
////			this.databaseEngine.updateGymFrequency(userId, 4);
////			this.databaseEngine.updateLoseGain(userId, 5);
////			this.databaseEngine.updateAge(userId, 21);
//////			this.databaseEngine.updateWaterReminder(userId, 3);
////			this.databaseEngine.updateUserName(userId, "abcd");
////			this.databaseEngine.updateGender(userId, "male");
////			this.databaseEngine.updateReqCalDay(userId);
////			
////			curr_user = databaseEngine.getUserRecord(userId);
////
////			
////	        	for (String str: menu)
////	        	{
////	        		dishes1.add(new Dish(str));
////	        	}
////	        	dishes2 = dishes1.toArray(new Dish[dishes1.size()]);
////	        final_dishes = recomDB.findCaloricContent(dishes2);
////	        
////	        	
////	        	recommend2 = new Recommendation(curr_user, final_dishes);
////	        	
////            	result2 = recommend2.getRecommendedDishes();            	
////				
////		}catch (Exception e) {
////			thrown = true;
////		}
////    		
////    		assertThat(curr_user.getWeight()).isEqualTo("65");
//////    		assertThat(result2).isEqualTo(null);
//////		assertThat(result2[0].getName()).isEqualTo("banana");
//////		assertThat(result[1].getName()).isEqualTo("noodles");
//////		assertThat(result[2].getName()).isEqualTo("apple");
//////		assertThat(result[3].getName()).isEqualTo("chicken and rice");
////
//////		assertThat(result.getHeight()).isEqualTo("172");
//////		assertThat(result.getGymFrequency()).isEqualTo("4");
//////		assertThat(result.getLoseGainPerWeek()).isEqualTo("5");
//////		assertThat(result.getAge()).isEqualTo("21");
////////		assertThat(result.getWaterReminder()).isEqualTo("3");
//////		assertThat(result.getName()).isEqualTo("abcd");
//////		assertThat(result.getGender()).isEqualTo("male");
//////		assertThat(result.getCalDay()).isEqualTo("210");
////		
////		
////	}
//
//}
//		} catch (Exception e) {
//			thrown = true;
//		}
//		assertThat(recommended_dishes.length).isEqualTo("3");
////		assertThat(recommended_dishes[0].getName()).isEqualTo("apples");
////		assertThat(recommended_dishes[1].getName()).isEqualTo("noodles and soup");
////		assertThat(recommended_dishes[2].getName()).isEqualTo("green salad");
//	}
	
	@Test
	public void testRecommendation() throws Exception 
	{
		boolean thrown = false;
		String userID = "sklo";
		User c_user = new User(userID);
		Dish[] result_recom=null;
		c_user.setHeight(173);
		c_user.setWeight(62);
		c_user.setGender("male");
		c_user.setGymFrequency(2);
		c_user.setAge(21);
		c_user.setCalDay(2000.4);
		String[] menu= {"chicken with rice","noodles","apple","banana"};
		List<Dish> dis = new ArrayList<Dish>();
		for(String str: menu)
		{
			dis.add(new Dish(str));
		}
		Dish[] dis2 = dis.toArray(new Dish[dis.size()]);
		try {
			
			Dish[] final_dishes = recomDB.findCaloricContent(dis2);
			Recommendation rec = new Recommendation(c_user,final_dishes);
			result_recom = rec.getRecommendedDishes();
			
		}catch(Exception e){
			thrown = true;
		}
		
//		assertThat(result_recom[0].getName()).isEqualTo(null);

	
	}
	
	
	@Test
	public void testFound() throws Exception {
		boolean thrown = false;
		User result = null;
		String userId = "heylo";
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
//			this.databaseEngine.updateReqCalDay(userId, 210);
			
			result = this.databaseEngine.getUserRecord("heylo");
			
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
//		assertThat(result.getCalDay()).isEqualTo("210");
		
		
	}

}
