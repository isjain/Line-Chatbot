package com.example.bot.spring;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInputDatabaseEngine extends DatabaseEngine {
<<<<<<< HEAD

	
	public void updateWeight(String UserId, float weight)
=======
	public void updateUserWeight(String UserId, float weight)
>>>>>>> master
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET weight=? WHERE user_id=?");

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
	
<<<<<<< HEAD

	public void updateHeight(String UserId, float height)
=======
	public void updateUserHeight(String UserId, float height)
>>>>>>> master
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
	
<<<<<<< HEAD

	public void updateGymFrequency(String UserId, int gymFrequency)
=======
	public void updateUserGymFrequency(String UserId, int gymFrequency)
>>>>>>> master
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET gymfrequency=? WHERE user_id=?");

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
	
<<<<<<< HEAD

	public void updateBMI(String UserId, float weight)
=======
	public void updateUserBMI(String UserId, float weight)
>>>>>>> master
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET weight=? WHERE user_id=?");

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
	
<<<<<<< HEAD

	public void updateUserBMR(String UserId, float weight)
=======
	public void UpdateUserBMR(String UserId, float weight)
>>>>>>> master
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET weight=? WHERE user_id=?");

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
	
<<<<<<< HEAD

	public void updateLoseGain(String UserId, float loseGain)
=======
	public void UpdateUserLoseGain(String UserId, float loseGain)
>>>>>>> master
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET losegainperweek=? WHERE user_id=?");

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
	
<<<<<<< HEAD

	public void updateAge(String UserId, int age)
=======
	public void UpdateUserAge(String UserId, int age)
>>>>>>> master
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET age=? WHERE user_id=?");

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
	
<<<<<<< HEAD

	public void updateWaterReminder(String UserId, int waterReminder)
=======
	public void UpdateUserWaterReminder(String UserId, int waterReminder)
>>>>>>> master
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET waterreminder=? WHERE user_id=?");

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
	
<<<<<<< HEAD
	public void updateUserName(String UserId, String name)
=======
	public void UpdateUserName(String UserId, String name)
>>>>>>> master
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET name=? WHERE user_id=?");

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
	
<<<<<<< HEAD
	public void updateGender(String UserId, String gender)
=======
	public void UserSetCalories(String UserId, String name)
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
	
	
	
	public void UpdateUserGender(String UserId, String gender)
>>>>>>> master
	{
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("UPDATE userdatatable SET gender=? WHERE user_id=?");

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
	
<<<<<<< HEAD

	public User getUserRecord(String UserId) {
		boolean found = false;
		String id = UserId;
		float weight=0;
		float height=0;
		int gymFrequency=0;
		float loseGainPerWeek=0;
		int age=0;
		float waterReminder=0;
		String name="";
		String gender="";
		double calDay=0;
		User user = new User(id);
		
		try {
			
			
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("SELECT * FROM userdatatable WHERE user_id = ?");
			smt.setString(1,id);
			ResultSet rs = smt.executeQuery();
			while(rs.next())
			{
				weight = Float.parseFloat(rs.getString("weight"));
				height = Float.parseFloat(rs.getString("height"));
				gymFrequency = Integer.parseInt(rs.getString("gymfrequency"));
				loseGainPerWeek = Float.parseFloat(rs.getString("losegainperweek"));
				age = Integer.parseInt(rs.getString("age"));
				waterReminder = Float.parseFloat(rs.getString("waterreminder"));
				name = rs.getString("name");
				gender = rs.getString("gender");
				calDay = Double.parseDouble(rs.getString("reqcalday"));
			}
=======
	public void UpdateUserReqCalDay(String UserId, double reqcal)
	{
		try {
			String weight = null;
			String height = null;
			String gender = null;
			String age = null;
			String losegainperweek = null;
			String gymfrequency = null;
			Connection con = getConnection();
			PreparedStatement smt1 = con.prepareStatement("SELECT * FROM userdatatable WHERE user_id=?");
			smt1.setString(1,UserId);
			while(rs.next())
			{
				weight = rs.getString("weight");
				height = rs.getString("height");
				age = rs.getString("age");
				losegainperweek = rs.getString("losegainperweek");
				gymfrequency = rs.getString("gymfrequency");	
			}
			float weight1 = parseFloat(weight);
			float height1 = parseFloat(height);
			int age1 = parseInt(age);
			int losegainperweek1= parseInt(losegainperweek);
			int gymfrequency1 = parseInt(gymfrequency);
			float calDayReq = setCalDay(gender, weight1, height1, age1, losegainperweek1, gymfrequency1);
			PreparedStatement smt2 = con.prepareStatement("UPDATE userdatatable SET reqcalday=? WHERE user_id='?'");
			smt.setFloat(1,calDayReq);
			smt.setString(2,UserId);
			ResultSet rs = smt2.executeQuery();
>>>>>>> master
			rs.close();
			smt.close();
			con.close();
			user.setWeight(weight);
			user.setAge(age);
			user.setGender(gender);
			user.setLostGainPerWeek(loseGainPerWeek);
			user.setGymFrequency(gymFrequency);
			user.setName(name);
			user.setHeight(height);
			user.setWaterReminder(waterReminder);
			user.setCalDay(calDay);

			return user;
			}catch (Exception e) {
				System.out.println(e);
			}
		return user;
	}



	public void updateReqCalDay(String UserId, double reqcal)
	{
		try {
			String weight = null;
			String height = null;
			String gender = null;
			String age = null;
			String losegainperweek = null;
			String gymfrequency = null;
			Connection con = getConnection();
			PreparedStatement smt1 = con.prepareStatement("SELECT * FROM userdatatable WHERE user_id=?");
			smt1.setString(1,UserId);
			ResultSet rs = smt1.executeQuery();
			while(rs.next())
			{
				weight = rs.getString("weight");
				height = rs.getString("height");
				age = rs.getString("age");
				losegainperweek = rs.getString("losegainperweek");
				gymfrequency = rs.getString("gymfrequency");	
			}
			float weight1 = Float.parseFloat(weight);
			float height1 = Float.parseFloat(height);
			int age1 = Integer.parseInt(age);
			int losegainperweek1= Integer.parseInt(losegainperweek);
			int gymfrequency1 = Integer.parseInt(gymfrequency);
			double calDayReq = setCalDay(gender, weight1, height1, age1, losegainperweek1, gymfrequency1);
			PreparedStatement smt2 = con.prepareStatement("UPDATE userdatatable SET reqcalday=? WHERE user_id='?'");
			smt2.setDouble(1,calDayReq);
			smt2.setString(2,UserId);
			ResultSet rs2 = smt2.executeQuery();
			rs.close();
			rs2.close();
			smt2.close();
			smt1.close();
			con.close();

		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
<<<<<<< HEAD

	public double setCalDay(String gender, float weight, float height, int age, int loseGainPerWeek, int gymFrequency) {
		double calDay=0;
=======
	public float setCalDay(String gender, float weight, float height, int age, int loseGainPerWeek, int gymFrequency) {
		float calDay;
>>>>>>> master
		float goal_bmr;
		if(gender=="male") {
			goal_bmr= (float) (10 * (weight+loseGainPerWeek) + 6.25 * height - 5 * age + 5);
		}
		else {
			goal_bmr=(float) (10 * (weight+loseGainPerWeek) + 6.25 * height - 5 * age - 161);
		}
		switch (gymFrequency) {
		case 0:calDay = goal_bmr*1.2;
				break;
		case 1:
		case 2:
		case 3:calDay = goal_bmr*1.375;
				break;
		case 4:
		case 5:calDay = goal_bmr*1.55;
		
				break;
		case 6:
		case 7:calDay = goal_bmr*1.725;
				break;
		}
		return calDay;
	}
<<<<<<< HEAD

=======
	
>>>>>>> master
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
		PreparedStatement smt = con.prepareStatement("SELECT * FROM userdatatable WHERE user_id = ?");
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

