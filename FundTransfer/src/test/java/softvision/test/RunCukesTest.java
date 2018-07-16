package softvision.test;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"pretty", "html:target/cucumber",
        "json:target/cucumber.json",
        "junit:target/cucumber_junit_report.xml"})
public class RunCukesTest {
	
}
