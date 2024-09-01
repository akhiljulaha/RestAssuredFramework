package com.qa.gorest.base;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.qa.gorest.client.RestClient;
import com.qa.gorest.configuration.ConfigurationManager;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
public class BaseTest {
	
	// maintaining service URL
	public static final String GOREST_ENDPOINT = "/public/v2/users";
	public static final String BOOKING_ENDPOINT = "/booking";
	public static final String CIRCUIT_ENDPOINT = "/api/f1";
	public static final String AMADEUS_TOKEN_ENDPOINT = "/v1/security/oauth2/token";
	public static final String AMADEUS_FLIGHTBOOKING_ENDPOINT = "/v1/shopping/flight-destinations";

	protected ConfigurationManager config;  //protected > Only the child class will access these properties
	protected Properties prop;
	protected RestClient restClient;
	protected String baseURI;
	
// parmeter anotation will fetch the baseURL from the testng.xml and it will suplly to the setup()	
	@Parameters({"baseURI"})  
	@BeforeTest
	public void setUp(String baseURI) {
		
		RestAssured.filters(new AllureRestAssured());
		
		config = new ConfigurationManager();
		prop = config.initProp();     // R > It will return Poperties file, so maintaing in the prop variable
//		String baseURI = prop.getProperty("baseURI");   // no need to read with the help of getProperty()
		this.baseURI = baseURI;
//		restClient = new RestClient(prop, baseURI); removing this because we added a @beforemethod in the test class
	}
	
//	// Use below one when you want read data from the properties file 
//	@BeforeTest
//	public void setUp() {
//		
//		RestAssured.filters(new AllureRestAssured());
//		
//		config = new ConfigurationManager();
//		prop = config.initProp();   
//		String baseURI = prop.getProperty("baseURI");
//		this.baseURI = baseURI;
//	}
	
	

}
