package steps;
import io.cucumber.java.Before;

import java.util.Collection;
import java.util.stream.Collectors;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import support.RequestFactory;
import utils.TestContext;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;
import support.TokenManager;
import utils.HeaderReader;
import java.util.Collections;
public class Hooks {
	TestContext testContext;
	RequestFactory requestFactory;
	TokenManager tokenManager;
	public String token;
	public Hooks(TestContext testContext) {
		this.testContext = testContext;
		this.requestFactory = testContext.requestFactory;
		this.tokenManager = this.requestFactory.tokenManager;
		
	}
@Before
public void beforeScenario(Scenario scenario) {
	
//setting testContext data according the tags specified feature file	
	Collection<String> set = scenario.getSourceTagNames();
	         Map<String,String>map =  set.stream()
	          .filter(t->t.contains(":"))
	          .map(t->t.replace("@","").split(":"))
	          .collect(Collectors.toMap(
	        		  a->a[0].trim(),
	        		  a->a[1].trim())
	        		  );
	         this.testContext.set(new HashMap<>(map));
	         
//Authentication process by setting tag into the testContext header	         
	         if(token==null || tokenManager.isExpired())
	        	 token = tokenManager.getToken();
	         testContext.addHeader("token",token); //OR
	         testContext.addHeader("Cookie", token); //OR
	         testContext.addHeader("Authorization", token);
	         
	       
//Reading headers from header.json file and setting/adding it into testContext header	  
	         /*  testContext.setHeader(new HeaderReader("header.json")
			   .getHeadersForScenario(testContext.get("scenarioName", String.class))); */
	         //OR
	            Optional.ofNullable(new HeaderReader("header.json")
	     			   .getHeadersForScenario(testContext.get("scenarioName", String.class)))
	     			   .filter(m->!m.isEmpty())
	     			   .ifPresentOrElse(testContext::setHeader, 
	     				 ()->  System.out.print("Header not found in header File for scenario: "
	     				 		+ testContext.get("scenarioName",String.class)));
	                  // .ifPresent(testContext::setHeader);
}

@After 
public void afterScenario(Scenario scenario) {
	
//clearing out token and testContext data	
	System.out.println("clearing testContext");
	tokenManager.clearToken();
	testContext.clear();
}
}
