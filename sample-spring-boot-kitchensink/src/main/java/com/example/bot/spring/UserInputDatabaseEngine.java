package com.example.bot.spring;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.lang.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;


import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.*;
import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.model.profile.UserProfileResponse;
import retrofit2.Response;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.*;
import java.util.Timer;
import java.util.TimerTask;

import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

public class UserInputDatabaseEngine extends DatabaseEngine {
	
	public void setBMR(String UserId)
	{
		try {
			float bmr = calcBMR(UserId);
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET bmr=? WHERE user_id=?");
			smt.setFloat(1,bmr);
			smt.setString(2,UserId);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	public float calcBMR(String UserId)
	{
		try {
			float weight = 0;
			float height = 0;
			String gender = "nogender";
			int age = 0;
			
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("SELECT * from userdatatable WHERE user_id=?");
			smt.setString(1, UserId);
			ResultSet rs = smt.executeQuery();
			float bmr = 0;
			while(rs.next())
			{
				weight = Float.parseFloat(rs.getString("weight"));
				height = Float.parseFloat(rs.getString("height"));
				gender = rs.getString("gender");
				age = Integer.parseInt(rs.getString("age"));
			}
			if (weight!=0.0 && height!=0.0 && age!=0 && gender!="nogender")
			{
				if(gender=="male") {
					bmr= (float) (10 * weight + 6.25 * height - 5 * age + 5);
				}
				else {
					bmr=(float) (10 * weight + 6.25 * height - 5 * age - 161);
				}
			}
			rs.close();
			smt.close();
			con.close();
			return bmr;
		}
		catch (Exception e) {
			System.out.println(e);
			return 0;

		}
	}
	
	public void setBMI(String UserId)
	{
		try {
			float bmi = calcBMI(UserId);
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET bmi=? WHERE user_id=?");
			smt.setFloat(1,bmi);
			smt.setString(2,UserId);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	public float calcBMI(String UserId)
	{
		try {
			float weight = 0;
			float height = 0;
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("SELECT * from userdatatable WHERE user_id=?");
			smt.setString(1, UserId);
			ResultSet rs = smt.executeQuery();
			float bmi = 0;
			while(rs.next())
			{
				weight = Float.parseFloat(rs.getString("weight"));
				height = Float.parseFloat(rs.getString("height"));
			}
			if (weight!=0.0 && height!=0.0)
			{
				bmi=weight/((height)*height);
			}
			rs.close();
			smt.close();
			con.close();
			return bmi;
			
		}
		catch (Exception e) {
			System.out.println(e);
			return 0;
		}
	}
	

	public void updateCalperDay(String UserId, String calpermeal)
	{			
		
		String totalCalList=null;
		String totalDates=null;
		boolean found=false;
		String delimiter = ";";
		
		String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());  // current date 

		
		try {
			
			Connection con = getConnection();
			PreparedStatement smt1 = con.prepareStatement("SELECT * FROM userdatatable WHERE user_id=?");
			smt1.setString(1,UserId);
		
			ResultSet rs = smt1.executeQuery();
			while(rs.next())
			{
				totalCalList = rs.getString("calperday");
				totalDates = rs.getString("dates");	
			}
			if( totalCalList!=null && totalDates!=null)
			{
//ERROR HERE
				String[] partsOfCal = totalCalList.split(";");
				String[] partsOfDate = totalDates.split(";");
				
				for (int i=0;i<partsOfDate.length;i++)
				{	
					if(partsOfDate[i].equals(date))
					{
					float changeCal = Float.parseFloat(partsOfCal[i]);
					changeCal+=Float.parseFloat(calpermeal);
					partsOfCal[i]=Float.toString(changeCal);
					found=true;
					break;
					}
				}
			
				
				totalCalList = String.join(delimiter, partsOfCal);
				totalDates = String.join(delimiter, partsOfDate);

			}		
			if(found==false)
			{
				if(totalCalList==null)
				{
					totalCalList=calpermeal;
					totalDates=date;
				}
				else
				{
					totalCalList=totalCalList+delimiter+calpermeal;
					totalDates=totalDates+delimiter+date;
				}
				
			}
		
			

			PreparedStatement smt2 = con.prepareStatement("UPDATE userdatatable SET calperday=?, dates=? WHERE user_id=?");
//			smt2.setString(1,totalCalList);
			smt2.setString(1,totalCalList);
			smt2.setString(2,totalDates);
			smt2.setString(3,UserId);
			System.out.println("\n\n\n\n"+smt2+"\n\n\n\n");
			ResultSet rs2 = smt2.executeQuery();
			rs.close();
			rs2.close();
			smt2.close();
			smt1.close();
			con.close();

		}
		catch (Exception e) {
			System.out.println(e);
		}
//		
	}


	
	public void updateWeight(String UserId, float weight)
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET weight=? WHERE user_id=?");

			smt.setFloat(1,weight);
			smt.setString(2,UserId);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	

	public void updateHeight(String UserId, float height)
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET height=? WHERE user_id=?");

			smt.setFloat(1,height);
			smt.setString(2,UserId);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	

	public void updateGymFrequency(String UserId, int gymFrequency)
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET gymfrequency=? WHERE user_id=?");

			smt.setInt(1,gymFrequency);
			smt.setString(2,UserId);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	

	public void updateBMI(String UserId, float weight)
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET weight=? WHERE user_id=?");

			smt.setFloat(1,weight);
			smt.setString(2,UserId);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	

	public void updateUserBMR(String UserId, float weight)
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET weight=? WHERE user_id=?");

			smt.setFloat(1,weight);
			smt.setString(2,UserId);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	

	public void updateLoseGain(String UserId, float loseGain)
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET losegainperweek=? WHERE user_id=?");

			smt.setFloat(1,loseGain);
			smt.setString(2,UserId);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void updateRestrictions(String UserId,String rest)
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET restrictions=? WHERE user_id=?");

			smt.setString(1,rest);
			smt.setString(2,UserId);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
	}
	

	public void updateAge(String UserId, int age)
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET age=? WHERE user_id=?");

			smt.setInt(1,age);
			smt.setString(2,UserId);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	

	public void updateWaterReminder(String UserId, int waterReminder)
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET waterreminder=? WHERE user_id=?");

			smt.setInt(1,waterReminder);
			smt.setString(2,UserId);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void updateUserName(String UserId, String name)
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET name=? WHERE user_id=?");

			smt.setString(1,name);
			smt.setString(2,UserId);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void updateGender(String UserId, String gender)
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET gender=? WHERE user_id=?");

			smt.setString(1,gender);
			smt.setString(2,UserId);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	

	public User getUserRecord(String UserId) {
		boolean found = false;
		String id = UserId;
		float weight=0;
		float height=0;
		int gymFrequency=0;
		float loseGainPerWeek=0;
		int age=0;
		float waterReminder=0;
		String name="";
		String gender="";
		double calDay=0;
		String res = "";
		User user = new User(id);
		
		try {
			
			
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("SELECT * FROM userdatatable WHERE user_id = ?");
			smt.setString(1,id);
			ResultSet rs = smt.executeQuery();
			while(rs.next())
			{
				weight = Float.parseFloat(rs.getString("weight"));
				height = Float.parseFloat(rs.getString("height"));
				gymFrequency = Integer.parseInt(rs.getString("gymfrequency"));
				loseGainPerWeek = Float.parseFloat(rs.getString("losegainperweek"));
				age = Integer.parseInt(rs.getString("age"));
				waterReminder = Float.parseFloat(rs.getString("waterreminder"));
				name = rs.getString("name");
				gender = rs.getString("gender");
				calDay = Double.parseDouble(rs.getString("reqcalday"));
				res = rs.getString("restrictions");
			}
			rs.close();
			smt.close();
			con.close();
			user.setWeight(weight);
			user.setAge(age);
			user.setGender(gender);
			user.setLostGainPerWeek(loseGainPerWeek);
			user.setGymFrequency(gymFrequency);
			user.setName(name);
			user.setHeight(height);
			user.setWaterReminder(waterReminder);
			user.setCalDay(calDay);
			user.setRestrictions(res);

			return user;
			}catch (Exception e) {
				System.out.println(e);
			}
		return user;
	}



	public void updateReqCalDay(String UserId)
	{
		try {
			String weight = null;
			String height = null;
			String gender = null;
			String age = null;
			String losegainperweek = null;
			String gymfrequency = null;
			Connection con = getConnection();
			PreparedStatement smt1 = con.prepareStatement("SELECT * FROM userdatatable WHERE user_id=?");
			smt1.setString(1,UserId);
			ResultSet rs = smt1.executeQuery();
			while(rs.next())
			{
				weight = rs.getString("weight");
				height = rs.getString("height");
				age = rs.getString("age");
				losegainperweek = rs.getString("losegainperweek");
				gymfrequency = rs.getString("gymfrequency");	
			}
			float weight1 = Float.parseFloat(weight);
			float height1 = Float.parseFloat(height);
			int age1 = Integer.parseInt(age);
			int losegainperweek1= Integer.parseInt(losegainperweek);
			int gymfrequency1 = Integer.parseInt(gymfrequency);
			double calDayReq = setCalDay(gender, weight1, height1, age1, losegainperweek1, gymfrequency1);
			PreparedStatement smt2 = con.prepareStatement("UPDATE userdatatable SET reqcalday=? WHERE user_id=?");
			smt2.setDouble(1,calDayReq);
			smt2.setString(2,UserId);
			ResultSet rs2 = smt2.executeQuery();
			rs.close();
			rs2.close();
			smt2.close();
			smt1.close();
			con.close();

		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	

	public double setCalDay(String gender, float weight, float height, int age, int loseGainPerWeek, int gymFrequency) {
		double calDay=0;
		float goal_bmr;
		if(gender=="male") {
			goal_bmr= (float) (10 * (weight+loseGainPerWeek) + 6.25 * height - 5 * age + 5);
		}
		else {
			goal_bmr=(float) (10 * (weight+loseGainPerWeek) + 6.25 * height - 5 * age - 161);
		}
		switch (gymFrequency) {
		case 0:calDay = goal_bmr*1.2;
				break;
		case 1:
		case 2:
		case 3:calDay = goal_bmr*1.375;
				break;
		case 4:
		case 5:calDay = goal_bmr*1.55;
		
				break;
		case 6:
		case 7:calDay = goal_bmr*1.725;
				break;
		}
		return calDay;
	}

	public void CreateNewUser(User new_User) throws Exception{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("INSERT INTO userdatatable (weight, user_id, height, gymFrequency, bmi, bmr, loseGainPerWeek, age, waterReminder, name, gender, goal, reqcalday) VALUES (0.0,?,0.0,0,0.0,0.0,0.0,0,0,'noname','nogender','nogoal',0)");
			smt.setString(1,new_User.getUserId());
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
			
		}catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	public boolean searchUser(String userID)
	{
		try {
		boolean found = false;
		String result = null;
		Connection con = getConnection();
		PreparedStatement smt = con.prepareStatement("SELECT * FROM userdatatable WHERE user_id = ?");
		smt.setString(1,userID);
		ResultSet rs = smt.executeQuery();
		while(rs.next())
		{
			result = rs.getString("user_id");
		}
		rs.close();
		smt.close();
		con.close();
		if (result.length()>0)
		{
			return true;
		}
		else
			return false;
		}catch (Exception e) {
			System.out.println(e);
		}
		return false;
		
	}
	

	public double getRequiredCalories(String userID) {
		double reqCal = 0;
		String reqCalString = null;


		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("SELECT reqcalday FROM userdatatable WHERE user_id=?");

			smt.setString(1,userID);
			ResultSet rs = smt.executeQuery();
			reqCalString = rs.getString("reqcalday");
			reqCal = Double.parseDouble(reqCalString);

			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}

		return reqCal;
	}
	
	

	
//	@Override
//	String search(String text) throws Exception {
//		//Write your code here
//		String result = null;
//		try {
//			Connection con = getConnection();
//			PreparedStatement smt = con.prepareStatement("SELECT response FROM msg where keyword like concat('%', ? ,'%')");
//			smt.setString(1,text);
//			ResultSet rs = smt.executeQuery();
//			while(rs.next())
//			{
//				result = rs.getString("response");
//			}
//			rs.close();
//			smt.close();
//			con.close();
//		}catch (Exception e) {
//			System.out.println(e);
//		}
//		
//		if(result!=null)
//			return result;
//		throw new Exception("NOT FOUND");
//		
//	}
//	
	
//	public void updateCalperDay(String UserId, float  )
//	{
//		try {
//			String totalCalList = null;
//			String totalDates = null;
//			bool found=false;
//			String delimiter = ";";
//			String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());  // current date 
//
//
//			Connection con = getConnection();
//			PreparedStatement smt1 = con.prepareStatement("SELECT calperday,dates FROM userdatatable WHERE user_id=?");
//			smt1.setString(1,UserId);
//			ResultSet rs = smt1.executeQuery();
//			while(rs.next())
//			{
//				totalCalList = rs.getString("calperday");
//				totalDates = rs.getString("dates");	
//			}
//
//			String[] partsOfCal = totalCalList.split(";");
//			String[] partsOfDate = totalDates.split(";");
//			
//			for (int i=0;i<partsOfDate.length;i++)
//			{
//				if(partsofDate[i]==date)
//				{
//				float changeCal = Float.parseFloat(partsofCal[i]);
//				changeCal+=calpermeal;
//				partsofCal[i]=String.parseString(changeCal);
//				found=true;
//				break;
//				}
//			}
//			totalCalList = String.join(delimiter, partsOfCal);
//			totalDates = String.join(delimiter, partsofDate);
//			if(found==false)
//			{
//			totalCalList=totalCalList+delimiter+String.parseString(calpermeal);
//			totalDates=totalCalList+delimiter+date;
//			}
//
//
//			PreparedStatement smt2 = con.prepareStatement("UPDATE userdatatable SET calperday=?,SET dates=? WHERE user_id='?'");
//			smt2.setString(1,totalCalList);
//			smt2.setString(2,totalDates);
//			smt2.setString(3,UserId);
//			ResultSet rs2 = smt2.executeQuery();
//			rs.close();
//			rs2.close();
//			smt2.close();
//			smt1.close();
//			con.close();
//
//		}
//		catch (Exception e) {
//			System.out.println(e);
//		}
//	}
	
	private Connection getConnection() throws URISyntaxException, SQLException {
		Connection connection;
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() +  "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

//		log.info("Username: {} Password: {}", username, password);
//		log.info ("dbUrl: {}", dbUrl);
		
		connection = DriverManager.getConnection(dbUrl, username, password);

		return connection;
	}
	
}

