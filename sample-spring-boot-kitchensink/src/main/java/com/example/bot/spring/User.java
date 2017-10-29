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
