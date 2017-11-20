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
	
	
	public String giveVegDishes(String input) {
		
		String msg = "The vegetarian dishes at "+ input + " are:\n\n";
		String fragment= "";
		
		
		try {
			
				Connection con = getConnection();
				PreparedStatement smt = con.prepareStatement("SELECT * FROM campusdishes WHERE location = ? ORDER BY calories");
				smt.setString(1,input);
				ResultSet rs = smt.executeQuery();
				while(rs.next())
				{
					fragment = rs.getString("name");
					msg = msg + fragment + "\n";
					
					
				}
				rs.close();
				smt.close();
				con.close();
			
		}catch(Exception e) {
			System.out.println(e);
		}
		
		return msg;
		
	}
	
	
	public Dish[] findCaloricContent(Dish[] dishes) {
		
		
			
			try {
				
				Connection con = getConnection();
				
				for(Dish d: dishes) { 

					String st = "select * from nutrienttable where ";
					String[] key = d.getKeywords();
					for (int j=0;j<key.length;j++)
					{
						st = st + "lower(Description) like ?";
						if(j!=key.length-1)
						{
							st = st + " and ";
						}
					}
					PreparedStatement smt = con.prepareStatement(st);


					for(int i=1;i<=key.length;i++)
					{
						smt.setString(i, "%"+key[i-1]+"%");
					}
					
					System.out.println("Statement: "+smt);
					ResultSet rs = smt.executeQuery();
					double k = 0;
					String l = "";
					double wt=0;
					int min_words=10;
					while(rs.next()) {
						String tempo = rs.getString("Description");
						String[] tempo_arr = tempo.toLowerCase().split(",");
						//Check if the new record is a better match by encapsulating dish in less keywords
						if(tempo_arr.length<min_words) {
						 k = Double.parseDouble(rs.getString("energy"));
						 l = rs.getString("ndb_no");
						 wt = Double.parseDouble(rs.getString("weight"));
						 min_words = tempo_arr.length;
						}
					}
					if(!Double.isNaN(k))
					{	d.setCalories(k);
						d.setDishId(l);
						d.setWeight(wt);
					}
					else
					{
						d.setCalories(0);
						d.setDishId(null);
						d.setWeight(0);
						String temp = d.getName();
						d.setName(temp+" "+"Not found in database");
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