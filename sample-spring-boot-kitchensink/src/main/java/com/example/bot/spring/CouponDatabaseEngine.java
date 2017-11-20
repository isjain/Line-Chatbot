package com.example.bot.spring;

import java.net.URI;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.Object;
import java.util.Random;
public class CouponDatabaseEngine extends DatabaseEngine {
	
	
	// thsi is not working properly, need to change to check whether it has been redeemed or not and the 5000 limit and return static image
	public boolean redeemCode(String code, String UserId) {
		boolean isChecked = false;
		// over here call a boolean to check if it has already been redeemed if it has been redeemed, then exit and return a string, saying "sorry this code has already been redeemed, otherwise execute""
		try {
			// check if the coupon code works
		Connection con = getConnection();
		isChecked = checkRedeemed(code);
		PreparedStatement smt = con.prepareStatement("UPDATE usertablelist SET claimuser=?, redeemed=1 WHERE couponcode=? AND redeemed=0");
		smt.setString(1, UserId);
		smt.setString(2, code);
		ResultSet rs = smt.executeQuery();
//		System.out.println("Shugan count : " + count);
		rs.close();
		System.out.println(smt);
		smt.close();
		con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		
		return isChecked;
	}
	
	public boolean isOldUser(String userId) {
		int userStatus =0;
		try {			
		Connection con = getConnection();
		PreparedStatement smt = con.prepareStatement("SELECT olduser from userdatatable where user_id=?");
		smt.setString(1, userId);
		ResultSet rs = smt.executeQuery();
		if (rs.next())
			userStatus = rs.getInt("olduser");
		rs.close();
		smt.close();
		con.close();
	
		}
		catch (Exception e) {
			System.out.println(e);
		}
		if (userStatus ==1)
			return true;
		
		return false;	
	}
	
	public boolean hasUserRedeemed(String userId) {
		int count =0;
		try {			
		Connection con = getConnection();
		PreparedStatement smt = con.prepareStatement("SELECT COUNT(*) FROM usertablelist WHERE claimuser=?");
		smt.setString(1, userId);
		ResultSet rs = smt.executeQuery();
		if (rs.next())
			count = rs.getInt("count");
		rs.close();
		smt.close();
		con.close();
	
		}
		catch (Exception e) {
			System.out.println(e);
		}
		if (count >0)
			return true;
		
		return false;
	}
	
	public String getRecommenderUserID(String code) {
		String recommender ="";
		try {
			// check if the coupon code works
			
		Connection con = getConnection();
		PreparedStatement smt = con.prepareStatement("SELECT issueuser FROM usertablelist WHERE couponcode=?");
		smt.setString(1, code);
		ResultSet rs = smt.executeQuery();
		if (rs.next())
			recommender = rs.getString("issueuser");
		rs.close();
		smt.close();
		con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return recommender;
		
	}
	
	public boolean isValidCode(String code) {
		int valid =0;
		try {
		Connection con = getConnection();
		PreparedStatement smt = con.prepareStatement("SELECT COUNT(*) FROM usertablelist WHERE couponcode=?");
		smt.setString(1, code);
		ResultSet rs = smt.executeQuery();
		if (rs.next())
			valid = rs.getInt("count");
		rs.close();
		smt.close();
		con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		if (valid == 0)
			return false;
		return true;
	}
	
	public boolean checkRedeemed(String code) {
		int redeemed =0;
		try {
			// check if the coupon code works
		Connection con = getConnection();
		PreparedStatement smt = con.prepareStatement("SELECT redeemed FROM usertablelist WHERE couponcode=?");
		smt.setString(1, code);
		ResultSet rs = smt.executeQuery();
		if (rs.next())
			redeemed = rs.getInt("redeemed");
		rs.close();
		smt.close();
		con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		if (redeemed == 1) {
			return false;
		}
		
		return true;
	}
		
	

	
	
	// check if setting to redeemed works
	
	public String saveCouponCode(String UserId)
	{
		String code = "404";
	
		// SELECT COUNT(*) FROM usertablelist; --> gets the amount of columns in a table
		try {
			int codeint = generateNewCode();
			code = Integer.toString(codeint);
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("INSERT INTO usertablelist VALUES (?,'none',?,0)");
			smt.setString(2,code);
			smt.setString(1,UserId);
//			smt.setInt(3, couponnumber);
			ResultSet rs = smt.executeQuery();
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return code;
	}
	
	int getCouponNumber() {
		int quant = 0;
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("SELECT COUNT(*) FROM usertablelist");
			ResultSet rs = smt.executeQuery();
			while (rs.next())
				quant = rs.getInt("count");
			System.out.println("Shugan : getcouponnumber() : " + quant);
			rs.close();
			smt.close();
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return quant;	
	}
	
	
	int generateNewCode() throws Exception{
		try {
				Connection con = getConnection();
				PreparedStatement smt = con.prepareStatement("SELECT couponcode FROM usertablelist");
				ResultSet rs = smt.executeQuery();
				int code =0;
				int couponFound = 1;
				while (couponFound==1)
				{
					Random ran = new Random();
					code= (100000 + ran.nextInt(899999));
					couponFound=0;
					while(rs.next())
					{
						String result = rs.getString("couponcode");
						if (code==Integer.parseInt(result))
						{
							couponFound=1;
						}
					}
					
					rs.close();
					smt.close();
					con.close();
					return code;	
				}
				return code;
//			
		} catch (Exception e) {
			System.out.println(e);
		}
		return 0;
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
