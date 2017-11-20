package com.example.bot.spring;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;
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
	
	@Test
	public void testNotFound() throws Exception {
		boolean thrown = false;
		try {
			
			this.databaseEngine.search("no");
		} catch (Exception e) {
			thrown = true;
		}
		assertThat(thrown).isEqualTo(true);
	}
	
	@Test
	public void testFound2() throws Exception {
		boolean thrown = false;
		String result = null;
		try {
			result = this.databaseEngine.search("Hi");
		} catch (Exception e) {
			thrown = true;
		}
		assertThat(!thrown).isEqualTo(true);
//		assertThat(result).isEqualTo("Hey, how things going?");
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
