package com.example.bot.spring;

import javax.swing.JOptionPane; // one of the java alert libraries i found online

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
	
	private String name;
	private String gender;
	private String goal;
	private double calDay;

	// weight should be in kgs
	//height should be in cms
	//age should be in years
	
	// For bmi and bmr, when the user is inputting, need to invoke set methods as constructor wont do it

	public User(String userId) {
		
//		this.name= name;
		this.userId= userId;
//		this.height= height;
//		this.gymFrequency= gymFrequency;
//		this.loseGainPerWeek= loseGainPerWeek;
//		this.age= age;
//		this.waterReminder= waterReminder;
//		this.name= name;
//		this.gender= gender;
//		this.goal= goal;
		
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
		switch (gymFrequency) {
		case 0:calDay = bmr*1.2;
				break;
		case 1:
		case 2:
		case 3:calDay = bmr*1.375;
				break;
		case 4:
		case 5:calDay = bmr*1.55;
				break;
		case 6:
		case 7:calDay = bmr*1.725;
				break;
		}
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

	
	
	//Need to find a timer interval, which displays the reminder every 24/waterReminder hours
	//The Joption code, displays the alert to the user right now
	public void setWaterReminder(float waterReminder) {
		// one way to do this, is to divide 4 daily reminders by 24 ie remind every 6 hours
		
		float gapHours = 24/waterReminder;
		
		Thread t = new Thread() {
		    @Override
		    public void run() {
		        while(true) {
		            try {
		                Thread.sleep(1000*60*60*((long)gapHours));
//		                "This is your reminder to drink water :)"
		                //Send notification to Line  
		                
		                
		            } catch (InterruptedException ie) {
		            }
		        }
		    }
		};
		t.start();
	
		
		

	
}
	

	
}
