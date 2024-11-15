package steps;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import io.cucumber.java.After;
import org.junit.AssumptionViolatedException;
import testbase.Testbase1;
import utilities.Util;
import utilities.WhitelistingPath;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Initializationstepdef extends Testbase1 {

    public static String toolName;
    public static String feature = "";
    private static boolean SkippedScenario = false;
    private static String screenshotFileName;

    private static String screenshotFilePath;

    public static HashMap<String,String> execution_Parameters = new HashMap<String,String>();

    @Before
    public void SkipScenario(Scenario scenario) throws FileNotFoundException {
        final ArrayList<String> scenarioTags = new ArrayList<>();
        scenarioTags.addAll(scenario.getSourceTagNames());

        if(!checkForScenarioTag(scenarioTags)){
            SkippedScenario = true;
            throw new AssumptionViolatedException("SKIPPED. Scenario does not hava associated tag marked as \"Yes\"");
        }else {
            SkippedScenario = false;
            screenshotFileName = scenario.getName().replace(" ", "-")+ "=" + getCurrentlyDateTime();

            screenshotFilePath = getScreenShotPath();
            System.out.println("Driver start");
            driverStart(scenario);
            System.out.println("Driver started");
        }
    }

    public static String getCurrentlyDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date());
    }

    private String getScreenShotPath(){
        File directory = new File(getPropertyValue("SCREENSHOT_PATH"));
        if(! directory.exists()){
            directory.mkdirs();
        }
        return System.getProperty(("user.dir")+ Util.getFileSeparator()+ getPropertyValue("SCREENSHOT_PATH"));
    }


    public void driverStart(Scenario scenario){
     //   String featureName = scenario.getId();
        String featureName = "1_Login.feature";
        System.out.println(featureName);
        execution_Parameters = this.getExecution_Parameters(featureName);
        if(!execution_Parameters.isEmpty()){
          toolName =  execution_Parameters.get("ToolName");
          if(toolName.equalsIgnoreCase("Selenium")){
              System.out.println("Driver start initiatedriverWeb");
              initiateDriverWeb(toolName,execution_Parameters.get("Browser"));

          }
        }
    }

    private boolean checkForScenarioTag(final ArrayList<String> scenarioTags) throws FileNotFoundException {
        boolean foundTag = false;
        ArrayList<String> tagList = getTagList();
        for(String tag : tagList){
            if(scenarioTags.contains(tag)){
                foundTag=true;
                break;
            }
        }
        if(foundTag)
            return true;
        else
            return false;
    }

    String getScreenShotFileLocation(){
        String absoluteFilePath = screenshotFilePath + Util.getFileSeparator() + screenshotFileName + ".docx";
        String encryptedPath = WhitelistingPath.cleanStringForFilePath(absoluteFilePath);
        return encryptedPath;
    }



    @After
    public void driverStop(Scenario scenario){
        if (!SkippedScenario){
            if(getPropertyValue("UPDATE_TO_JIRA").equalsIgnoreCase("Y")){
             //   String testLogFile = getScreenShotFileLocation();
             //   updateExecutionStatusToJira(Scenario, testLogFile);
            }
            closeDriver(toolName);
        }
    }

}
