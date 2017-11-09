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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.common.io.ByteStreams;

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

import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@LineMessageHandler
public class KitchenSinkController {
	


	@Autowired
	private LineMessagingClient lineMessagingClient;
	private UserInputDatabaseEngine database;
	private RecommendationDatabaseEngine recomDB;

	@EventMapping
	public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
		log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		log.info("This is your entry point:");
		log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		TextMessageContent message = event.getMessage();
		handleTextContent(event.getReplyToken(), event, message);
	}

	@EventMapping
	public void handleStickerMessageEvent(MessageEvent<StickerMessageContent> event) {
		handleSticker(event.getReplyToken(), event.getMessage());
	}

	@EventMapping
	public void handleLocationMessageEvent(MessageEvent<LocationMessageContent> event) {
		LocationMessageContent locationMessage = event.getMessage();
		reply(event.getReplyToken(), new LocationMessage(locationMessage.getTitle(), locationMessage.getAddress(),
				locationMessage.getLatitude(), locationMessage.getLongitude()));
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
		reply(((MessageEvent) event).getReplyToken(), new ImageMessage(jpg.getUri(), jpg.getUri()));

	}

	@EventMapping
	public void handleAudioMessageEvent(MessageEvent<AudioMessageContent> event) throws IOException {
		final MessageContentResponse response;
		String replyToken = event.getReplyToken();
		String messageId = event.getMessage().getId();
		try {
			response = lineMessagingClient.getMessageContent(messageId).get();
		} catch (InterruptedException | ExecutionException e) {
			reply(replyToken, new TextMessage("Cannot get image: " + e.getMessage()));
			throw new RuntimeException(e);
		}
		DownloadedContent mp4 = saveContent("mp4", response);
		reply(event.getReplyToken(), new AudioMessage(mp4.getUri(), 100));
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
		this.replyText(replyToken, "Got postback " + event.getPostbackContent().getData());
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


	private void handleSticker(String replyToken, StickerMessageContent content) {
		reply(replyToken, new StickerMessage(content.getPackageId(), content.getStickerId()));
	}

	private void handleTextContent(String replyToken, Event event, TextMessageContent content)
            throws Exception {
        String text = content.getText();
        String[] arr = text.split(":");
        String command = arr[0];
        String inputData = arr[1];
        
        log.info("Got text message from {}: {}", replyToken, text);
        switch (command) {

            case "profile": {
                String userId = event.getSource().getUserId();
                if (userId != null) {
                    lineMessagingClient
                            .getProfile(userId)
                            .whenComplete(new ProfileGetter (this, replyToken));
                } else {
                    this.replyText(replyToken, "Bot can't use profile API without user ID");
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
            case "Start": {

            		String userId = event.getSource().getUserId();
            		if(database.searchUser(userId)==false)
            		{
            		User user = new User(userId);
            		this.replyText(replyToken,"\t\t\t\t\t\t\t\t\tUser created!\n Please call the following:\nname,\ngender,\nweight(kg),\nheight(cm),\nage,\ngymFrequency(0 to 7 per week),\nloseGainPerWeek(No. of kgs to gain or lose. eg: -10 for losing 10 kgs per week),\nwaterReminder(Integer No. of reminders per day)");	
            		database.CreateNewUser(user);
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
        	String userId = event.getSource().getUserId();
        	database.updateGender(userId, inputData);
        	this.replyText(replyToken,inputData + " received");
        	database.setBMR(userId);
        	database.setBMI(userId);
    		break;
        }
        case "weight": {
        	String userId = event.getSource().getUserId();
        	database.updateWeight(userId, Float.parseFloat(inputData));
        	this.replyText(replyToken,inputData + " received");
        	database.setBMR(userId);
        	database.setBMI(userId);
        	break;
        }
        case "height": {
        	String userId = event.getSource().getUserId();
        	database.updateHeight(userId, Float.parseFloat(inputData));
        	this.replyText(replyToken,inputData + " received");
        	database.setBMR(userId);
        database.setBMI(userId);
    		break;
        }
        
        
        case "age": {
        	String userId = event.getSource().getUserId();
    		database.updateAge(userId, Integer.parseInt(inputData));
    		this.replyText(replyToken,inputData + " received");
    		database.setBMR(userId);
        	database.setBMI(userId);
    		break;
        }
        case "gymFrequency": {
        	String userId = event.getSource().getUserId();
    		database.updateGymFrequency(userId, Integer.parseInt(inputData));
    		this.replyText(replyToken,inputData + " received");
    		break;
        }
        case "loseGainPerWeek": {
        	String userId = event.getSource().getUserId();
    		database.updateLoseGain(userId, Integer.parseInt(inputData));
    		this.replyText(replyToken,inputData + " received");
    		break;
        }
        case "waterReminder": {
        	String userId = event.getSource().getUserId();
    		database.updateWaterReminder(userId, Integer.parseInt(inputData));
    		this.replyText(replyToken,"Thank you, you will be alerted");
    		break;
        }
        
        case "recommend" : {
    		//this.replyText(replyToken,"We recommend a corn soup with salad and cheese, and croutons.");
        	String userId = event.getSource().getUserId();
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
        	User curr_user = database.getUserRecord(userId);
//        	Recommendation recommend = new Recommendation(curr_user, final_dishes);
//        	Dish[] recommended_dishes = recommend.getRecommendedDishes();
        	String reply_msg = "Recommended dishes in best to least:\n";
        	for(Dish d: final_dishes)
        	{
        		reply_msg = reply_msg + d.getName() + d.getCalories() + "\n";
        	}
        	this.replyText(replyToken, reply_msg + "\n\n" + translator.translate(fromLang, toLang, reply_msg));
        	break;
        }
        
        case "translate": {
        	String userId = event.getSource().getUserId();
            String fromLang = "en";
            String toLang = "zh-CN";
        	Translator translator = new Translator();
        this.replyText(replyToken, translator.translate(fromLang, toLang, inputData));        	
        	break;
        }
        
        
        case "carousel": {
           String imageUrl = createUri("/static/buttons/1040.jpg");
           CarouselTemplate carouselTemplate = new CarouselTemplate(
           Arrays.asList(
              new CarouselColumn(imageUrl, "hoge", "fuga", Arrays.asList(
                     new URIAction("Go to line.me","https://line.me"),
                     new PostbackAction("Say hello1", "hello ã�“ã‚“ã�«ã�¡ã�¯"))),
                     new CarouselColumn(imageUrl, "hoge", "fuga", Arrays.asList(
                     new PostbackAction("è¨€ hello2", "hello ã�“ã‚“ã�«ã�¡ã�¯", "hello ã�“ã‚“ã�«ã�¡ã�¯"),
                     new MessageAction("Say message","Rice=ç±³")))));
                TemplateMessage templateMessage = new TemplateMessage("Carousel alt text", carouselTemplate);
                this.reply(replyToken, templateMessage);
                break;
            }
        
        case "Motivation" : {
          	 Random rand = new Random();
           String[] msgs = {"Good progress! One more step towards a healthier lifestyle", "Add oil!", "Strive for progress, not perfection", "The struggle you're in today is developing the strength you need for tomorrow", "Yes, you can! The road may be bumpy, but stay committed to the process.", "Making excuses burns 0 calories per hour."};
           int  n = rand.nextInt(6);
           this.replyText(replyToken,msgs[n]);    
           break;
          }

            default:
            	String reply = null;
            	try {
            		reply = database.search(text);
            	} catch (Exception e) {
            		reply = text;
            	}
                log.info("Returns echo message {}: {}", replyToken, reply);
                this.replyText(
                        replyToken,
                        text
                );
                break;
               
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
