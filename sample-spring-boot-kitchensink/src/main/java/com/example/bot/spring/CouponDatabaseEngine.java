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
	
	public boolean redeemCode(float code, String UserId)
	{
		try {
			// should check if the coupon code exists
			Connection con = getConnection();
//			PreparedStatement smt = con.prepareStatement("SELECT * FROM usertablelist WHERE couponcode=?");
//			PreparedStatement smt = con.prepareStatement("UPDATE usertablelist SET claimuser = ? WHERE couponcode = ?");
			PreparedStatement smt = con.prepareStatement("SELECT * FROM usertablelist WHERE couponcode= ?");
			smt.setFloat(1,code);
//			smt.setString(1, UserId);
			ResultSet rs = smt.executeQuery();
			rs.last();
			int rowCount = rs.getRow();
			if (rowCount==0)
			{// coupon code doesn't exist
				rs.close();
				smt.close();
				con.close();
				return false;
			}
			else {// coupon exists
				PreparedStatement smt2 = con.prepareStatement("UPDATE usertablelist SET claimuser = ? WHERE couponcode = ?");
				smt2.setString(1, UserId);
				smt2.setFloat(2, code);
				rs.close();
				smt2.close();
				con.close();
				smt.close();
				return true;
			}
			
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return false;
		
	}
	
	public float saveCouponCode(String UserId)
	{
		float code = 404;

		try {
			code = generateNewCode();
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("INSERT INTO usertablelist VALUES (?,?,'none')");
	smt.setFloat(1,code);
			smt.setString(2,UserId);
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
	
	float generateNewCode() throws Exception{
		try {
				Connection con = getConnection();
				PreparedStatement smt = con.prepareStatement("SELECT couponcode FROM usertablelist");
				ResultSet rs = smt.executeQuery();
				int code=0;
				int couponFound = 1;
				while (couponFound==1)
				{
					Random ran = new Random();
					code= (100000 + ran.nextInt(899999));
					couponFound=0;
					while(rs.next())
					{
						int result = rs.getInt("couponcode");
						if (code==result)
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
