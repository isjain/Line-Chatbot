package com.example.bot.spring;

import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import com.linecorp.bot.model.profile.UserProfileResponse;
import java.util.Random;
import java.text.DecimalFormat;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.common.io.ByteStreams;
import java.util.ArrayList;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.MessageContentResponse;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.BeaconEvent;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.JoinEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.UnfollowEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.RoomSource;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.AudioMessage;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.ImagemapMessage;
import com.linecorp.bot.model.message.LocationMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.StickerMessage;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.imagemap.ImagemapArea;
import com.linecorp.bot.model.message.imagemap.ImagemapBaseSize;
import com.linecorp.bot.model.message.imagemap.MessageImagemapAction;
import com.linecorp.bot.model.message.imagemap.URIImagemapAction;
import com.linecorp.bot.model.message.template.ButtonsTemplate;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.model.message.template.ConfirmTemplate;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import org.springframework.web.bind.annotation.*;
import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.model.profile.UserProfileResponse;
import retrofit2.Response;
import com.linecorp.bot.model.message.ImageMessage;



import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * waterReminder class is used to send reminders to the user to drink water in intervals set by the user. 
*
* @author Project Group 25
* @version 1.0
* @since 2017-11-20
*/

public class waterReminder {
	
	
	
	/**
	    * This method is used to set water reminders for the user to be reminded of it.
	    * @param hourGap Sets the hours of gap between both
	    * @param userID  Sets the user id to who to send the message ti
	    */
	public void setWaterReminder(int hourGap, String userID) {
    	if (hourGap ==0) 
    		return;
    	
    	Timer timer = new Timer ();
    	TimerTask hourlyTask = new TimerTask () {
    	    @Override
    	    public void run () {
    	        // your code here...
            	TextMessage textMessage = new TextMessage("It is time to drink your water :)");
            	PushMessage pushMessage = new PushMessage(userID, textMessage);
            	try {
            	Response<BotApiResponse> response =
            	        LineMessagingServiceBuilder
            	                .create("CJo3Ka/VX7VW4fsG78i5dNDpP5qqYgr1PD7YUclFFc62ZtnrIpHiM/Muof6oLc/J/bPoaheiYdHNoUkg09kAt5VqnD+tMyzOCClGLwvJaR3+etoVOdsHo1DGXv2UqOljNgUIFR/zQWk1U4iFRPr4TQdB04t89/1O/w1cDnyilFU=") // channel access token
            	                .build()
            	                .pushMessage(pushMessage)
            	                .execute();
            	System.out.println(response.code() + " " + response.message());
            	}
            	catch (Exception e) {
            		e.printStackTrace();
            	}
    	    }
    	};

    	// schedule the task to run starting now and then every hour...
    	timer.schedule (hourlyTask, 0l, 1000*60*60*hourGap);

	}

}
