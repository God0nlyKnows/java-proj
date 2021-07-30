package zadanie;

import org.junit.runner.RunWith;   
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;  

@RunWith(Cucumber.class) 
@CucumberOptions(features="src/test/resources/features/zadanie1.feature",
glue={"zadanie"},monochrome = true,

plugin = { "pretty", "html:target/htmlreports", "json:target/reports/cucumber.json",  "junit:target/reports/cucumber.xml"})
public class TestZad1 {
    
}