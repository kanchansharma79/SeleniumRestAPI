package TestFramework;
import org.testng.annotations.Test;

import googleAPIs.PayLoad;

import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.equalTo;

public class GooglePlaceAPI_Post {
	
	@Test
	public void createPlaceAPI()
	{
		RestAssured.baseURI="https://maps.googleapis.com";
		given().
		
		queryParam("key","AIzaSyAOf1oBeuGsGr8T9kejERwersZyuFP_cLJSLmYc").
		body(PayLoad.createPlaceData()).
		
		when().
		post("/maps/api/place/add/json").
		
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
		body("status",equalTo("OK"));
	}
}
