package com.example.bot.spring;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInputDatabaseEngine extends DatabaseEngine {
	
	
	public void newUser(User new_User) throws Exception{
		boolean success = false;
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("INSERT INTO user (weight, userId, height, gymFrequency, bmi, bmr, loseGainPerWeek, age, waterReminder, name, gender, goal) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
			smt.setString(1,new_User.getWeight());
			smt.setString(2,new_User.getUserId());
			smt.setString(3,new_User.getHeight());
			smt.setString(4,new_User.getGymFrequency());
			smt.setString(5,new_User.getBMI());
			smt.setString(6,new_User.getBMR());
			smt.setString(7,new_User.getLoseGainPerWeek());
			smt.setString(8,new_User.getAge());
			smt.setString(9,new_User.getWaterReminder());
			smt.setString(10,new_User.getName());
			smt.setString(11,new_User.getGender());
			smt.setString(12,new_User.getGoal());
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
			
		}catch (Exception e) {
			System.out.println(e);
		}
		
		
	}
	@Override
	String search(String text) throws Exception {
		//Write your code here
		String result = null;
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("SELECT response FROM msg where keyword like concat('%', ? ,'%')");
			smt.setString(1,text);
			ResultSet rs = smt.executeQuery();
			while(rs.next())
			{
				result = rs.getString("response");
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
	
	
	private Connection getConnection() throws URISyntaxException, SQLException {
		Connection connection;
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() +  "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

//		log.info("Username: {} Password: {}", username, password);
//		log.info ("dbUrl: {}", dbUrl);
		
		connection = DriverManager.getConnection(dbUrl, username, password);

		return connection;
	}

}
