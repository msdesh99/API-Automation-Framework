package utils;
//https://www.lonti.com/blog/using-query-parameters-and-headers-in-rest-api-design
import support.RequestFactory;
import support.TokenManager;
import java.util.Map;
import java.util.HashMap;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
public class TestContext {
	public ConfigReader configReader;
	public RequestFactory requestFactory;
	public TokenManager tokenManager;
	private RequestSpecification reqSpec;
	private Response response;
	private Map<String,Object> contextData = new HashMap<String,Object>();
	private Map<String,String> contextHeader =new HashMap<String,String>();
	  public TestContext() {
		  this.configReader = ConfigReader.getInstance();
		  this.tokenManager = new TokenManager(this.configReader);
		  this.requestFactory = new RequestFactory(this.configReader,this.tokenManager);		  
	  }
   public void set(String key, Object value) {
	   contextData.put(key, value);
   }	  
   public void set(Map<String,Object> map) {
	   contextData.putAll(map);
   }
   public <T> T get(String key, Class<T> type) {
	   return type.cast(contextData.get(key));
   }
   public void setHeader(Map<String,String> map) {
	   contextHeader.putAll(map);
   }
   public void addHeader(String key,String value) {
	   contextHeader.put(key, value);
   }
   public Map<String,String> getHeader(){
	   return contextHeader;
   }
   public void setRequestSpecification(RequestSpecification reqSpec) {
	   this.reqSpec = reqSpec;
   }
   public RequestSpecification getReqSpec() {
	   return this.reqSpec;
   }
  public Response getResponse() {
	  return this.response;
  }
  public void setResponse(Response response) {
	  this.response = response;
  }
  public void clear() {
	  System.out.println("clearing testContext");
	  contextData.clear();
	  contextHeader.clear();
	  configReader=null;
	  requestFactory=null;
	  response=null;
	  reqSpec=null;
  }
}
