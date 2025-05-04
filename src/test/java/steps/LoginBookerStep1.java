package steps;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.greaterThan;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.testng.Assert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import endpoints.Endpoint;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import pojo.Booking;
import support.RequestFactory;
import utils.TestContext;
import java.text.ParseException;
import java.time.LocalDateTime;
public class LoginBookerStep1 {
	TestContext testContext;
	RequestFactory requestFactory;
	String baseUri, basePath;
	Response response;
	List<Integer> bookingidList;
	private Booking booking;
     public LoginBookerStep1(TestContext testContext) {
    	 this.testContext = testContext;
    	 this.requestFactory = this.testContext.requestFactory;
     }
     @Given("user launches the API url after authentication")
     public void user_launches_the_api_url_after_authentication() {
          baseUri = testContext.configReader.get("baseUri");
          basePath = Endpoint.valueOf(testContext.get("scenarioName",String.class)).get();
          testContext.setRequestSpecification(
        		    requestFactory.buildRequest(baseUri, basePath, testContext.getHeader(),null, null)
        		  );
     }


     @When("user sends a request to retrieve all booking ids")
     public void user_sends_a_request_to_retrieve_all_booking_ids() {
    	testContext.setResponse(
    			 requestFactory.makeRequest(null,null, null,"GET",testContext.getReqSpec()));
        response = testContext.getResponse();
     }

  
     @Then("user should receive list of booking ids")
     public void user_should_receive_list_of_booking_ids() {
    	
//assert statuscode, statusLine, list is not empty
        bookingidList = response.then()
    	 .statusCode(200)
    	 .statusLine(containsStringIgnoringCase("HTTP/1.1"))
    	 .extract()
    	 .path("bookingid");      
    //setting list into contextData    
   	    testContext.set("bookingidList",bookingidList);
    	Assert.assertFalse(bookingidList.isEmpty(),"Bookingids list should not be empty");       		 				
     }
     @Then("user extracts <{int}> booking id from the list")
     public void user_extracts_booking_id_from_the_list(Integer bookingidEle) {
//assert list size > 0
    	 Assert.assertTrue(bookingidList.size()>=bookingidEle-1,
    			 "List of booking id doesn't have "+ bookingidEle +" element");
    	 testContext.set("bookingid",testContext.get("bookingidList", List.class).get(bookingidEle));
    	 System.out.println("booking id: "+ testContext.get("bookingid", Integer.class));
     }

 
     @When("user sends a request using the extracted booking id")
     public void user_sends_a_request_using_the_extracted_booking_id() {
    	 basePath = Endpoint.GET_BOOKINGBYID.get();
    	 testContext.getReqSpec().basePath(basePath);
          testContext.setResponse(requestFactory.makeRequest(null,
        		  Map.of("id",testContext.get("bookingid", Integer.class))
        		  ,null,"GET", testContext.getReqSpec()));
     }

 
     @Then("user should receive the booking details for the id")
     public void user_should_receive_the_booking_details_for_the_id() {
 //extract response with {} into class using mapper.readVlaue and .then()extract()
 //convert pojo class into Map  3 ways using Map.class, TypeRefernce, converting into jsonstring
 //convert pojo into json string
         response = testContext.getResponse();
         System.out.println("response: "+ response.asPrettyString());

         ObjectMapper mapper = new ObjectMapper();
         booking = new Booking();
         
  //1st way extract reponse with {} into Class Object
         try {
		         booking = mapper.readValue(response.getBody().asInputStream(), Booking.class);
		         System.out.println("booking class1: "+ booking.toString());    
         }catch(IOException ioe) {System.out.println(ioe.getMessage());}
         		//System.out.println("firname: "+ booking.getBookingdates());
         		
 //2nd way to extract into class
        booking = response.then()
        		          .statusCode(200)
        		          .statusLine("HTTP/1.1 200 OK")
        		          .extract()
        		          .response()
        		          .getBody().as(Booking.class);
        //System.out.println("booking class: "+ booking.toString());    

       //OR 		          		
//convert class into map as Map.class 
         Map<String, Object> map = mapper.convertValue(booking, Map.class);
         //map.entrySet().stream().forEach(key->System.out.println(key.getKey()+ " "+ key.getValue()));
//convert class into Map
         Map<String,Object> map1 = mapper.convertValue(booking, new TypeReference<>() {});
 //convert class into json string
         try{ 
         		String json  = mapper.writeValueAsString(booking);
         		//System.out.println("json: "+ json);
         		Map<String,Object> map2 = mapper.readValue(json, new TypeReference<>() {});
         	}catch(Exception e) {
         		e.printStackTrace();
         	}
     }

 
     @Then("user extracts the firstname from the booking details")
     public void user_extracts_the_firstname_from_the_booking_details() {
          testContext.set("firstname", booking.getFirstname());
          testContext.set("lastname",booking.getLastname());
     }

 
     @When("user sends a request with extracted firstname")
     public void user_sends_a_request_with_extracted_firstname() {
    	 System.out.println("firstname: "+ testContext.get("firstname",String.class));
    	 testContext.setResponse(requestFactory.makeRequest(
    			 Map.of("firstname",booking.getFirstname(),
    			   "lastname",booking.getLastname())
    			 ,null,null,"GET",testContext.getReqSpec()));
    	 Response response = testContext.getResponse();
         System.out.println("response fro firstname: "+ response.asPrettyString());
     }

 
     @Then("user should receive the booking details matching the firstname")
     public void user_should_receive_the_booking_details_matching_the_firstname() {
//assert firstname
//assert response equals to booking object
//assert response is not empty
    	 Response response = testContext.getResponse();
    	 ValidatableResponse validResponse = response.then();
         String fname = 
        		        validResponse
        		 		.statusCode(200)
        		 		.statusLine(containsStringIgnoringCase("HTTP/1.1"))
        		 		//.body("firstname",equalToCompressingWhiteSpace("Josh"))
        		 	    .body("firstname", equalTo(testContext.get("firstname", String.class)))
        		 	    .body("size()",greaterThan(0))
        		 		.extract()       		 	
        		 		.path("firstname");
         Assert.assertEquals(validResponse.extract().path("firstname"),testContext.get("firstname",String.class),"Firstname assertion is failed");
         Assert.assertEquals(fname,testContext.get("firstname",String.class),"Firstname assertion is failed");

         System.out.println("fname: "+ fname);
         System.out.println("firstname: "+ response.jsonPath().getString("firstname"));

         Assert.assertTrue(Objects.deepEquals(response.as(Booking.class),booking),"Response Body assertion is failed");
   
     }
   
