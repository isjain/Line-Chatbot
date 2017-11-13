package com.example.bot.spring;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CouponDatabaseEngine extends DatabaseEngine {
	int generateNewCode throws Exception{
		try {
				Connection con = getConnection();
				PreparedStatement smt = con.prepareStatement("SELECT couponcode FROM usercouponlist");
				ResultSet rs = smt.executeQuery();
				int code=0;
				
				boolean couponFound = 0;
				while (couponFound==0)
				{
					code= (100000 + ran.nextInt(899999)).toString;
					while(rs.next())
					{
						result = rs.getInt("couponcode");
						if (code==result)
						{
							couponFound=0;
							break;
						}
					}
					rs.close();
					smt.close();
					con.close();
					return code;	
				}
			
		} catch (Exception e) {
			System.out.println(e);
		}
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
