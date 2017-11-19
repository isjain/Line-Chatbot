//package testing;
package com.example.bot.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.net.URI;
import java.lang.*;
//import java.math;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Recommendation{
   
	private Dish[] inputDishes;
	private User inputUser;
	private Dish[] rDishes;
    
	public Recommendation(User user, Dish[] dishes) {  
		inputDishes = dishes;
		inputUser = user;
		findRecommendation();	
	}
	public Dish[] getInputDishes() {
		return inputDishes;
	}
		
	public void findRecommendation()
	{
			rDishes = new Dish[inputDishes.length];
			for( int i=0; i<inputDishes.length; i++ )
				rDishes[i] = new Dish(inputDishes[i]);
//			rDishes = inputDishes.clone();
			double reqcal  = Double.parseDouble(inputUser.getCalDay());
			double mealcal = reqcal/3;
		    // Below lines are similar to insertion sort
		    for (int i = 1; i < rDishes.length; i++) {
		        double diff = Math.abs(rDishes[i].getpropCalories() - mealcal);
		 
		        // Insert arr[i] at correct place
		        int j = i - 1;
		        if (Math.abs(rDishes[j].getpropCalories() - mealcal) > diff) {
		            Dish temp = new Dish(rDishes[i]);
		            while (j >= 0 && Math.abs(rDishes[j].getpropCalories() - mealcal) > diff) {
		                rDishes[j + 1] = rDishes[j];
		                j--;
		            }
		            rDishes[j + 1] = temp;
		            
		        }
		    }
		    
		    //Diet Restrictions
		    List<Dish> restricted_dishes = new ArrayList<Dish>();
			String restr= inputUser.getRestrictions();
			String[] restric = restr.split(",");
			for(Dish d: rDishes)
			{	int skt=0;
				String[] kwrds = d.getKeywords();
				
				for(String st: kwrds)
				{	
					for(String st2: restric )
					{
						if(st.toLowerCase().equals(st2.toLowerCase()))
						{
							skt=1;
							break;
							
						}
						
					}
					
				}
				
				if(skt==0)
				{	
					restricted_dishes.add(new Dish(d));
					
				}
			}
			
			Dish[] temp_rDishes = restricted_dishes.toArray(new Dish[restricted_dishes.size()]);
		   
			rDishes = temp_rDishes;
		    		    
		    //keep only the first 5 elements of rDishes
		    if (rDishes.length>5)
		    {
			    Dish[] tempp = new Dish[5];
			    for(int i=0; i<5; i++) {
			    		tempp[i] = rDishes[i];
			    }
			    rDishes = tempp.clone();	   
		    }
		    
		    //portion size
		    for (int i = 0; i < rDishes.length; i++) {
		    		double portion = (reqcal/3)/rDishes[i].getpropCalories();
		    		if(portion>0.6)
		    			portion = Math.round(portion);
		    		else 
		    			portion=0.5;
		        rDishes[i].setPortion(portion);
		    }	
	}
	
	public Dish[] getRecommendedDishes() 
	{
		return rDishes;
	}
	
	public Dish[] getVegRecommendedDishes() {
		
		ArrayList<Dish> veg_dishes = new ArrayList<Dish>();
		String[] non_veg= {"chicken","pork","beef","fish","ham","sausage","sushi","hotdog","tuna","duck","lamb","pig","turkey","dog","goat","buffalo","cow","deer"};
		for(Dish d: rDishes)
		{	int s=0;
			String[] kwrds = d.getKeywords();
			for(String st: kwrds)
			{	
				for(String st2: non_veg )
				{
					if(st.toLowerCase().equals(st2))
					{
						s=1;
						
					}
					
				}
				
			}
			if(s!=1)
			{veg_dishes.add(new Dish(d));}
		}
		
		Dish[] veg_dishes2 = veg_dishes.toArray(new Dish[veg_dishes.size()]);
		
		return veg_dishes2;
		

		
	}


//------------motivational messages------------------

	public String motivationMessage(){
		Random rand = new Random();
		String[] msgs = {"good progress! One more step towards a healthier lifestyle", "add oil!", "strive for progress not perfection", "you can do it! The road may be bumpy, but stay committed to the process", "making excuses burns 0 calories per hour", "a little progress each day adds up to big results", "when you feel like stopping think about why you started", "the best project you will ever work on is you", "you're not there yet but you're closer than you were yesterday", "be stronger than your excuse", "nothing tastes as good as being healthy feels", "weight loss is not a physical challenge, it's a mental one", "three months from now you will thank yourself", "you are what you eat. So don't be fast, cheap, easy or fake", "if it was easy, everyone would do it", "eat for the body you want", "you don't get what you wish for, you get what you work for", "your stomach should not be a waste basket", "once you see results, it becomes an addiction"};
		int  n = rand.nextInt(msgs.length);
		return (inputUser.getName()+", "+msgs[n]);
	}
}












//	public String setMenu(String m) {menu=m; return menu; }
//	public float setCal(float c) {cal=c;  return cal; }
//	
//	public String getMenu() {return menu; }
//	public float getCal() {return cal; }
	
	

//	public static Recommendation[] sortedMenu(Recommendation [] r, String userId, int n ){
//			try {
//				String reply = database.search_calday();
//	    	} catch (Exception e) {
//	    		reply = text;
//	    	}
//			
//		float x = (Float.parseFloat(search_calday(userId)))/3;
//        //int n=3;  // IMP:figure out x from database and n from total menu list- HARD CODED RIGHT NOW
//		Recommendation temp;
//
//	    for (int i = 0; i < n; i++) {
//	      for (int j = 0; j < n-1; j++) {
//	        if (Math.abs((r[j].cal-x)) > Math.abs((r[j+1].cal-x))) 
//	        {
//	           temp = r[j];
//	           r[j] = r[j+1];
//	           r[j+1] = temp;
//	        }
//	      }
//	    }
//        input_into_database(r[0], userId);
//        return r;
//	} 
//


//    public static void input_into_database(Recommendation r, String userId) throws Exception {
//        boolean success = false;
//        try {
//            Connection con = getConnection();
//            PreparedStatement smt = con.prepareStatement("insert into userstoreddata (user_id, cal) values (?,?)");
//            smt.setString(1,userId);
//            smt.setString(2,Float.toString(r.cal));
//            ResultSet rs = smt.executeQuery();
//            rs.close();
//            smt.close();
//            con.close();
//        }catch (Exception e) {
//            System.out.println(e);
//        }
//        
//    }

 
// this is not the actual data we'll use, this just shows how to work with recommendation
	//objects and push them as a value to the sortedmenu function.