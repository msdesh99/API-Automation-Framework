package utils;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
public class HeaderReader {
	private String file;
	private Map<String,Map<String,String>> headerMap;
	public HeaderReader(String filePath) {
		file = System.getProperty("user.dir")+"/src/test/resources/testdata/"+filePath;
		ObjectMapper mapper = new ObjectMapper();
		try(FileInputStream fis = new FileInputStream(new File(file))){
			 headerMap =  mapper.readValue(fis, 					  
					  new TypeReference<Map<String,Map<String,String>>>(){});
		}catch(IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}
	public Map<String,String>getHeadersForScenario(String scenarioName){
	        return headerMap.get(scenarioName);	
	}
	
	public static void main(String[] args) {
		HeaderReader hRead = new HeaderReader("header.json");
		hRead.headerMap.entrySet().stream().forEach(key->System.out.println(key.getKey()+" "+ key.getValue()));
	}

}
