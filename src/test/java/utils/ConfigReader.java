package utils;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
public class ConfigReader {
	private static ConfigReader configReader;
	private static Properties prop;
	
	private ConfigReader() {
		prop = new Properties();
		String filePath = System.getProperty("user.dir")
				+"/src/test/resources/testdata/config.properties";
		try(FileInputStream fis = new FileInputStream(new File(filePath))){ 
			 prop.load(fis);
		}catch(IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}
	public static ConfigReader getInstance() {
		 if(configReader==null) configReader = new ConfigReader();
		 return configReader;
	}
	
	public String get(String key) {
		return prop.getProperty(key);
	}
}
