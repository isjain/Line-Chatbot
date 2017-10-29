package com.example.bot.spring;

import javax.swing.JOptionPane; // one of the java alert libraries i found online

public class User {

	private float weight;
	private int userId;
	private float height;
	private int gymFrequency;
	private float bmi;
	private float bmr;
	private float loseGainPerWeek;
	private int age;
	private int waterReminder;
	
	private String name;
	private String gender;
	private String goal;
	
	// weight should be in kgs
	//height should be in cms
	//age should be in years
	
	// For bmi and bmr, when the user is inputting, need to invoke set methods as constructor wont do it

	public User(float weight, int userId, float height, int gymFrequency,
			float loseGainPerWeek, int age, int waterReminder, String name, String gender, String goal) {
		
		this.name= name;
		this.userId= userId;
		this.height= height;
		this.gymFrequency= gymFrequency;
		this.loseGainPerWeek= loseGainPerWeek;
		this.age= age;
		this.waterReminder= waterReminder;
		this.name= name;
		this.gender= gender;
		this.goal= goal;
		
	}
	
	public void setBMI() {
		bmi= weight/((height)*height);
	}
	
	public void serBMR() {
		
		if(gender=="male") {
			bmr= (float) (10 * weight + 6.25 * height - 5 * age + 5);
		}
		else {
			bmr=(float) (10 * weight + 6.25 * height - 5 * age - 161);
		}
	}
	
	public float getWeight() {
		return weight;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public float getHeight() {
		return height;
	}
	
	public int getGymFrequency() {
		return gymFrequency;

	}
	
	public float getBMR() {
		return bmr;
	}
	
	public float getBMI() {
		return bmi;
	}
	
	public float getLoseGainPerWeek() {
		return loseGainPerWeek;
	}
	
	public int getAge() {
		return age;
	}
	
	public int getWaterReminder() {
		return waterReminder;
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
	
	
	//Need to find a timer interval, which displays the reminder every 24/waterReminder hours
	//The Joption code, displays the alert to the user right now
	public void setWaterReminder(int waterReminder) {
		// one way to do this, is to divide 4 daily reminders by 24 ie remind every 6 hours
		for(int i=1; i<=waterReminder;i++) {
			
			JOptionPane.showMessageDialog(null, "Paani Paani time");
		
		}
	}
	

	
}
