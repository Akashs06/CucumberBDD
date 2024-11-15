package pagebase;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import steps.Initializationstepdef;
import testbase.Pagebase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Commonfunctions extends Pagebase {

    public static String toolName = Initializationstepdef.toolName;
    public Commonfunctions(){
        super(toolName);
    }

    public void takeScreenShot() throws IOException {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

        File source = ((TakesScreenshot) remoteDriver).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir");
        String filePath = path + "\\Screenshots\\" + sdf.format(d) + ".png";

     //   System.out.println(filePath);
        FileUtils.copyFile(source, new File(filePath));
        }


        public static void drawBorder(WebDriver driver, WebElement element){
        try{
            JavascriptExecutor js = (JavascriptExecutor) driver;
            if(element!=null){
                js.executeScript("arguments[0].style.border='3px solid red'", element);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        }
    }



