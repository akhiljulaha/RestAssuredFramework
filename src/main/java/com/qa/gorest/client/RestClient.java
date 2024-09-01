package com.qa.gorest.client;
import static io.restassured.RestAssured.given;

import java.util.Map;
import java.util.Properties;

import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.frameworkexception.APIFrameworkException;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {
//	private static final String BASE_URI = "https://gorest.co.in";
//	private static final String BEARER_TOKEN = "159b39ff866232ff73c9d1d717585c322d07a317fefa4b8bad8ab3f408b67095";
	private static RequestSpecBuilder specBuilder;
	private Properties prop;       //properties referance
	private String baseURI;
	private boolean isAuthorizationHeaderAdded = false;
//	static {       //	why not using the static? :           
//		specBuilder = new RequestSpecBuilder();
//	}	
	public RestClient(Properties prop, String baseURI) {  //properties class will help me to get the data from the confiq.properties 
		specBuilder = new RequestSpecBuilder();
		this.prop = prop;
		this.baseURI = baseURI;
	}
	public void addAuthorizationHeader() {   // this header we need to use with others req. alos, call directly if you need
		if(!isAuthorizationHeaderAdded) {
			specBuilder.addHeader("Authorization", "Bearer "+ prop.getProperty("tokenId"));
			isAuthorizationHeaderAdded= true;
		}
	}
	
	private void setRequestContentType(String contentType) {
		switch (contentType.toLowerCase()) {
		case "json":
			specBuilder.setContentType(ContentType.JSON);		
			break;
		case "xml":
			specBuilder.setContentType(ContentType.XML);		
			break;
		case "text":
			specBuilder.setContentType(ContentType.TEXT);		
			break;
		case "multipart":
			specBuilder.setContentType(ContentType.MULTIPART);		
			break;
		default:
			System.out.println("Plz pass the right content type....");
			throw new APIFrameworkException("INVALIDCONTENTTYPE");
		}
	}
	
//  useful only for the basic call 
	private RequestSpecification createRequestSpec(boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		if(includeAuth) {
			addAuthorizationHeader(); 
		}
		return specBuilder.build(); 
	}
	// useful if want to pass only extra headers(passing headers with the help of hashmap)
	private RequestSpecification createRequestSpec(Map<String, String> headersMap, boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		if(includeAuth) {
			addAuthorizationHeader(); 
		}
		if(headersMap !=null) {
			specBuilder.addHeaders(headersMap);
		}
		return specBuilder.build(); 
	}
	//useful if want to pass the headers and query paramaeter
	private RequestSpecification createRequestSpec(Map<String, String> headersMap, Map<String, Object> queryParams, boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		if(includeAuth) {
			addAuthorizationHeader(); 
		}
		if(headersMap !=null) {
			specBuilder.addHeaders(headersMap);
		}
		if(queryParams !=null) {
			specBuilder.addQueryParams(queryParams);
		}
		return specBuilder.build(); 
	}
	
	// want to pass content type (POST: no need query paramater, only need for the GET)
	private RequestSpecification createRequestSpec(Object requestBody, String contentType, boolean includeAuth) { //L>POJO
		specBuilder.setBaseUri(baseURI);
		if(includeAuth) {
			addAuthorizationHeader(); 
		}
		setRequestContentType(contentType);
		if(requestBody!=null) {
			specBuilder.setBody(requestBody);         // use 2nd suggestion > Object
		}
		return specBuilder.build(); 
	}
	// want to pass content type with extra headers
	private RequestSpecification createRequestSpec(Object requestBody, String contentType, Map<String, String> headersMap, boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		if(includeAuth) {
			addAuthorizationHeader(); 
		}
		setRequestContentType(contentType);
		if(headersMap !=null) {
			specBuilder.addHeaders(headersMap);
		}
		if(requestBody!=null) {
			specBuilder.setBody(requestBody);         // use 2nd suggestion > Object
		}
		return specBuilder.build(); 
	}
	// all above methods are only for the request specification
	// below we are creating the HTTP methods utils
	// above > private  below Public > pricate methods can be access by the public methods >>> Encapsulations
	//GET
	public Response get(String serviceUrl, boolean includeAuth,boolean log) {
		
		if(log) {
			return RestAssured.given(createRequestSpec(includeAuth)).log().all()
   	      .when()
   	          .get(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(includeAuth)).when().get(serviceUrl);
	}
	// passing headers
   public Response get(String serviceUrl, Map<String, String> headersMap , boolean includeAuth, boolean log) {
		if(log) {
			return RestAssured.given(createRequestSpec(headersMap, includeAuth)).log().all()
   	      .when()
   	          .get(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(headersMap, includeAuth)).when().get(serviceUrl);
	}
	
// passing headers and query parameter
   public Response get(String serviceUrl, Map<String, Object> queryParams ,Map<String, String> headersMap , boolean includeAuth, boolean log) {
		if(log) {
			return RestAssured.given(createRequestSpec(headersMap, queryParams,includeAuth)).log().all()
   	      .when()
   	          .get(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(headersMap, queryParams,includeAuth)).when().get(serviceUrl);
	}
	//POST - With Extra headers
   
   public Response post(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap , boolean includeAuth, boolean log) {
	   if(log) {
		  return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).log().all()
		        .when()
		            .post(serviceUrl);
	   }
	   return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
		        .when()
		            .post(serviceUrl);
   }
   
	//POST - Without Extra headers
   
   public Response post(String serviceUrl, String contentType, Object requestBody , boolean includeAuth, boolean log) {
	   if(log) {
		  return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).log().all()
		        .when()
		            .post(serviceUrl);
	   }
	   return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth))
		        .when()
		            .post(serviceUrl);
   }
   