     @Then("user extracts checkin date from the booking deatils")
     public void user_extract_checkin_date_from_the_booking_deatils() {
    	 Response response = testContext.getResponse();
    	 String checkin = response.jsonPath().getObject("bookingdates.checkin",String.class);
    	 testContext.set("checkin", checkin);
    	 testContext.set("checkin", "2024-01-01");

    	 System.out.println("checin: "+ checkin);
    //DateFormat 
    	 DateFormat df = new SimpleDateFormat("YYYY-MM-DD");
         //Date date = df.parse(checkin);
    //DateTimeformat
         DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	 LocalDate checkinDate = LocalDate.parse(checkin,dtf);
    	 System.out.println("chekDate: "+ checkinDate);
         
     }
     @When("user sends a request with checkin date")
     public void user_sends_a_request_with_checkin_date() {
//Fecting the booking ids having checkin date is after set Date "24-01-01"
//Comparing the performance efficiency for traditional way and using java8 stream(),Parallel functionality 
//Using traditional it tooks 23 sec and for parallel 3 seconds per 100 records
//find time taken using both approcahes
    //fectching all booking ids
         Response response = RestAssured.given()
         .baseUri(testContext.configReader.get("baseUri"))
         .basePath("/booking")
         .header("Content-Type","application/json")
         .when()
         .get();
        List<Integer> ids = response.jsonPath().getList("bookingid");
   	    System.out.println("Total No of Booking Ids: "+ ids.size());
       
   	    //setting checkin date formator get it from textContext
   	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
   	    
     	//testContext.set("checkin", checkin);
    	 testContext.set("checkin", "2024-01-01");
         LocalDate setDate = LocalDate.parse(testContext.get("checkin", String.class),dtf); 


        List<Integer> matchedIds = new ArrayList<Integer>();
    //note the start datetime
        LocalDateTime ldt1 = LocalDateTime.now();
        System.out.println("Fetching booking Ids using traditional approach Start Time: "+ ldt1);
         //for(int id: ids) {
         int idTot = ids.size()>100? 100:ids.size();
         for(int i=0; i<idTot;i++) {      	 
            int id = ids.get(i);
        	 response = RestAssured.given()
        	         .baseUri(testContext.configReader.get("baseUri"))
        	         //.pathParam("id", id)
        	         .basePath("/booking/"+id)
        	         .header("Content-Type","application/json")
        	         .when()
        	         .get();
        	 //System.out.println("res in ids: "+ response.asPrettyString());
        	 LocalDate ldate = LocalDate.parse(response.jsonPath().getString("bookingdates.checkin"),dtf);
        	// if(!ldate.isBefore(LocalDate.parse(testContext.get("checkin", String.class),dtf))) {
              if(!ldate.isBefore(setDate)) {
        		 System.out.println("Id: "+id+ " CheckinDate: "+ldate);
        		  matchedIds.add(id);
        	 }		  
         }
         LocalDateTime ldt2 = LocalDateTime.now();
         System.out.println("Traditional approach End Time: "+ ldt2);

         // System.out.println("ldt1 : "+ldt1+" differ: "+ ldt1.until(ldt2, ChronoUnit.SECONDS));
          System.out.println("Time Taken for Traditional approach in Seconds: "
        		  	+ ChronoUnit.SECONDS.between(ldt1, ldt2));
         
         System.out.println("size: "+ ids.size());
         System.out.println("Number of Matching Ids: "+ matchedIds.size());
         matchedIds.stream().forEach(System.out::println);

         
         LocalDateTime ldt3 = LocalDateTime.now();
         System.out.println("Parallel approach Start Time: "+ ldt3);		 
         
         List<Integer> li2 = IntStream.range(0, idTot).parallel()
         .boxed()
         .map(i->{
        	    try {   
        	         Response response1 = RestAssured.given()
        	    		   .baseUri(testContext.configReader.get("baseUri"))
        	    		   .basePath("/booking/"+i)
        	    		   .header("Content-Type","application/json")
        	    		   .when().get();  
        	           LocalDate ldate = LocalDate.parse(response1.jsonPath().getString("bookingdates.checkin"),dtf);
        	        	   return !ldate.isBefore(setDate)?i:null; 
        	    }catch(Exception pe) {
        	    	//System.out.println(pe.getMessage());
        	    	return null;
        	    }
         })
         .filter(Objects::nonNull)
         .collect(Collectors.toList());
         LocalDateTime ldt4 = LocalDateTime.now();
         System.out.println("Prallel approach End Time: "+ ldt4);
         System.out.println("Time taken for parallel approach in Seconds: "+ ChronoUnit.SECONDS.between(ldt3,ldt4));
         System.out.println("Number of matching Ids: "+ li2.size());
         li2.stream().forEach(System.out::println);
     }

     @Then("user should receive the booking details matching to checkin")
     public void user_should_receive_the_booking_details_matching_to_checkin() {
         
     }

}
