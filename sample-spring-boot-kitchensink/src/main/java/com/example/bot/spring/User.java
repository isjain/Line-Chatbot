<<<<<<< HEAD
//package com.example.bot.spring;
//
//import javax.swing.JOptionPane; // one of the java alert libraries i found online
//
//public class User {
//
//	private float weight;
//	private int userId;
//	private float height;
//	private int gymFrequency;
//	private float bmi;
//	private float bmr;
//	private float loseGainPerWeek;
//	private int age;
//	private int waterReminder;
//	
//	private String name;
//	private String gender;
//	private String goal;
//  private float calDay;
//	
//	// weight should be in kgs
//	//height should be in cms
//	//age should be in years
//	
//	// For bmi and bmr, when the user is inputting, need to invoke set methods as constructor wont do it
//
//	public User(float weight, int userId, float height, int gymFrequency,
//			float loseGainPerWeek, int age, int waterReminder, String name, String gender, String goal) {
//		
//		this.name= name;
//		this.userId= userId;
//		this.height= height;
//		this.gymFrequency= gymFrequency;
//		this.loseGainPerWeek= loseGainPerWeek;
//		this.age= age;
//		this.waterReminder= waterReminder;
//		this.name= name;
//		this.gender= gender;
//		this.goal= goal;
//		
//	}
//	
//	public void getCalDay() {
//			switch(gymFrequency) {
//				case 0: calDay = bmr*1.2;
//						break;
//				case 1:
//				case 2: 
//				case 3: calDay = bmr*1.375;
//						break;
//				case 4:
//				case 5: calDay = bmr*1.55;
//						break;
//				case 6: 
//				case 7: calDay = bmr*1.725;
//						break;
//			}
//	}

//	public void setBMI() {
//		bmi= weight/((height)*height);
//	}
//	
//	public void serBMR() {
//		
//		if(gender=="male") {
//			bmr= (float) (10 * weight + 6.25 * height - 5 * age + 5);
//		}
//		else {
//			bmr=(float) (10 * weight + 6.25 * height - 5 * age - 161);
//		}
//	}
//	
//	public String getWeight() {
//		return Integer.toString((int) weight);
//	}
//	
//	public String getUserId() {
//		return Integer.toString((int) userId);
//	}
//	
//	public String getHeight() {
//		return Integer.toString((int) height);
//	}
//	
//	public String getGymFrequency() {
//		return Integer.toString((int) gymFrequency);
//
//	}
//	
//	public String getBMR() {
//		return Integer.toString((int)bmr);
//	}
//	
//	public String getBMI() {
//		return Integer.toString((int) bmi);
//	}
//	
//	public String getLoseGainPerWeek() {
//		return Integer.toString((int) loseGainPerWeek);
//	}
//	
//	public String getAge() {
//		return Integer.toString((int) age);
//	}
//	
//	public String getWaterReminder() {
//		return Integer.toString((int) waterReminder);
//	}
//	
//	public String getName() {
//		return name;
//	}
//	
//	public String getGender() {
//		return gender;
//	}
//	
//	public String getGoal() {
//		return goal;
//	}

//	
//	
//	
//	//Need to find a timer interval, which displays the reminder every 24/waterReminder hours
//	//The Joption code, displays the alert to the user right now
//	public void setWaterReminder(int waterReminder) {
//		// one way to do this, is to divide 4 daily reminders by 24 ie remind every 6 hours
//		for(int i=1; i<=waterReminder;i++) {
//			
//			JOptionPane.showMessageDialog(null, "Paani Paani time");
//		
//		}
//	}
//	
//
//	
//}
=======
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
	private float calDay;
	
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
	//where are these functions being called?
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
		return Integer.toString((int)weight);
	}
	
	public String getUserId() {
		return Integer.toString(userId);
	}
	
	public String getHeight() {
		return Integer.toString((int)height);
	}
	
	public String getGymFrequency() {
		return Integer.toString(gymFrequency);

	}
	
	public String getBMR() {
		return Integer.toString((int)bmr);
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
		return Integer.toString(waterReminder);
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
		return calDay;
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
>>>>>>> 3d1d6e80d8df8473e69780ac1992316c0a36fedc
