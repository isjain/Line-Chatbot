package com.example.bot.spring;

import javax.swing.JOptionPane; // one of the java alert libraries i found online
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.model.PushMessage;
import retrofit2.Response;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.profile.UserProfileResponse;
import com.linecorp.bot.client.LineMessagingServiceBuilder;



/**
* User class is used to create new users and set their attributes.
*
* @author Project Group 25
* @version 1.0
* @since 2017-11-20
*/
public class User {

	private float weight;
	private String userId;
	private float height;
	private int gymFrequency;
	private float bmi;
	private float bmr;
	private float loseGainPerWeek;
	private int age;
	private float waterReminder;
	private String restrictions;
	private String name;
	private String gender;
	private String goal;
	private double calDay;   //required number of cals per day
	private String calperday;  //actual consumption of cals
	private String dates;


	// weight should be in kgs
	//height should be in cms
	//age should be in years
	
	// For bmi and bmr, when the user is inputting, need to invoke set methods as constructor wont do it

	public User(String userId) {
		
		this.name= null;
		this.userId= userId;
		this.height= 0;
		this.gymFrequency= 0;
		this.loseGainPerWeek= 0;
		this.age= 0;
		this.waterReminder= 0;
		this.weight= 0;
		this.gender= null;
//		this.goal= goal;
		this.restrictions="none";
	}
	
	public void setRestrictions(String r) {
		
		this.restrictions = r;
	}
	
	public void setAge(int w) {
		this.age = w;
	}
	public void setWeight(float w) {
		this.weight = w;
	}
	public void setGender(String w) {
		this.gender = w;
	}
	public void setLostGainPerWeek(float w) {
		this.loseGainPerWeek = w;
	}
	public void setGymFrequency(int w) {
		if(w>0 && w<8)
			this.gymFrequency = w;
	}
	public void setHeight(float w) {
		this.height = w;
	}
	public void setName(String n) {
		this.name = n;
	}
	public void setBMI() {
		bmi= weight/((height)*height);
	}
	public void setCalperday(String n) {
		this.calperday = calperday;
	}
	public void setdates(String n) {
		this.dates = dates;
	}
	
	public void setBMR() {
		
		if(gender=="male") {
			bmr= (float) (10 * weight + 6.25 * height - 5 * age + 5);
		}
		else {
			bmr=(float) (10 * weight + 6.25 * height - 5 * age - 161);
		}
	}
	//added func to calculate req number of calories per day
	
	public void setCalDay() {
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
	}
	public void setCalDay(double c) {
		this.calDay = c;
	}	
	public String getWeight() {
		return Integer.toString((int) weight);
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getHeight() {
		return Integer.toString((int) height);
	}
	
	public String getGymFrequency() {
		return Integer.toString(gymFrequency);

	}
	
	public String getRestrictions() {
		return restrictions;
	}
	
	public String getBMR() {
		return Integer.toString((int) bmr);
	}
	
	public String getBMI() {
		return Integer.toString((int) bmi);
	}
	
	public String getLoseGainPerWeek() {
		return Integer.toString((int) loseGainPerWeek);
	}
	
	public String getAge() {
		return Integer.toString(age);
	}
	
	public String getWaterReminder() {
		return Float.toString(waterReminder);
	}
	
	public String getName() {
		return name;
	}
	
	public String getGender() {
		return gender;
	}
	
	public String getGoal() {
		return goal;
	}
	public String getCalDay() {
		return Integer.toString((int)calDay);
	}
	public void setWaterReminder(float w) {
		this.waterReminder = w;
	}

	
}
