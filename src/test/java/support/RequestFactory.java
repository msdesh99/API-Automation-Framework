package support;
import utils.ConfigReader;
import support.TokenManager;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import java.util.Map;
import java.util.Optional;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import static org.hamcrest.Matchers.allOf;
import  static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.lessThan;
public class RequestFactory {
   public ConfigReader configReader;
   public TokenManager tokenManager;
   public RequestFactory requestFactory;
   
	public RequestFactory(ConfigReader configReader, TokenManager tokenManager) {		
		this.configReader = configReader;
		this.tokenManager = tokenManager;
	}
	public RequestSpecification buildRequest(String baseUri,String basePath,Map<String,String>header,
			    Map<String,Object> queryParamMap, Object reqBody) {
		    RequestSpecBuilder builder = new RequestSpecBuilder();
		    builder.setBaseUri(baseUri)
		    .setBasePath(basePath) ;
		    
		    Optional.ofNullable(header)
		    .filter(h->!h.isEmpty())
		    .ifPresent(builder::addHeaders);
		    Optional.ofNullable(queryParamMap)
		    .filter(q->!q.isEmpty())
		    .ifPresent(builder::addQueryParams);
		    Optional.ofNullable(reqBody)
		    .ifPresent(builder::setBody);
		   
		    return builder.build();
	}
	
	public ValidatableResponse makeRequest(RequestSpecification requestSpec,String method,
			Map<String,Object> queryParamMap,Object reqBody) {
		      Optional.ofNullable(queryParamMap)
		      .filter(q->!q.isEmpty())
		      .ifPresent(requestSpec::queryParams);
		      
		      Optional.ofNullable(reqBody)
		      .ifPresent(requestSpec::body);
		      
		      
		      ValidatableResponse validResponse  = RestAssured.given()
		        		.spec(requestSpec)
		        		.when()
		        		.request(Method.valueOf(method))
		        		.then();
		     //.statusCode(not(401));  
		     //.statusCode(allOf(greaterThanOrEqualTo(200), lessThan(400)));
		      
		      return checkTokenValidity(requestSpec,validResponse,method);		    	  
	}
	public ValidatableResponse checkTokenValidity(RequestSpecification requestSpec,
			ValidatableResponse validResponse, String method) {
		if(validResponse.extract().statusCode()==401){
			tokenManager.forceRefresh();
			validResponse = RestAssured.given()
					.spec(requestSpec)
					.when()
					.request(Method.valueOf(method)).then();
			}
			return validResponse;		
	}
	public Response makeRequest(Map<String,Object>queryMap,Map<String,Object>pathParam,Object reqBody,String method,RequestSpecification reqSpec) {
		Optional.ofNullable(queryMap)
		.filter(q->!q.isEmpty())
		.ifPresent(reqSpec::queryParams);
		Optional.ofNullable(pathParam)
		.filter(p->!p.isEmpty())
		.ifPresent(reqSpec::pathParams);
		Response response = RestAssured.given()
				.spec(reqSpec)
				.when()
				.request(Method.valueOf(method))
				.then()
				.extract()
				.response();
		        return checkTokenValidity(reqSpec,response,method);
	}
	public Response checkTokenValidity(RequestSpecification reqSpec,Response response,String method) {
		  
		        if(response.statusCode()==401) {
					   System.out.println("Token Expired. Refreshing...");
					   tokenManager.forceRefresh();
					   tokenManager.getToken();		        	
		        
		        response = RestAssured.given()
						.spec(reqSpec)
						.when()
						.request(Method.valueOf(method))
						.then()
						.extract()
						.response();
		        } 
		        return response;		
	}
	
}
