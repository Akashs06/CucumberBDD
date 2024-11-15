package testbase;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import utilities.Readdatesheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;


public class Testbase1 {
    public  RemoteWebDriver remoteDriver;
    public static HashMap<String, String> execution_Parameters = new HashMap<String,String>();
    public static String toolName = "";

    protected static Readdatesheet rds = new Readdatesheet();



    public HashMap<String,String> getExecution_Parameters(String featureName){
        try{
         //   FileInputStream fis = new FileInputStream("C:\\Users\\akash\\Intellij Idea\\Myntra\\target\\TestRunnerBDD.xlsx");
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\target\\TestRunnerBDD.xlsx");

            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sh = wb.getSheet("Features");
            String userDir = System.getProperty("user.dir");
            userDir = userDir.concat("src\\main\\java\\Feature");

            for(int count=1; count<=sh.getLastRowNum();count++){
                XSSFRow row = sh.getRow(count);

                if((row.getCell(1).toString().equalsIgnoreCase("yes")) &&
                        featureName.contains(row.getCell(0).toString())){
                    System.out.println(row.getCell(2).toString());
                    if(row.getCell(2) != null){
                        execution_Parameters.put("ToolName", row.getCell(2).toString());
                    }
                    if(row.getCell(3) != null){
                        execution_Parameters.put("Browser", row.getCell(3).toString());
                    }
                    if(row.getCell(4) != null){
                        execution_Parameters.put("AppType", row.getCell(4).toString());
                    }
                    break;

                }
            }
        }catch (FileNotFoundException e ){
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return execution_Parameters;
    }

    public String initiateDriverWeb(String toolName, String browser){
        try{
            Pagebase pagebaseclass = new Pagebase(remoteDriver, toolName);
            remoteDriver = pagebaseclass.launchSite(browser);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("toolname testbase :" + toolName);
        return toolName;
    }
    public String getPropertyValue(String key) {
        String value = "";
        try {
            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\akash\\Intellij Idea\\Myntra\\target\\data.properties");
            Properties property = new Properties();
            property.load(fileInputStream);
            value = property.getProperty(key);
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return value;
    }

    public static ArrayList<String>getTagList() throws FileNotFoundException {
        ArrayList<String> listOfTags = new ArrayList<String>();
        try {
            FileInputStream fis;
            fis = new FileInputStream("C:\\Users\\akash\\Intellij Idea\\Myntra\\target\\TestRunnerBDD.xlsx");
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheet("Tags");

            for (int count = 1; count <= sheet.getLastRowNum(); count++) {
               XSSFRow row = sheet.getRow(count);

            if (row.getCell(1).getStringCellValue().equalsIgnoreCase("yes")) {
                String temp = row.getCell(0).toString();
                listOfTags.add(temp);
            }
        }
            wb.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
    }
return listOfTags;
        }

        public void closeDriver(String toolName){
        if (toolName.equalsIgnoreCase("Selenium")){

            remoteDriver.close();
            remoteDriver.quit();
        }
        }



}

