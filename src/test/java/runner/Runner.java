package runner;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.DataProvider;

@CucumberOptions(
		tags = (""),
		features= {"src/test/resources/feature"},
		glue= {"steps"},
		plugin = {"timeline:test-output/timeline-cucumberreports.html","pretty",
				"html:htmlreports.html"},
		dryRun=false,
		monochrome=false,
		publish=false		
		)
public class Runner extends AbstractTestNGCucumberTests{

@Override
@DataProvider(parallel=true)
public Object[][] scenarios(){
	return super.scenarios();
}
	
}
