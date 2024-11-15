import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions( features ="src/main/java/Feature/1_Login.feature" ,
        glue = {"steps.Loginstepdef"},
        dryRun = false,
        monochrome = true,stepNotifications = true,
        plugin = {"html:target/cucumber-report/reports1.html"})


//,
public class ExecuteTest {
}
