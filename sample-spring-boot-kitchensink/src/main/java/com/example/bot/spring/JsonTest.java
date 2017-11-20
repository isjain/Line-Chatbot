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

