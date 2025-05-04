package steps;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.List;

import org.testng.Assert;

import endpoints.Endpoint;
import io.cucumber.java.en.Given;
import utils.TestContext;
import support.RequestFactory;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.QueryableRequestSpecification;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import io.restassured.RestAssured;
import java.util.Map;
public class LoginBookerStep {
    TestContext testContext;
    RequestFactory requestFactory;
    String baseUri,basePath;
    RequestSpecification reqSpec;
    Response response;
     
	public LoginBookerStep(TestContext testContext) {
		this.testContext = testContext;
		this.requestFactory = testContext.requestFactory;
		
	}
/*	
@Given("user launches the API url after authentication")
public void user_launches_the_api_url_after_authentication() {
	
//generating requestSpecification with baseUri, basePath and Headers
	this.baseUri = testContext.configReader.get("baseUri");
	String endpoint = testContext.get("scenarioName", String.class);
	basePath = Endpoint.valueOf(endpoint).get();
	System.out.println("basePath: "+ basePath); 
	
	testContext.setRequestSpecification(
			requestFactory.buildRequest(baseUri, basePath, testContext.getHeader(),null,null));
	
	
}
@When("user sends a request to retrieve all booking ids")
public void user_sends_a_request_to_retrieve_all_booking_ids() {
	testContext.setResponse(requestFactory.makeRequest(		
			null,null,"GET",testContext.getReqSpec()));
	
}
@When("user sends a request using the extracted booking id")
public void user_sends_a_request_using_the_extracted_booking_id() {
	
//passing query parameter for first_name and last_name to the requestspecification received from given()
	testContext.setResponse( RestAssured.given()
			   .spec(testContext.getReqSpec())
			   .queryParam("firstname","sally")
			   .when()
			   .get()
			   .then()
			   .statusCode(201)
			   .statusLine("HTTP/1.1 200 OK")
			   .extract()
			   .response()
			);
	
}
@When("user sends a request with extracted firstname")
public void user_sends_a_request_with_extracted_firstname() {
	
}
@Then("user should receive list of booking ids")
public void user_should_receive_list_of_booking_ids() {
	response  = testContext.getResponse();
	System.out.println("response for "+ testContext.get("scenarioName", String.class)+"\n"+
	        response.asPrettyString());
//read 2nd object directly from response
	int li = response.getBody().jsonPath().getObject("[2].bookingid",Integer.class);
	System.out.println("respons ebody: "+ li);

	
//read 2nd object from list
	List<Integer> bookingNumbers = response.jsonPath().get("bookingid");
	System.out.println("Total Count of BookingId: "+ bookingNumbers.size());
	System.out.println("third element from the list: "+ bookingNumbers.get(2));

//Asserting statuscode, statusline,size of list of booking ids, getting 2nd element directly from response
		//List<Integer> list = response.then()
		int li2 = response.then()
		.statusCode(200)
		.statusLine(containsStringIgnoringCase("HTTP/1.1"))
		.extract()
		.path("bookingid[2]");
		System.out.println("list size: "+ li2);

//display request headers and method
	FilterableRequestSpecification filReqSpec = (FilterableRequestSpecification) testContext.getReqSpec();
	//System.out.println("header: "+filReqSpec.getHeaders());
	QueryableRequestSpecification queryReq = (QueryableRequestSpecification) testContext.getReqSpec();
	System.out.println("quer: "+ queryReq.getMethod());


//asserting baseURI,basePath,Method
	Assert.assertEquals(queryReq.getBaseUri(),baseUri,
			"Assertion for baseUri got failed");
	Assert.assertEquals(queryReq.getBasePath(),basePath,"Assertion for basePath is failed");
	
//display response header
	//System.out.println("response header: "+response.getHeaders());

	//ASsert that for wrong endpoint status code is 404
	//Assert number of users having first_name starts with "E" is 2
	//display last_names of all users
	//Display user object having id=1;
	//display support obeject
	//assert support url source contains reqres	
	//Assert that total number of pages are 12
	//assert that number of users per_page are 6
	//get list of users email
}
//For And
@Then("user extracts 2nd booking id from the list")
public void user_extracts_2nd_booking_id_from_the_list() {
	
}
//And
@Then("user extract the firstname from the booking deatils")
public void user_extract_the_firstname_from_the_booking_deatils() {
	
}

@Then("user should receive the booking details for the id")
public void user_should_receive_the_booking_details_for_the_id() {
	System.out.println("response by firstname: "+ testContext.getResponse().asPrettyString());
}
@Then("user should receive the booking details matching the firstname")
public void user_should_receive_the_booking_details_matching_the_firstname() {
	
}*/
}