//PUT - With Extra headers
   
   public Response put(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap , boolean includeAuth, boolean log) {
	   if(log) {
		  return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).log().all()
		        .when()
		            .put(serviceUrl);
	   }
	   return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
		        .when()
		            .put(serviceUrl);
   }
   
	//PUT - Without Extra headers
   
   public Response put(String serviceUrl, String contentType, Object requestBody , boolean includeAuth, boolean log) {
	   if(log) {
		  return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).log().all()
		        .when()
		            .put(serviceUrl);
	   }
	   return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth))
		        .when()
		            .put(serviceUrl);
   }
   
//patch - With Extra headers
   
   public Response patch(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap , boolean includeAuth, boolean log) {
	   if(log) {
		  return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).log().all()
		        .when()
		            .patch(serviceUrl);
	   }
	   return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
		        .when()
		            .patch(serviceUrl);
   }
   
	//patch - Without Extra headers
   
   public Response patch(String serviceUrl, String contentType, Object requestBody , boolean includeAuth, boolean log) {
	   if(log) {
		  return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).log().all()
		        .when()
		            .patch(serviceUrl);
	   }
	   return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth))
		        .when()
		            .patch(serviceUrl);
   }
   

   // Delete
   public Response delete(String serviceUrl , boolean includeAuth, boolean log) {  // not passing the ID because that would be a hardcoded work only for the 1 API
	   if(log) {
			  return RestAssured.given(createRequestSpec(includeAuth)).log().all()
			        .when()
			            .delete(serviceUrl);
		   }
		   return RestAssured.given(createRequestSpec(includeAuth))
			        .when()
		              .delete(serviceUrl);
   }
	
   
   public String getAccessToken(String serviceURL, String grantType, String clientId, String clientSecret) {  
		// 1. POST - get the access token
		RestAssured.baseURI = "https://test.api.amadeus.com";
		 String accessToken = given().log().all()
			.contentType(ContentType.URLENC)     	 
		    .formParam("grant_type", grantType)
		    .formParam("client_id", clientId)
		    .formParam("client_secret", clientSecret)
		.when()
		    .post(serviceURL)
		.then().log().all()
		    .assertThat()
		        .statusCode(APIHttpStatus.OK_200.getCode())
		        .extract().path("access_token");
		 
		    System.out.println("Access Token: "+ accessToken);
		    return accessToken;
	}
	
	

}






/*
 * if you have 5 test then it will generate 5 different token and this approach is very good, no need to think 
 * if the token wil expire after 5 mins or suppose running 30 test. by reaching it 3o your token will expired
 */



/*
 * the static block will be executed before creating the object of this class,
 * before creating the object or before main method if you want to run any code
 * then write inside the static part
 */
// Ctrl + O
