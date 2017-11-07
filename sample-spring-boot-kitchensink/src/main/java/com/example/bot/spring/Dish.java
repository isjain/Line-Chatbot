package com.example.bot.spring;

public class Dish {

	private String dishId;
	private double calories;
	private String name;
	private String[] keywords;
	private String sodium;
	private String fatty_acids;

	public Dish(String name) {
		this.dishId = null;
		this.name = name;
		this.keywords = name.split(" ");
		
	}
	public String getDishId() {
		return dishId;
	}
	public double getCalories() {
		return calories;
	}
	public String getName() {
		return name;
	}
	public String[] getKeywords() {
		return keywords;
	}
	
	public void setDishId(String id) {
		this.dishId = id;
	}
	public void setCalories(double cal) {
		this.calories = cal;
	}
	public void setName(String name) {
		this.name= name;
	}
	public void setKeywords(String[] kwords) {
		this.keywords = kwords;
	}

}
