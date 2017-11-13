package com.example.bot.spring;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class JSON_Conversion {

	public String ResultJSON(String jsonStr) throws JSONException {
		
		//String jsonStr=null; ---- need to initialize jsonStr as null and then check in Kitchen Sink
		//how to check if the user selects the jSON input String jsonStr=?
		
		//if(jsonStr!=null) {
		
				//jsonStr is the JSON input the user provides on the application
				//String jsonStr = "{\"userInput\": [{\r\n\t\"name\":\"Spicy Bean curd with Minced Pork served with Rice\",\r\n\t\"price\":35,\r\n\t\"ingredients\":[\"Pork\",\"Bean curd\",\"Rice\"]\r\n},\r\n{\r\n\t\"name\":\"Sweet and Sour Pork served with Rice\",\r\n\t\"price\":36,\r\n\t\"ingredients\":[\"Pork\",\"Sweet and Sour Sauce\",\"Pork\"]\r\n},\r\n{\r\n\t\"name\":\"Chili Chicken on Rice\",\r\n\t\"price\":28,\r\n\t\"ingredients\":[\"Chili\",\"Chicken\",\"Rice\"]\r\n}]}";
				JSONObject jsonObj = new JSONObject(jsonStr);
				//dishes is the JSONArray which holds all the dishes
				JSONArray dishes = jsonObj.getJSONArray("userInput"); //here userInput needs to be the name of the JSON array the user gives
				
				//Create a new String array that holds dishes one by one after being parsed for name, etc.
				String [] Dishes= new String[dishes.length()];
				
				// looping through All dishes given by the user
		        for (int i = 0; i < dishes.length(); i++) {
		            JSONObject dish = dishes.getJSONObject(i);
		            
		            // This extracts all the details for each individual dish 
		            String name = dish.getString("name");
		            int price = dish.getInt("price");
		            
		            JSONArray JSONingredients = dish.getJSONArray("ingredients");
		           
		            //String [] ingredients=  new String[JSONingredients.length()];  		
//		            ArrayList<String> ingredients = new ArrayList<String>();
//		            for(int n = 0; n < JSONingredients.length(); n++){
//		            	ingredients.add(JSONingredients.getJSONObject(n));
//		            }
//		            
		            //Need to store each dish and its values in an array
		            Dishes[i]= name + " " + JSONingredients + " " + price ;   
		            System.out.println(); //To get the next dish on next line
		            
		        }
	      
//		        for(int j=0;j<dishes.length();j++){
//		        	 System.out.println(Dishes[j]);	//Returns the String input from JSON in an Array for 
//		        }
		        
		        
		        StringBuilder strBuilder = new StringBuilder();
		        for (int i = 0; i < Dishes.length; i++) {
		           strBuilder.append(Dishes[i]);
		           strBuilder.append("\n");
		        }
		        String newString = strBuilder.toString();
//		        System.out.println(newString);
		        
		        
		        
		        
		        
		        return newString;
				}
	
//	public static void main(String [] args) throws JSONException{
//		
//		
//		ResultJSON();
//		
//		
//	}
	
	
	
			
}