//package com.example.jsonfile;
//
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.Set;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import com.fasterxml.jackson.databind.JsonNode;
//public class Commonvalueprint {
//
//
//
//
//	        ObjectMapper objectMapper = new ObjectMapper();
//
//	        try {
//	            // Read and parse the first JSON file
//	            JsonNode jsonNode1 = objectMapper.readTree("D:\\JBPMWORKSPACE\\JBPM DIAGRAM\\JsonProject\\src\\main\\resources\\data.json");
//	            
//	            // Read and parse the second JSON file
//	            JsonNode jsonNode2 = objectMapper.readTree("D:\\JBPMWORKSPACE\\JBPM DIAGRAM\\JsonProject\\src\\main\\resources\\data2.json");
//
//	            // Create a set to store common values
//	            Set<String> commonValues = new HashSet<>();
//
//	            // Iterate through the JSON nodes and compare the values
//	            for (JsonNode valueNode1 : jsonNode1) {
//	                for (JsonNode valueNode2 : jsonNode2) {
//	                    if (valueNode1.asText().equals(valueNode2.asText())) {
//	                        commonValues.add(valueNode1.asText());
//	                    }
//	                }
//	            }
//
//	            // Print the common values
//	            for (String commonValue : commonValues) {
//	                System.out.println("Common Value: " + commonValue);
//	            }
//
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//	    }
//	}
//
