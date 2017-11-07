package com.example.bot.spring;

import lombok.extern.slf4j.Slf4j;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.net.URISyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

public class RecommendationDatabaseEngine extends DatabaseEngine {
	
	
	
	public Dish[] findCaloricContent(Dish[] dishes) {
		
		
			
			try {
				
				Connection con = getConnection();
				for(Dish d: dishes) {
					PreparedStatement smt = con.prepareStatement("SELECT * FROM nutrienttable WHERE Description LIKE '%?%' and Description LIKE '%?%' and Description LIKE '%?%' and Description LIKE '%?%' and Description LIKE '%?%'");
					String[] key = d.getKeywords();
					for(int i=1;i<=5;i++) {
						smt.setString(i,key[i-1]);
					}
					ResultSet rs = smt.executeQuery();
					while(rs.next()) {
						d.setCalories(Double.parseDouble(rs.getString("Energy")));
						d.setDishId(rs.getString("Id"));
					}
					rs.close();
					smt.close();
				}
				con.close();
			}catch(Exception e) {
				System.out.println(e);
			}
			return dishes;
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