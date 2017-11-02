package com.example.bot.spring;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInputDatabaseEngine extends DatabaseEngine {
	public void updateWeight(String UserId, float weight)
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET weight=? WHERE user_id='?'");
			smt.setFloat(1,weight);
			smt.setString(2,UserId);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void updateHeight(String UserId, float height)
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET height=? WHERE user_id=?");
			smt.setFloat(1,height);
			smt.setString(2,UserId);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void updateGymFrequency(String UserId, int gymFrequency)
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET gymfrequency=? WHERE user_id='?'");
			smt.setInt(1,gymFrequency);
			smt.setString(2,UserId);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void updateBMI(String UserId, float weight)
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET weight=? WHERE user_id='?'");
			smt.setFloat(1,weight);
			smt.setString(2,UserId);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void updateUserBMR(String UserId, float weight)
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET weight=? WHERE user_id='?'");
			smt.setFloat(1,weight);
			smt.setString(2,UserId);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void updateLoseGain(String UserId, float loseGain)
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET losegainperweek=? WHERE user_id='?'");
			smt.setFloat(1,loseGain);
			smt.setString(2,UserId);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void updateAge(String UserId, int age)
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET age=? WHERE user_id='?'");
			smt.setInt(1,age);
			smt.setString(2,UserId);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void updateWaterReminder(String UserId, int waterReminder)
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET waterreminder=? WHERE user_id='?'");
			smt.setInt(1,waterReminder);
			smt.setString(2,UserId);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void updateUserName(String UserId, String name)
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET name='?' WHERE user_id='?'");
			smt.setString(1,name);
			smt.setString(2,UserId);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void updateGender(String UserId, String gender)
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET gender='?' WHERE user_id='?'");
			smt.setString(1,gender);
			smt.setString(2,UserId);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void updateReqCalDay(String UserId, double reqcal)
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET reqcalday=? WHERE user_id='?'");
			smt.setDouble(1,reqcal);
			smt.setString(2,UserId);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void CreateNewUser(User new_User) throws Exception{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("INSERT INTO userdatatable (weight, user_id, height, gymFrequency, bmi, bmr, loseGainPerWeek, age, waterReminder, name, gender, goal, reqcalday) VALUES (0.0,?,0.0,0,0.0,0.0,0.0,0,0,'noname','nogender','nogoal',0)");
			smt.setString(1,new_User.getUserId());
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
			
		}catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	public boolean searchUser(String userID)
	{
		try {
		boolean found = false;
		String result = null;
		Connection con = getConnection();
		PreparedStatement smt = con.prepareStatement("SELECT * FROM userdatatable WHERE user_id = '?'");
		smt.setString(1,userID);
		ResultSet rs = smt.executeQuery();
		while(rs.next())
		{
			result = rs.getString("user_id");
		}
		rs.close();
		smt.close();
		con.close();
		if (result.length()>0)
		{
			return true;
		}
		else
			return false;
		}catch (Exception e) {
			System.out.println(e);
		}
		return false;
		
	}
	
//	public void newUser(User new_User) throws Exception{
//		boolean success = false;
//		try {
//			Connection con = getConnection();
//
//			PreparedStatement smt = con.prepareStatement("INSERT INTO user (weight, userId, height, gymFrequency, bmi, bmr, loseGainPerWeek, age, waterReminder, name, gender, goal, reqcalday) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
//			smt.setString(1,new_User.getWeight());
//			smt.setString(2,new_User.getUserId());
//			smt.setString(3,new_User.getHeight());
//			smt.setString(4,new_User.getGymFrequency());
//			smt.setString(5,new_User.getBMI());
//			smt.setString(6,new_User.getBMR());
//			smt.setString(7,new_User.getLoseGainPerWeek());
//			smt.setString(8,new_User.getAge());
//			smt.setString(9,new_User.getWaterReminder());
//			smt.setString(10,new_User.getName());
//			smt.setString(11,new_User.getGender());
//			smt.setString(12,new_User.getGoal());
//			smt.setString(13,new_User.getCalDay());
//			ResultSet rs = smt.executeQuery();
//			rs.close();
//			smt.close();
//			con.close();
//			
//		}catch (Exception e) {
//			System.out.println(e);
//		}
//		
//		
//	}
	
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



//package com.example.bot.spring;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;



//import java.sql.SQLException;
//
//public class UserInputDatabaseEngine extends DatabaseEngine {
//	public void CreateNewUser(User new_User) throws Exception{
//		try {
//			Connection con = getConnection();
//			PreparedStatement smt = con.prepareStatement("INSERT INTO userdatatable (weight, user_id, height, gymFrequency, bmi, bmr, loseGainPerWeek, age, waterReminder, name, gender, goal, reqcalday) VALUES (0.0,?,0.0,0,0.0,0.0,0.0,0,0,'noname','nogender','nogoal',0)");
//			smt.setString(1,new_User.getUserId());
//			ResultSet rs = smt.executeQuery();
//			rs.close();
//			smt.close();
//			con.close();
//			
//		}catch (Exception e) {
//			System.out.println(e);
//		}
//		
//	}
//	
//	public boolean searchUser(String userID)
//	{
//		try {
//		boolean found = false;
//		String result = null;
//		Connection con = getConnection();
//		PreparedStatement smt = con.prepareStatement("SELECT * FROM userdatatable WHERE user_id = '?'");
//		smt.setString(1,userID);
//		ResultSet rs = smt.executeQuery();
//		while(rs.next())
//		{
//			result = rs.getString("user_id");
//		}
//		rs.close();
//		smt.close();
//		con.close();
//		if (result.length()>0)
//		{
//			return true;
//		}
//		else
//			return false;
//		}catch (Exception e) {
//			System.out.println(e);
//		}
//		return false;
//		
//	}
//	
////	public void newUser(User new_User) throws Exception{
////		boolean success = false;
////		try {
////			Connection con = getConnection();
////
////			PreparedStatement smt = con.prepareStatement("INSERT INTO user (weight, userId, height, gymFrequency, bmi, bmr, loseGainPerWeek, age, waterReminder, name, gender, goal, reqcalday) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
////			smt.setString(1,new_User.getWeight());
////			smt.setString(2,new_User.getUserId());
////			smt.setString(3,new_User.getHeight());
////			smt.setString(4,new_User.getGymFrequency());
////			smt.setString(5,new_User.getBMI());
////			smt.setString(6,new_User.getBMR());
////			smt.setString(7,new_User.getLoseGainPerWeek());
////			smt.setString(8,new_User.getAge());
////			smt.setString(9,new_User.getWaterReminder());
////			smt.setString(10,new_User.getName());
////			smt.setString(11,new_User.getGender());
////			smt.setString(12,new_User.getGoal());
////			smt.setString(13,new_User.getCalDay());
////			ResultSet rs = smt.executeQuery();
////			rs.close();
////			smt.close();
////			con.close();
////			
////		}catch (Exception e) {
////			System.out.println(e);
////		}
////		
////		
////	}
//	
//	@Override
//	String search(String text) throws Exception {
//		//Write your code here
//		String result = null;
//		try {
//			Connection con = getConnection();
//			PreparedStatement smt = con.prepareStatement("SELECT response FROM msg where keyword like concat('%', ? ,'%')");
//			smt.setString(1,text);
//			ResultSet rs = smt.executeQuery();
//			while(rs.next())
//			{
//				result = rs.getString("response");
//			}
//			rs.close();
//			smt.close();
//			con.close();
//		}catch (Exception e) {
//			System.out.println(e);
//		}
//		
//		if(result!=null)
//			return result;
//		throw new Exception("NOT FOUND");
//		
//	}
//	
//	
//	private Connection getConnection() throws URISyntaxException, SQLException {
//		Connection connection;
//		URI dbUri = new URI(System.getenv("DATABASE_URL"));
//
//		String username = dbUri.getUserInfo().split(":")[0];
//		String password = dbUri.getUserInfo().split(":")[1];
//		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() +  "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
//
////		log.info("Username: {} Password: {}", username, password);
////		log.info ("dbUrl: {}", dbUrl);
//		
//		connection = DriverManager.getConnection(dbUrl, username, password);
//
//		return connection;
//	}
//
//}
