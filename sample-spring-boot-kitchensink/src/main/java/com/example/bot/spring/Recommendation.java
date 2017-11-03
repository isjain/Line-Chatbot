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


public class Recommendation extends DatabaseEngine{
	
    @Override
    String search(String userId) throws Exception {
        //Write your code here
        String result = null;
        try {
            Connection con = getConnection();
            PreparedStatement smt = con.prepareStatement("select reqcalday from userdatatable where user_id = ?");
            smt.setString(1,userId);
            ResultSet rs = smt.executeQuery();
            while(rs.next())
            {
                result = rs.getString("reqcalday");
            }
            rs.close();
            smt.close();
            con.close();
        }catch (Exception e) {
            System.out.println(e);
        }
        
        if(result!=null)
            return result;
        throw new Exception("NOT FOUND");
        
    }
    
    private String menu;
	private float cal;
	
    
    
	public Recommendation(String string, int i) {   menu=string; cal=i;}
	public String setMenu(String m) {menu=m; return menu; }
	public float setCal(float c) {cal=c;  return cal; }
	
	public String getMenu() {return menu; }
	public float getCal() {return cal; }

	public static Recommendation[] sortedMenu(Recommendation [] r, String userId, int n ){
        float x = (Float.parseFloat(search(userId)))/3;
        //int n=3;  // IMP:figure out x from database and n from total menu list- HARD CODED RIGHT NOW
		Recommendation temp;

	    for (int i = 0; i < n; i++) {
	      for (int j = 0; j < n-1; j++) {
	        if (Math.abs((r[j].cal-x)) > Math.abs((r[j+1].cal-x))) 
	        {
	           temp = r[j];
	           r[j] = r[j+1];
	           r[j+1] = temp;
	        }
	      }
	    }
        input_into_database(r[0], userId);
        return r;
	} 

    public void input_into_database(Recommendation r, String userId) throws Exception {
        boolean success = false;
        try {
            Connection con = getConnection();
            PreparedStatement smt = con.prepareStatement("insert into userstoreddata (user_id, cal) values (?,?)");
            smt.setString(1,userId);
            smt.setString(2,Float.toString(r.cal));
            ResultSet rs = smt.executeQuery();
            rs.close();
            smt.close();
            con.close();
        }catch (Exception e) {
            System.out.println(e);
        }
        
    }
 
// this is not the actual data we'll use, this just shows how to work with recommendation
	//objects and push them as a value to the sortedmenu function.
 /*  public static void main(String[] args) throws Exception {
        Recommendation[] r = new Recommendation[] {
            new Recommendation("COMP3111", 1100),
            new Recommendation("COMP3311", 400),
            new Recommendation("COMP3311", 280),
        };
        
        String fromLang = "en";
        String toLang = "zh-CN";
        

        
        Recommendation[] Arr = new Recommendation[3];
        Arr=sortedMenu(r);
        for(int i=0;i<3;i++)
        { System.out.println(Arr[i].getMenu() + Arr[i].getCal());
            Translator.translate(fromLang, toLang, Arr[i].getMenu());}
    }  */
    private Connection getConnection() throws URISyntaxException, SQLException {
    	Connection connection;
    	URI dbUri = new URI(System.getenv("DATABASE_URL"));

    	String username = dbUri.getUserInfo().split(":")[0];
    	String password = dbUri.getUserInfo().split(":")[1];
    	String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() +  "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

//    	log.info("Username: {} Password: {}", username, password);
//    	log.info ("dbUrl: {}", dbUrl);

    	connection = DriverManager.getConnection(dbUrl, username, password);

    	return connection;
    	}
}





//------------motivational messages------------------
//public class motivation{
//
//public static String motivationMessage(){
//    Random rand = new Random();
//    String[] msgs = {"Good progress! One more step towards a healthier lifestyle", "Add oil!", "Strive for progress, not perfection", "The struggle you're in today is developing the strength you need for tomorrow", "Yes, you can! The road may be bumpy, but stay committed to the process.", "Making excuses burns 0 calories per hour."};
//    int  n = rand.nextInt(6);
//    return msgs[n];
//}
//}

