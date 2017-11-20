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

/**
* JsonTest is used to convert a url to a list of dish objects which will be used to give recommendations to the user
*
* @author Project Group 25
* @version 1.0
* @since 2017-11-20
*/
public class JsonTest {
	
	/**
	*This method extracts the menu in json format from a url and returns an array of dish objects
	* @param url This is the paramter to getJSONlistweb
	* @return Dish[] This returns the array of dishes.
	*/
	public Dish[] getJSONlistweb(String url)
	{
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Quote[]> responseEntity = restTemplate.getForEntity(url, Quote[].class);
	

		Quote[] test=responseEntity.getBody();
		DishAdapter ds=new DishAdapter(test);

		Dish[] dishes2 = ds.dishes.toArray(new Dish[ds.dishes.size()]);

	    return dishes2;
	}
	
	
}

