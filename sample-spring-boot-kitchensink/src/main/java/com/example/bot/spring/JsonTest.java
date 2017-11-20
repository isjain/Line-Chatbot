package com.example.bot.spring;
import org.springframework.boot.SpringApplication;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import java.lang.Object;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import java.io.*;
//import org.apache.http.client.utils.URIBuilder;

public class JsonTest {
	
	public String getJSONweb(String url, String userID)
	{
	RestTemplate restTemplate = new RestTemplate();
	
//	JsonDatabaseEngine jsondb=new JsonDatabaseEngine();
//	jsondb.addjsonurl(url, userID);
////	String newjsonlink = "https://api.myjson.com/bins/d4t4b";
//	
//	String newjsonlink = jsondb.getjsonurl(userID);
//	System.out.println("Vinamra's json url" + newjsonlink + "ddd");

//	ResponseEntity<Quote[]> responseEntity = restTemplate.getForEntity(url, Quote[].class);
	ResponseEntity<Quote[]> responseEntity = restTemplate.getForEntity(url, Quote[].class);

//    Quote[] quotes = restTemplate.getForObject("https://api.myjson.com/bins/1hhki3", Quote.class);
//    log.info(quote.toString());
	Quote[] test=new Quote[20];
	test=responseEntity.getBody();
    return test[2].getName();
	}
	
	public String getJSONuri(String url)
	{
//		URI uri = null;
//		uri = new URI(url);
//		URL newurl=null;
//		newurl=uri.toURL();
//		return newurl.toString();
		return url.toString();
	}
	public Dish[] getJSONlistweb(String url)
	{
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Quote[]> responseEntity = restTemplate.getForEntity(url, Quote[].class);
	
	//    Quote[] quotes = restTemplate.getForObject("https://api.myjson.com/bins/1hhki3", Quote.class);
	//    log.info(quote.toString());
		Quote[] test=responseEntity.getBody();
//		ArrayList<Dish> dishes=new ArrayList<Dish>();
		DishAdapter ds=new DishAdapter(test);
//		Dish[] dishes=new Dish[100];
//		for (int i=0; i<test.length; i++)
//		{
//			dishes.add(new Dish((test[i].getName())));
////			dishes[i]=new Dish((test[i].getName()));
//		}	
		Dish[] dishes2 = ds.dishes.toArray(new Dish[ds.dishes.size()]);
//		Dish[] final = new Dish[dishes.size()];
//		dishes.toArray(final);
	    return dishes2;
	}
	
	
}

