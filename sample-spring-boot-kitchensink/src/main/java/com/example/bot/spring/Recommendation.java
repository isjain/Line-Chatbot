//package testing;

package com.example.bot.spring;

import java.util.Random;
import java.net.URI;
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
	
	public void findRecommendation()
	{
		
	}
	
	public Dish[] getRecommendedDishes() 
	{
		return rDishes;
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