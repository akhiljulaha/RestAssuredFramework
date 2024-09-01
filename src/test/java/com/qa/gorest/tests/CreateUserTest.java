package com.qa.gorest.tests;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIConstants;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.User;
import com.qa.gorest.utils.ExcelUtil;
import com.qa.gorest.utils.StringUtils;

public class CreateUserTest extends BaseTest{

	@BeforeMethod 
	public void getUserSetup() {
		restClient = new RestClient(prop, baseURI);

	}
	
	
	@DataProvider
	public Object[][] getUserTestData() {
		return new Object[][] {
			{"Subodh", "male", "active"},
			{"Seema", "female", "inactive"},
			{"Madhuri", "female", "active"}
		};
	}
	
	@DataProvider
	public Object[][] getUserTestSheetData() {
		return ExcelUtil.getTestData(APIConstants.GOREST_USER_SHEET_NAME);
	}
	//Post
	@Test(dataProvider = "getUserTestSheetData")
	public void createUserTest(String name, String gender, String status) {
		User user = new User(name, StringUtils.getRandomEmailId(), gender, status);
		Integer userId = restClient.post(GOREST_ENDPOINT, "json", user,true, true)
		     .then().log().all()
		         .assertThat().statusCode(APIHttpStatus.CREATED_201.getCode())
		              .extract().path("id");
		System.out.println("User id: "+ userId);
/*
 * create 2 seperate object, otherwise it will add 2 auth token and result would be 400 bad request		
 */
		//GET
		RestClient ClientGet = new RestClient(prop, baseURI);
		ClientGet.get(GOREST_ENDPOINT+"/"+userId, true, true)
		    .then().log().all()
		       .assertThat().statusCode(APIHttpStatus.OK_200.getCode())
		           .assertThat().body("id", equalTo(userId));
		
	}
}
// ** Specially in this test, create another object for the the GET call 