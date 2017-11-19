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
	
	public void redeemCode(String code, String UserId) {
		
		try {
			// check if the coupon code works
		Connection con = getConnection();
		PreparedStatement smt = con.prepareStatement("UPDATE usertablelist SET claimuser=? WHERE couponcode=?");
		smt.setString(1, UserId);
		smt.setString(2, code);
		System.out.println(smt);
		smt.close();
		con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	
	
	
	public String saveCouponCode(String UserId)
	{
		String code = "404";

		try {
			int codeint = generateNewCode();
			code = Integer.toString(codeint);
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("INSERT INTO usertablelist VALUES (?,'none',?)");
			smt.setString(2,code);
			smt.setString(1,UserId);
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
