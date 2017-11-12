package com.example.bot.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;  


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
    		this.keywords = name.toLowerCase().split(" ");
    		cleanKWORDS();
		this.calories = 0;
		
	}
	
	public void cleanKWORDS() {
		
//		String[] dirty_words = {"the","and","with","or","of","a","an","&"," "};
		
		ArrayList<String> words = new ArrayList<String>();
		words.add("the");
		words.add("and");
		words.add("with");
		words.add("or");
		words.add("of");
		words.add("a");
		words.add("an");
		words.add("&");
		words.add(" ");
	    List<String> kwords = Arrays.asList(keywords);  
		kwords.removeAll(words);
		keywords = kwords.toArray(new String[kwords.size()]);
		System.out.println("keywords: "+keywords);
		System.out.println("kwords: "+kwords);
		System.out.println("words: "+words);

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
