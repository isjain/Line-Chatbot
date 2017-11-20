package com.example.bot.spring;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
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
