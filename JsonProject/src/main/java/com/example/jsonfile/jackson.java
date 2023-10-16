package com.example.jsonfile;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class Laptop {
	private String brand;
	private String model;

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
}

public class jackson {
public static void main(String[] args) {
	//String json="{\"brand\":\"abc\",\"model\":\"xyz\"}";
	String json="[{\"brand\":\"abc\",\"model\":\"xyz\"},]";
    ObjectMapper  mapper=new ObjectMapper();
    try {
    	Laptop[] l=mapper.readValue(json, Laptop[].class);
    	for(Laptop lt:l)
    	System.out.println(lt);
		
	} catch (JsonMappingException e) {
		e.printStackTrace();
	}catch (JsonProcessingException e) {
		e.printStackTrace();
	}

  }
}