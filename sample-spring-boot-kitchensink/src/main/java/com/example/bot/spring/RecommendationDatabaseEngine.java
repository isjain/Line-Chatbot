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
					d.setCalories(k);
					d.setDishId(l);
					d.setWeight(wt);
					rs.close();
					smt.close();
					
				}
				
			con.close();
			}catch(Exception e) {
				System.out.println(e);
			}
			return dishes;
		}
	
	public void useStoredCal(String UserId)
	{
		try {
			String totalCalList = null;
			double reqperday=0;
			double total=0;
			double avgcal=0;
			double differCal=0;
			double newrec=0;
			Connection con = getConnection();
			PreparedStatement smt1 = con.prepareStatement("SELECT calperday,reqcalday FROM userdatatable WHERE user_id=?");
			smt1.setString(1,UserId);
			ResultSet rs = smt1.executeQuery();
			while(rs.next())
			{
				totalCalList = rs.getString("calperday");
				reqperday=rs.getFloat("reqcalday");	
			}
			if(totalCalList!=null)
			{	String[] partsOfCal = totalCalList.split(";");
			   if(partsOfCal.length>7)
			   {
					for (int i=0;i<partsOfCal.length;i++)
					{
					total += Double.parseDouble(partsOfCal[i]);
					}
				
				avgcal=total/(partsOfCal.length);
				newrec=reqperday-(avgcal-reqperday);	
			   }
			   else
			   {
				   newrec=reqperday;
			   }
			}

			PreparedStatement smt2 = con.prepareStatement("UPDATE userdatatable SET reqcalday=? WHERE user_id=?");
			smt2.setDouble(1,newrec);
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