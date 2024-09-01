package com.qa.gorest.utils;

import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.qa.gorest.frameworkexception.APIFrameworkException;



public class JsonPathValidator {
	
	private String getJsonResponseAsString(Response response) {
		return response.getBody().asString();
	}
	

	public <T> T read(Response response, String jsonPath) {  // this will give any type of return string, int etc
		String jsonResponse = getJsonResponseAsString(response);
		 try {
		 return JsonPath.read(jsonResponse, jsonPath);
		 }
		 catch (PathNotFoundException e) {       // this expection is the predefined in the jayway api
			 e.printStackTrace();
			 throw new APIFrameworkException(jsonPath + " is not found... " );
		}
	}
	
	public <T> List<T> readlist(Response response, String jsonPath) {  // this will give any type of the list (T > Type-> means any type)
		String jsonResponse = getJsonResponseAsString(response);
		 try {
		 return JsonPath.read(jsonResponse, jsonPath);
		 }
		 catch (PathNotFoundException e) {       // this expection is the predefined in the jayway api
			 e.printStackTrace();
			 throw new APIFrameworkException(jsonPath + " is not found... " );
		}
	}
	
	public <T> List<Map<String, T>> readlistOfMaps(Response response, String jsonPath) {   
		String jsonResponse = getJsonResponseAsString(response);
		 try {
		 return JsonPath.read(jsonResponse, jsonPath);
		 }
		 catch (PathNotFoundException e) {     
			 e.printStackTrace();
			 throw new APIFrameworkException(jsonPath + " is not found... " );
		}
	}
	
	
	
	
}
