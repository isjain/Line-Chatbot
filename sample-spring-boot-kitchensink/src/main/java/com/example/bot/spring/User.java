package com.example.bot.spring;

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

	
	public void setBMI() {
		bmi= weight/((height)*height);
	}
	// weight should be in kgs
	//height should be in cms
	//age should be in years
	public void serBMR() {
		
		if(gender=="male") {
			bmr= (float) (10 * weight + 6.25 * height - 5 * age + 5);
		}
		else {
			bmr=(float) (10 * weight + 6.25 * height - 5 * age - 161);
		}
	}
	

	
}
