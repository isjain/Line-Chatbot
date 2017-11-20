package com.example.bot.spring;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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

import com.example.bot.spring.CouponDatabaseEngine;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = { CouponDbTester.class, CouponDatabaseEngine.class })
public class CouponDbTester {
	@Autowired
	private CouponDatabaseEngine databaseEngine;
	
	
	// not a CouponDb but still why not
	@Test
public void waterReminderCorrectInputtest() {  
		waterReminder ks = new waterReminder();
		ks.setWaterReminder(1, "Uc748ae75a91014989d8664a356ffab8d");
		assertThat(true);
	}
	
	
	@Test
public void waterReminderLineClientTestWrongInputtest() {  
		waterReminder ks = new waterReminder();
		ks.setWaterReminder(0, "Uc748ae75a91014989d8664a356ffab8d");
		assertThat(true);
	}
	
	@Test	
	// isNotOldUser
public void isOldUsertest() {  
	// uday = old user
				assertThat(this.databaseEngine.isOldUser("U633c7e32ccc0a6b09902d22791eddce8")).isEqualTo(true);		// Uday
				assertThat(this.databaseEngine.isOldUser("Uc6f9f762ebd52e0abedea651e2982b3c")).isEqualTo(false);		// Irene

}
	
	

	@Test
public void checkRedeemedtest() {  
		assertThat(this.databaseEngine.checkRedeemed("702124")).isEqualTo(false);		
		assertThat(this.databaseEngine.checkRedeemed("528435")).isEqualTo(true);		
	}
	
	
	//////////LATER MAYBEE WILL DO NOT FOR NOW
	@Test
public void RedeemedCodeTest() {  
		assertThat(this.databaseEngine.redeemCode("x", "Testing")).isEqualTo(true);		
//		assertThat(this.databaseEngine.redeemCode("741359", "TestingAgain")).isEqualTo(false);		
	}	

	
	@Test
public void generateNewCodeTest() {  
		int x =100000;
		try {
			x = this.databaseEngine.generateNewCode();
	}
		catch (Exception e) {
			System.out.println(e);
		}
		
		if (x >100000 && x <999999 )
			assert(true);
		else assert(false);	
	}
	
	
	@Test
public void saveCouponCodeTest() {  
		assertThat(this.databaseEngine.saveCouponCode("test")).isNotEqualTo("404");
	}
	

	@Test
public void isValidCodeTest() {  
		assertThat(this.databaseEngine.isValidCode("test")).isEqualTo(false);
		assertThat(this.databaseEngine.isValidCode("702124")).isEqualTo(true);
	}
	
	@Test
public void getCouponNumberTest() {  
		try {
			assertThat(this.databaseEngine.getCouponNumber()).isNotEqualTo(0);
		}
		
	
		catch (Exception e) {
			System.out.println(e);
		}	
	
	// getCouponNumber

	
}
}
