//
//String text = null;
//private void handleTextContent(String replyToken, Event event, TextMessageContent content) {
//    	    			text = content.getText();
//    }
package com.example.bot.spring;
public class setNewUser {
	String getUserWeight = "Enter your weight in kgs";
	String getUserName = "Enter your name";
	String getUserGender = "Enter your gender (male or female";
	String getUserGoal = "Please enter your goal";
	String getUserGymFrequency = "Please enter the number of times you exercise in a week (0-7)";
	String getUserHeight = "Please enter your height in cm";
	String getWaterReminderFreq = "Please enter the number of reminder notifications you would like in a day (recommended is 8)";
	String getUserLoseGain = "Please enter the amount of weight you wish to lose per week in kgs";
	
	
	private float weight;
	private int userId;
	private float height;
	private int gymFrequency;
	
	private float loseGainPerWeek;
	private int age;
	private int waterReminder;
	
	private String name;
	private String gender;
	private String goal;
	
	void createNewUser()
	{
		this.replyText(
                replyToken,
                getUserName
        );
		this.replyText(
                replyToken,
                getUserName
        );
		
		
		
		
	}


}
