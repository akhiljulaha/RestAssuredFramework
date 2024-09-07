package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;

public class GetUserTest extends BaseTest{

	@BeforeMethod // this will start working before each and every test method, we want to create the object of restclient before each and every method
	public void getUserSetup() {
		restClient = new RestClient(prop, baseURI);

	}
	//code smell : sonarQube
	@Test(enabled = true, priority = 3)
	public void getAllUser() {
		restClient.get(GOREST_ENDPOINT, true, true)        // service URL is inherit fron the parent class
		              .then().log().all()                         //here we have to write .log .all manually
		                  .assertThat().statusCode(APIHttpStatus.OK_200.getCode());	
		System.out.println("testing");
	}
	
	@Test(priority = 2)
	public void getUserTest() {
		restClient = new RestClient(prop, baseURI);         // temp solution, baseURI coming from baseTest
		restClient.get(GOREST_ENDPOINT+"/"+7386821, true, true)
		              .then().log().all()                         //here we have to write .log .all manually
		                  .assertThat().statusCode(APIHttpStatus.OK_200.getCode())
		                      .and().body("id", equalTo(7386821));	                  
	}

	
	@Test(priority = 1)
	public void getUserWithQueryParamsTest() {
		RestClient restClient1 = new RestClient(prop, baseURI); 
		Map<String, Object> queryParms = new HashMap<String, Object>();
		queryParms.put("name", "naveen");
		queryParms.put("status", "active");
		restClient1.get(GOREST_ENDPOINT, queryParms, null, true, true)    // if don't have heders the u can pass the Null also because we added 1 check 
		              .then().log().all()                         //here we have to write .log .all manually
		                  .assertThat().statusCode(APIHttpStatus.OK_200.getCode());	     
	}
}
/*
 * Q-> why we using the @before method 
 * Using a new RestClient object for each test ensures that each test is independent, reliable, and unaffected
 *  by any shared state, leading to more stable and maintainable tests.
 *          OR
 * Example > If you use the same RestClient object for both the 2nd and 3rd tests, the query parameters added
 *           in the 3rd test could unintentionally carry over to the 2nd test and 3rd test. This is a common issue 
 *           when sharing objects that store state across multiple tests.
 *  ** By using a fresh RestClient instance for each test, you avoid the risk of shared state issues like unintentional
 *   query parameters, headers, or configurations affecting the results of other tests.
 *   
 *   
 *   
 *   Creting 3 object istead of the 1 (DRY RUN)
 * 1> creting obj 1 is refered by the RestClient
 * 2> creting the Obj 2 is refered by the same RestClient but before refering it will break the connection with the Obj 1
 * 3 > creting the Obj 3 is refered by the same RestClient but again it will break the connection with the Obj2 before refering 
 */
