package TestFramework;

import static io.restassured.RestAssured.given; // RestAssured API
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import googleAPIs.PayLoad;
import googleAPIs.GoogleMapsResources;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GooglePlaceAPI_PostAndDelete {
	
	private static Logger log = LogManager.getLogger(GooglePlaceAPI_PostAndDelete.class.getName());
	Properties prop = new Properties();
	
	@BeforeTest
	public void getData() throws IOException {
		// C:\work\RESTAssuredTest\env.properties
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//env.properties");
		prop.load(fis);
	}

	@Test
	public void getPlaceAPI() {
		// TODO Auto-generated method stub

		// BaseURL or Host
		RestAssured.baseURI = prop.getProperty("HOST");
		
		given()
		.param("location", "-33.8670522,151.1957362")
		.param("radius", "500")				
		.param("key", prop.getProperty("KEY"))
		
		.when()
			.get(GoogleMapsResources.placeJsonGetData())
			
		.then()
			.assertThat().statusCode(200)
			.and().contentType(ContentType.JSON)
			.and().body("results[0].name", equalTo("Sydney"))
			.and().body("results[0].place_id", equalTo("ChIJP3Sa8ziYEmsRUKgyFmh9AQM"))
			.and().header("Server", "scaffolding on HTTPServer2");
	}	
	
	@Test
	public void AddandDeletePlace() {
		
		// Task 1- Grab the response
		log.info("Host information:- "+ prop.getProperty("HOST"));
		RestAssured.baseURI = prop.getProperty("HOST");
		
		//Response comes as raw data 
		Response res = 
				given().
					queryParam("key", prop.getProperty("KEY")).body(PayLoad.getPostData())
				
				.when()
					.post(GoogleMapsResources.placePostData())
				
				.then()
					.assertThat().statusCode(200).and()
					.contentType(ContentType.JSON).and().body("status", equalTo("OK"))
				
				.extract().
					response();
		
		// Task 2- Grab the Place ID from response
		String responseString = res.asString();		//Transfer Raw data to string  
		log.info(responseString);
		JsonPath js = new JsonPath(responseString);	//and then Convert to Json or XML
		String placeid = js.get("place_id");
		log.info(placeid);

		// Task 3 place this place id in the Delete request
		given()
			.queryParam("key", prop.getProperty("KEY"))
			.body("{" + "\"place_id\": \"" + placeid + "\"" + "}")
			
			.when()
				.post(GoogleMapsResources.placeDeleteData())
			
			.then()
				.assertThat().statusCode(200).and().contentType(ContentType.JSON).and().body("status", equalTo("OK"));

	}


}
