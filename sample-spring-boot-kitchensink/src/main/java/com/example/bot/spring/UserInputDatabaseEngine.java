package com.example.bot.spring;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInputDatabaseEngine extends DatabaseEngine {
	
	@Override
	void newUser(User new_User) throws Exception{
		boolean success = false;
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("INSERT INTO user (weight, userId, height, gymFrequency, bmi, bmr, loseGainPerWeek, age, waterReminder, name, gender, goal) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
			smt.setString(1,new_User.weight);
			smt.setString(2,new_User.userId);
			smt.setString(3,new_User.height);
			smt.setString(4,new_User.gymFrequency);
			smt.setString(5,new_User.bmi);
			smt.setString(6,new_User.bmr);
			smt.setString(7,new_User.loseGainPerWeek);
			smt.setString(8,new_User.age);
			smt.setString(9,new_User.waterReminder);
			smt.setString(10,new_User.name);
			smt.setString(11,new_User.gender);
			smt.setString(12,new_User.goal);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
			
		}catch (Exception e) {
			System.out.println(e);
		}
		
		
	}
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

		log.info("Username: {} Password: {}", username, password);
		log.info ("dbUrl: {}", dbUrl);
		
		connection = DriverManager.getConnection(dbUrl, username, password);

		return connection;
	}

}
