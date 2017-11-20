package com.example.bot.spring;
import java.util.ArrayList;


import org.springframework.boot.SpringApplication;

/**
* DishAdapter deserializes Quote class from json into an arraylist of Dish objects
*
* @author Project Group 25
* @version 1.0
* @since 2017-11-20
*/
public class DishAdapter {
	public ArrayList<Dish> dishes=new ArrayList<Dish>();
	
	
	public DishAdapter(Quote[] test)
	{
	for (int i=0; i<test.length; i++)
	{
		dishes.add(new Dish((test[i].getName())));
//		dishes[i]=new Dish((test[i].getName()));
	}
	}
	

}
