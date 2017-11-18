package com.example.bot.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;  
import java.util.*;


public class Dish {

	private String dishId;
	private double calories;
	private String name;
	private String[] keywords;
	private String sodium;
	private String fatty_acids;
	private double weight;

	public Dish(String name) {
		this.dishId = null;
		this.name = name;
    		this.keywords = name.toLowerCase().split(" ");
    		cleanKWORDS();    		
		this.calories = 0;
		this.weight = 0;
		
	}	
	public Dish(Dish dishes) {
		this.dishId = dishes.getDishId();
		this.name = dishes.getName();
    		this.keywords = dishes.getKeywords();    		
		this.calories = dishes.getCalories();
//		this.weight = dishes.getWeight();	
	}	
	public void cleanKWORDS() {	
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
		List<String> kwords = new ArrayList<String>();  
        Collections.addAll(kwords, keywords); 
		kwords.removeAll(words);
		keywords = kwords.toArray(new String[kwords.size()]);
      	System.out.println("keywords 2: "+Arrays.toString(keywords));
	}

	public String getDishId() {
		return dishId;
	}
	public double getCalories() {
		return calories;
	}
	//return calories per 600 gms of the food item
	public double getpropCalories() {
		return ((calories/weight)*600);
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
	public void setWeight(double wt) {
		this.weight = wt;
	}
	public void setName(String name) {
		this.name= name;
	}
	public void setKeywords(String[] kwords) {
		this.keywords = kwords;
	}

}
