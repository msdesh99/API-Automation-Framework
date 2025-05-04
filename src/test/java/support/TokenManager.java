package support;

import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;

import java.time.Instant;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.ConfigReader;

public class TokenManager {
	private String token;
	private Instant expiry;
	private ConfigReader configReader;
    public TokenManager(ConfigReader configReader) {
    	  this.configReader = configReader;
    }
	private void generateToken() {
		String baseUri = configReader.get("baseUri");
		String reqBody = "{\"username\":\"admin\",\n"
				+ "\"password\":\"password123\"}";
		System.out.println("body: "+ reqBody);
		System.out.println("baseuri: "+baseUri);
		try {
			token =
					RestAssured.given()
				.baseUri(baseUri)
				.basePath("/auth")
				.header("Content-Type","application/json")
				.body(reqBody)
				.when().post()
				.then()
				.statusCode(200)   //assertion
				.statusLine(containsStringIgnoringCase("HTTP/1.1")) //assertion
				.extract()    //extraction after assertion				
				.response()
				.path("token")				
				;
		
				expiry = Instant.now().plusSeconds(1);
		}catch(AssertionError ae) {
			System.out.println(ae.getMessage());
		}
				
	}
	public boolean isExpired() {
		return (token==null || Instant.now().isAfter(expiry));
	}
	public void forceRefresh() {
		this.token=null;
		this.expiry=null;
		generateToken();
	}
	public String getToken() {
		if(isExpired()) generateToken();
		return token;
	}
	public void clearToken() {
		System.out.println("clearing token");
		this.token=null;
		this.expiry=null;
	}
}
