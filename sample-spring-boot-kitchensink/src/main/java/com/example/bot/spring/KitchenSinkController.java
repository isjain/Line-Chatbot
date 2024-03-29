/*
 * Copyright 2016 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.example.bot.spring;

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


import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@LineMessageHandler
/**
* The KitchenSinkController class is the mediator class which is interacting with the other classes.
* It reads the user input and calls the relevant functions.
*
* @author Project Group 25
* @version 1.0
* @since 2017-11-20
*/

public class KitchenSinkController {
	

	@Autowired
	private LineMessagingClient lineMessagingClient;
	private UserInputDatabaseEngine database;
	private RecommendationDatabaseEngine recomDB;
	private CouponDatabaseEngine icedb;

	@EventMapping
	public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
		log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		log.info("This is your entry point:");
		log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		TextMessageContent message = event.getMessage();
		handleTextContent(event.getReplyToken(), event, message);
	}



	@EventMapping
	public void handleImageMessageEvent(MessageEvent<ImageMessageContent> event) throws IOException {
		final MessageContentResponse response;
		String replyToken = event.getReplyToken();
		String messageId = event.getMessage().getId();
		try {
			response = lineMessagingClient.getMessageContent(messageId).get();
		} catch (InterruptedException | ExecutionException e) {
			reply(replyToken, new TextMessage("Cannot get image: " + e.getMessage()));
			throw new RuntimeException(e);
		}
		DownloadedContent jpg = saveContent("jpg", response);
		reply(((MessageEvent) event).getReplyToken(), new ImageMessage(jpg.getUri()));

	}



	@EventMapping
	public void handleUnfollowEvent(UnfollowEvent event) {
		log.info("unfollowed this bot: {}", event);
	}

	@EventMapping
	public void handleFollowEvent(FollowEvent event) {
		String replyToken = event.getReplyToken();
		this.replyText(replyToken, "Got followed event");
	}

	@EventMapping
	public void handleJoinEvent(JoinEvent event) {
		String replyToken = event.getReplyToken();
		this.replyText(replyToken, "Joined " + event.getSource());
	}

	@EventMapping
	public void handlePostbackEvent(PostbackEvent event) {
		String replyToken = event.getReplyToken();
		System.out.println(event.getSource().getUserId());
		String cal = (event.getPostbackContent().getData()).substring(event.getPostbackContent().getData().lastIndexOf(" ")+1);
		String userId = event.getSource().getUserId();
		database.updateCalperDay(userId, cal);
		this.replyText(replyToken, (event.getPostbackContent().getData()).substring(0, (event.getPostbackContent().getData()).lastIndexOf(" ")));
	}

	@EventMapping
	public void handleBeaconEvent(BeaconEvent event) {
		String replyToken = event.getReplyToken();
		this.replyText(replyToken, "Got beacon message " + event.getBeacon().getHwid());
	}

	@EventMapping
	public void handleOtherEvent(Event event) {
		log.info("Received message(Ignored): {}", event);
	}

	private void reply(@NonNull String replyToken, @NonNull Message message) {
		reply(replyToken, Collections.singletonList(message));
	}

	private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
		try {
			BotApiResponse apiResponse = lineMessagingClient.replyMessage(new ReplyMessage(replyToken, messages)).get();
			log.info("Sent messages: {}", apiResponse);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	private void replyText(@NonNull String replyToken, @NonNull String message) {
		if (replyToken.isEmpty()) {
			throw new IllegalArgumentException("replyToken must not be empty");
		}
		if (message.length() > 1000) {
			message = message.substring(0, 1000 - 2) + "..";
		}
		this.reply(replyToken, new TextMessage(message));
	}


	@SuppressWarnings("fallthrough")
	public void handleTextContent(String replyToken, Event event, TextMessageContent content)
            throws Exception {
        String text = content.getText();
//        String[] arr = text.split(":");
        int ind = text.indexOf(":");
        String command= text.substring(0 , ind);
        command=command.trim();
        command=command.toLowerCase();
        String inputData = text.substring(ind+1);
        inputData=inputData.trim();
        
        log.info("Got text message from {}: {}", replyToken, text);
        switch (command) {

            case "profile": {
                String userId = event.getSource().getUserId();
                if (userId != null) {
                    User u = database.getUserRecord(userId);
                    String reply_msg = "Name:" + u.getName() + "\n" + "Weight:"+ u.getWeight().toString() +"\n"+ "Height:" + u.getHeight().toString() + "\n" + "Restrictions:" + u.getRestrictions() + "\n" + "Age:" + u.getAge().toString() + "\n" + "GymFrequency:" + u.getGymFrequency() + "\n" + "LoseGainPerWeek:" + u.getLoseGainPerWeek();
                    this.replyText(replyToken, reply_msg);
                } else {
                    this.replyText(replyToken, "User not found, type Start:x to begin!");
                }
                break;
            }
            case "confirm": {
                ConfirmTemplate confirmTemplate = new ConfirmTemplate(
                        "Do it?",
                        new MessageAction("Yes", "Yes!"),
                        new MessageAction("No", "No!")
                );
                TemplateMessage templateMessage = new TemplateMessage("Confirm alt text", confirmTemplate);
                this.reply(replyToken, templateMessage);
                break;
            }
            case "start": {

            		String userId = event.getSource().getUserId();
            		if(database.searchUser(userId)==false)
            		{
            		User user = new User(userId);
            		this.replyText(replyToken,"User created!\n Please call the following:\nname,\ngender,\nweight(kg),\nheight(cm),\nage,\ngymFrequency(0 to 7 per week),\nloseGainPerWeek(No. of kgs to gain or lose. eg: -10 for losing 10 kgs per week),\nwaterReminder(Integer No. of reminders per day)");	
            		database.CreateNewUser(user);
            		}
            		else {
            			this.replyText(replyToken, "You already have an existing account");
            		}
            		break;
            }
            case "name": {
            	
                String userId = event.getSource().getUserId();
            		database.updateUserName(userId, inputData);
            		this.replyText(replyToken,inputData + " received");

        		break;
        }
            
        
            
        case "gender": {
        	inputData=inputData.toLowerCase();
        	inputData=inputData.trim();
        	if (inputData.equals("male")!=true && inputData.equals("female")!=true)
        	{
        		this.replyText(replyToken,"Please enter either male or female");
        		break;
        	}
        	
        	
        	String userId = event.getSource().getUserId();
        	database.updateGender(userId, inputData);
        	this.replyText(replyToken,inputData + " received");
        	database.setBMR(userId);
        	database.setBMI(userId);
    		break;
        }
        case "weight": {
        	Float weight;
        	
        	try {
        		weight = Float.parseFloat(inputData);
        	}
        	catch(NumberFormatException nb){
        		this.replyText(replyToken,"Invalid input, please enter a number");
        		break;
        	}
        	String userId = event.getSource().getUserId();
        	database.updateWeight(userId, weight);
        	this.replyText(replyToken,inputData + " received");
        	database.setBMR(userId);
        	database.setBMI(userId);
        	database.updateReqCalDay(userId);
        	break;
        }
        case "height": {
        	Float height;
        	try {
        		height = Float.parseFloat(inputData);
        	}
        	catch(NumberFormatException nb){
        		this.replyText(replyToken,"Invalid input, please enter a number");
        		break;
        	}
        	String userId = event.getSource().getUserId();
        	database.updateHeight(userId, height);
        	this.replyText(replyToken,inputData + " received");
        	database.setBMR(userId);
        	database.setBMI(userId);
        	database.updateReqCalDay(userId);

    		break;
        }
        


        case "waterme" : {
        	Integer water;
        	try {
        		water = Integer.parseInt(inputData);
        	}
        	catch(NumberFormatException nb){
        		this.replyText(replyToken,"Invalid input, please enter a number");
        		break;
        	}
        	String userId = event.getSource().getUserId();
        	
        	waterReminder  remind = new waterReminder();
        	remind.setWaterReminder(water, userId);
        	break;
        }
           
        


        case "restrictions": {
        	String userId = event.getSource().getUserId();
//        	User u = database.getUserRecord(userId);
//        	u.setRestrictions(inputData);
        	database.updateRestrictions(userId, inputData.toLowerCase());
        	this.replyText(replyToken,inputData + " received");
        	break;
        }
        case "age": {
        	Integer age;
        	try {
        		age = Integer.parseInt(inputData);
        	}
        	catch(NumberFormatException nb){
        		this.replyText(replyToken,"Invalid input, please enter a number");
        		break;
        	}
        	String userId = event.getSource().getUserId();
    		database.updateAge(userId, age);
    		this.replyText(replyToken,inputData + " received");
    		database.setBMR(userId);
        	database.setBMI(userId);
    		break;
        }
        case "gymfrequency": {
        	Integer gym;
        	try {
        		gym = Integer.parseInt(inputData);
        	}
        	catch(NumberFormatException nb){
        		this.replyText(replyToken,"Invalid input, please enter a number");
        		break;
        	}
        	String userId = event.getSource().getUserId();
    		database.updateGymFrequency(userId, gym);
    		this.replyText(replyToken,inputData + " received");
    		break;
        }
        case "losegainperweek": {
        	Integer lg;
        	try {
        		lg = Integer.parseInt(inputData);
        	}
        	catch(NumberFormatException nb){
        		this.replyText(replyToken,"Invalid input, please enter a number");
        		break;
        	}
        	String userId = event.getSource().getUserId();
    		database.updateLoseGain(userId, lg);
    		this.replyText(replyToken,inputData + " received");
    		break;
        }

        case "vege": {	
        	
        }
        case "recommend" : {
        	
        	if( (inputData.equals("Cafe")) || (inputData.equals("Bistro")) || (inputData.equals("Subway")) || (inputData.equals("LSK")) || (inputData.equals("LG7")))
			{
				String location_dishes = recomDB.giveVegDishes(inputData);
				this.replyText(replyToken, location_dishes);
				break;
			}
        	
        	
        	String userId = event.getSource().getUserId();
        	
        	User curr_user = database.getUserRecord(userId);
        	
        	if(curr_user.getWeight().equals("0") || curr_user.getHeight().equals("0") || curr_user.getGender().equals("nogender") || curr_user.getAge().equals("0"))
        	{
        		this.replyText(replyToken, "Please make sure that weight, height, gender and age are accepted first!");
        		break;
        	}
        	
            String fromLang = "en";
            String toLang = "zh-CN";
        	Translator translator = new Translator();
        	
        	//Recommendation
        	String[] menu = inputData.split(",");
        	List<Dish> dishes = new ArrayList<Dish>();
        	for (String str: menu)
        	{
        		dishes.add(new Dish(str));
        	}
        	Dish[] dishes2 = dishes.toArray(new Dish[dishes.size()]);
        	Dish[] final_dishes = recomDB.findCaloricContent(dishes2);
        	
//        	String[] a = curr_user.getRestrictions().split(",");
        	
        	Recommendation recommend = new Recommendation(curr_user, final_dishes);
//        	log.info("inputted dishes: "+recommend.getInputDishes());
        	Dish[] recommended_dishes;
        	//vege function
        	if(command.equals("vege")) {
        		
        		recommended_dishes = recommend.getVegRecommendedDishes();
        		
        	}
        	else {
        		
            	recommended_dishes = recommend.getRecommendedDishes();

        	}
        	
        	String motivation = recommend.motivationMessage();
        	String reply_msg = "Recommended dishes in best to least:\n";

        	
        	//*********************************************************************
		DecimalFormat df = new DecimalFormat("#.#");

        	String imageUrl = createUri("/static/buttons/final.png");
        	List<CarouselColumn> dishlist = new ArrayList<CarouselColumn>();
        	for(Dish d: recommended_dishes) {
        		if(d.getCalories()!=0)
        		{dishlist.add(new CarouselColumn(imageUrl,d.getName(),df.format(d.getpropCalories())+" calories, "+ df.format(d.getPortion())+" portions", Arrays.asList(
                        new PostbackAction("Choose", d.getName()+" confirmed"+ "\n\n" + translator.translate(fromLang, toLang, d.getName()) + "\n\n"+ motivation +" "+ String.valueOf(d.getCalories())))));
        		}
        	}
        CarouselTemplate carouselTemplate = new CarouselTemplate(dishlist);
        TemplateMessage templateMessage = new TemplateMessage("Carousel alt text", carouselTemplate);
        this.reply(replyToken, templateMessage);
        recomDB.useStoredCal(userId);
        break;
        
        }
        
        case "json": {
        	JsonTest jst = new JsonTest();
    		String userId = event.getSource().getUserId();
    		Dish[] dishes2 = jst.getJSONlistweb(inputData.trim());
//???        	
        	if( (inputData.equals("Cafe")) || (inputData.equals("Bistro")) || (inputData.equals("Subway")) || (inputData.equals("LSK")) || (inputData.equals("LG7")))
			{
				String location_dishes = recomDB.giveVegDishes(inputData);
				this.replyText(replyToken, location_dishes);
				break;
			}
        	
            String fromLang = "en";
            String toLang = "zh-CN";
        	Translator translator = new Translator();
        	
        	//Recommendation
        	
        	Dish[] final_dishes = recomDB.findCaloricContent(dishes2);
        	User curr_user = database.getUserRecord(userId);
//        	String[] a = curr_user.getRestrictions().split(",");
        	
        	Recommendation recommend = new Recommendation(curr_user, final_dishes);
//        	log.info("inputted dishes: "+recommend.getInputDishes());
        	Dish[] recommended_dishes;
        	//vege function
        	if(command.equals("vege")) {
        		
        		recommended_dishes = recommend.getVegRecommendedDishes();
        		
        	}
        	else {
        		
            	recommended_dishes = recommend.getRecommendedDishes();

        	}
        	
        	String motivation = recommend.motivationMessage();
        	String reply_msg = "Recommended dishes in best to least:\n";
        	
        	//*********************************************************************
		DecimalFormat df = new DecimalFormat("#.#");

        	String imageUrl = createUri("/static/buttons/final.png");
        	List<CarouselColumn> dishlist = new ArrayList<CarouselColumn>();
        	for(Dish d: recommended_dishes) {
        		if(d.getCalories()!=0) {
        		dishlist.add(new CarouselColumn(imageUrl,d.getName(),df.format(d.getpropCalories())+" calories, "+df.format(d.getPortion())+" portions", Arrays.asList(
                        new PostbackAction("Choose", d.getName()+" confirmed"+ "\n\n" + translator.translate(fromLang, toLang, d.getName()) + "\n\n"+ motivation +" "+ String.valueOf(d.getCalories())))));
        	}
        		}
        CarouselTemplate carouselTemplate = new CarouselTemplate(dishlist);
        TemplateMessage templateMessage = new TemplateMessage("Carousel alt text", carouselTemplate);
        this.reply(replyToken, templateMessage);
        recomDB.useStoredCal(userId);
        break;
	    }

      
        case "friend": {
	    		int MAX_QUANT_COUPON = 4999;
	    	 	int couponQuant = icedb.getCouponNumber();
		    		if (couponQuant > MAX_QUANT_COUPON ) {
		    	 		this.replyText(replyToken,"Sorry, this promotion is no longer available!");
		    	 		break;
		    		}
	    	 	String userId = event.getSource().getUserId();
	    	 	String code = icedb.saveCouponCode(userId);
	    	 	if (code == "404")
	    	     	this.replyText(replyToken,"We cannot currently generate a code, please try again later.");    	 	
	    	 	else 
	    	 		this.replyText(replyToken,"Your code is " + code);
	
	 		break;
    }
        
        case "code": {
	    	 	String userId = event.getSource().getUserId();
	    	 	boolean isOldUser = icedb.isOldUser(userId);
	    	 	if (isOldUser) {
        			this.replyText(replyToken, "Sorry, you are not eligible for this promotion.");
        			break;
	    	 	}
        		boolean userRedeemed = icedb.hasUserRedeemed(userId);
        		if (userRedeemed)  {
        			this.replyText(replyToken, "Sorry, you have already redeemed a code.");
        			break;
        		}
        		

	     	boolean valid = icedb.isValidCode(inputData);
	     	
	     	if (valid == false) {
	     		this.replyText(replyToken, "Sorry, the entered code " + inputData + " is invalid. Try again");
	     		break;
	     	}
	     	
    	 		String recommendUserId = icedb.getRecommenderUserID(inputData);
	     	boolean redeemed = icedb.redeemCode(inputData, userId);

	     	if (redeemed) {
	            String imageUrl = createUri("/static/buttons/ice.jpg");
	            CarouselTemplate carouselTemplate = new CarouselTemplate(
	            Arrays.asList(
	               new CarouselColumn(imageUrl, "Congratulations", "The coupon " + inputData + " has been redeemed!", Arrays.asList(
	                      new MessageAction("Accept","Thank you!")))));
	                 TemplateMessage templateMessage = new TemplateMessage("Carousel alt text", carouselTemplate);
	                 this.reply(replyToken, templateMessage);	     	

	                 // try to push message to old user
//	                	TextMessage textMessage = new TextMessage("Thank you for your recommendation, your friend has redeemed your code!");
	                	PushMessage pushMessage = new PushMessage(recommendUserId, templateMessage);
	                	try {
	                	Response<BotApiResponse> response =
	                	        LineMessagingServiceBuilder
	                	                .create("CJo3Ka/VX7VW4fsG78i5dNDpP5qqYgr1PD7YUclFFc62ZtnrIpHiM/Muof6oLc/J/bPoaheiYdHNoUkg09kAt5VqnD+tMyzOCClGLwvJaR3+etoVOdsHo1DGXv2UqOljNgUIFR/zQWk1U4iFRPr4TQdB04t89/1O/w1cDnyilFU=") // channel access token
	                	                .build()
	                	                .pushMessage(pushMessage)
	                	                .execute();
	                	}
	                	catch (Exception e) {
	                		e.printStackTrace();
	                	}
	                	break;              
	     	}
	     	
	     	else 
	     		this.replyText(replyToken, "Sorry, the code " + inputData + " has already been redeemed!");
	     	// hopefully it works
	     	break;
    }




        default:{
//    		String reply_text="this is default";
    		String reply_text="Incorrect input! Please write your message in the following format:"+"\n"+
    				"Start:<string>"+"\n"+
    				"Profile:<string>"+"\n"+
    				"Name:<string>"+"\n"+
    				"Gender:<male/female>"+"\n"+
    				"Weight:<float(kg)>"+"\n"+
   				"Height:<float(cm)>"+"\n"+
   				"Age:<int(years)>"+"\n"+
   				"loseGainPerWeek:<(+/-)float>"+"\n"+
   				"Restrictions:<ingredient,ingredient>"+"\n"+
   				"waterMe:<integer>"+"\n"+
   				"gymFrequency:<integer>"+"\n"+
   				"vege:<Bistro/LSK/Cafe/LG7/Subway>"+"\n"+
   				"vege:<dish name,dish name>"+"\n"+
   				"recommend:<dish name,dish name>"+"\n"+
   				"json:<url>"+"\n"+
   				"friend:<string>"+"\n"+
   				"code:<code>";

        this.replyText(
                replyToken,
                reply_text
        );

        break;
    }
        }
    }
	

	static String createUri(String path) {
		return ServletUriComponentsBuilder.fromCurrentContextPath().path(path).build().toUriString();
	}

	private void system(String... args) {
		ProcessBuilder processBuilder = new ProcessBuilder(args);
		try {
			Process start = processBuilder.start();
			int i = start.waitFor();
			log.info("result: {} =>  {}", Arrays.toString(args), i);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} catch (InterruptedException e) {
			log.info("Interrupted", e);
			Thread.currentThread().interrupt();
		}
	}

	private static DownloadedContent saveContent(String ext, MessageContentResponse responseBody) {
		log.info("Got content-type: {}", responseBody);

		DownloadedContent tempFile = createTempFile(ext);
		try (OutputStream outputStream = Files.newOutputStream(tempFile.path)) {
			ByteStreams.copy(responseBody.getStream(), outputStream);
			log.info("Saved {}: {}", ext, tempFile);
			return tempFile;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private static DownloadedContent createTempFile(String ext) {
		String fileName = LocalDateTime.now().toString() + '-' + UUID.randomUUID().toString() + '.' + ext;
		Path tempFile = KitchenSinkApplication.downloadedContentDir.resolve(fileName);
		tempFile.toFile().deleteOnExit();
		return new DownloadedContent(tempFile, createUri("/downloaded/" + tempFile.getFileName()));
	}


	public KitchenSinkController() {
		database = new UserInputDatabaseEngine();
		itscLOGIN = System.getenv("ITSC_LOGIN");
		recomDB = new RecommendationDatabaseEngine();
		icedb = new CouponDatabaseEngine();
	}

	private String itscLOGIN;
	

	//The annontation @Value is from the package lombok.Value
	//Basically what it does is to generate constructor and getter for the class below
	//See https://projectlombok.org/features/Value
	@Value
	public static class DownloadedContent {
		Path path;
		String uri;
	}


	//an inner class that gets the user profile and status message
	class ProfileGetter implements BiConsumer<UserProfileResponse, Throwable> {
		private KitchenSinkController ksc;
		private String replyToken;
		
		public ProfileGetter(KitchenSinkController ksc, String replyToken) {
			this.ksc = ksc;
			this.replyToken = replyToken;
		}
		@Override
    	public void accept(UserProfileResponse profile, Throwable throwable) {
    		if (throwable != null) {
            	ksc.replyText(replyToken, throwable.getMessage());
            	return;
        	}
        	ksc.reply(
                	replyToken,
                	Arrays.asList(new TextMessage(
                		"Display name: " + profile.getDisplayName()),
                              	new TextMessage("Status message: "
                            		  + profile.getStatusMessage()))
        	);
    	}
    }
	
	

}
