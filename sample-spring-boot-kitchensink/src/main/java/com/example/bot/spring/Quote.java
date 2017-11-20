package com.example.bot.spring;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
/**
 * Quote.java deserialises the json object
* @author Project Group 25
* @version 1.0
* @since 2017-11-20
*/
public class Quote {
	private String name;
	private Float price;
	private List<String> ingredients;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public Float getPrice() {
		return price;
	}
	
	public void setPrice(float price) {
		this.price=price;
	}
	
	public List<String> getIngredients() {
		return ingredients;
	}
	
	public void setIngredients(List<String> ingredients) {
		this.ingredients=ingredients;
	}
	

//    private String type;
//    private Value value;
//
//    public Quote() {
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public Value getValue() {
//        return value;
//    }
//
//    public void setValue(Value value) {
//        this.value = value;
//    }
//
//    @Override
//    public String toString() {
//        return "Quote{" +
//                "type='" + type + '\'' +
//                ", value=" + value +
//                '}';
//    }
}