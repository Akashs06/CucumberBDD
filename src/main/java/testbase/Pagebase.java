package testbase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Pagebase {

    private static String toolName;
    public String webBrowser;

    public static RemoteWebDriver remoteDriver;

    public static String executionEnvironment = null, chromeDriverPath = null;

    public Pagebase(RemoteWebDriver driver, String tool) {
        remoteDriver = driver;
        if (remoteDriver == null) {
            System.out.println("Warning: remoteDriver is null upon initialization in Pagebase.");
        } else {
            System.out.println("Success: remoteDriver is initialized in Pagebase.");
        }
        PageFactory.initElements(Pagebase.remoteDriver, this);
        toolName = tool;
    }



    public Pagebase(String toolName) {
        if (toolName.equalsIgnoreCase("Selenium")) {
            PageFactory.initElements(remoteDriver, this);
        }
    }

    public void launchSiteurl(String url) {
        remoteDriver.get(url);
    }

    public void navigateBack() {
        switch (toolName) {
            case "Selenium":
                remoteDriver.navigate().back();
                remoteDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                break;
        }
    }

    public static WebDriver getCurrentDriver() {
        WebDriver driver = remoteDriver;
        if (driver != null) {
            return driver;
        } else {
            return null;
        }
    }


    public static void defaultFrame() {
        WebDriver driver = getCurrentDriver();
        driver.switchTo().defaultContent();
    }

    public static void switchNewWindow(WebElement ele) throws InterruptedException {
        WebDriver driver = getCurrentDriver();
        Set<String> existingWindows = driver.getWindowHandles();
        System.out.println("Inside new Window");
        remoteDriver.findElement((By) ele).click();

        for (int i = 0; i <= 30; i++) {
            Set<String> currentWindows = driver.getWindowHandles();
            if (currentWindows.size() > existingWindows.size()) {
                break;
            } else {
                Thread.sleep(1000);
            }
        }
        try {
            Set<String> win = driver.getWindowHandles();
            for (String w : win) {
                if (!existingWindows.contains(w)) {
                    driver.switchTo().window(w);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        driver.manage().window().maximize();
    }

    public static void switchWindowByTitle(String title) {
        try {
            if (!remoteDriver.getTitle().equals(title)) {

                Set<String> windows = remoteDriver.getWindowHandles();
                for (String w : windows) {
                    remoteDriver.switchTo().window(w);
                    if (remoteDriver.getTitle().equals(w)) {
                        break;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public RemoteWebDriver launchSite(String browser) {
        webBrowser = browser.toLowerCase();
        switch (webBrowser) {
            case "chrome":
                chromeDriverPath = getAppProperty("ChromeDriverPath");
                System.out.println(chromeDriverPath);
                System.setProperty("webdriver.chrome.driver", chromeDriverPath);

                ChromeOptions options = new ChromeOptions();
                options.addArguments("--ignore-ssl-errors=ues");
                options.addArguments("--ignore-certificate-error");

                System.out.println("Before chrome Driver");
                remoteDriver = new ChromeDriver(options);
                break;
        }
        remoteDriver.manage().window().maximize();
        remoteDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        return remoteDriver;
    }

    public String getAppProperty(String key) {
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

    public void enterURL(String URL, String Pagetitle) {
        try {
            switch (toolName) {
                case "Selenium":

                    remoteDriver.get(URL);
                    break;
            }
        } catch (Exception e) {
            System.out.println("URL Launched");
            e.printStackTrace();
        }
    }

    public void elementClick(WebElement e, String elementName) {
        try {
            switch (toolName) {
                case "Selenium":
                    WebDriverWait wait = new WebDriverWait(remoteDriver, Duration.ofSeconds(10));
                    wait.until(ExpectedConditions.visibilityOf(e));
                    wait.until(ExpectedConditions.elementToBeClickable(e));
                    e.click();
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void enterText(WebElement element, String cred, String elementName) {
        try {
            switch (toolName) {
                case "Selenium":
                    WebDriverWait wait = new WebDriverWait(remoteDriver, Duration.ofSeconds(10));
                    wait.until(ExpectedConditions.visibilityOf(element));
                    break;
            }
            element.sendKeys(cred);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectByValue(WebElement element, String value, String elementName) {
        try {
            switch (toolName) {
                case "Selenium":
                    WebDriverWait wait = new WebDriverWait(remoteDriver, Duration.ofSeconds(10));
                    wait.until(ExpectedConditions.visibilityOf(element));
                    break;
            }
            element.click();
            Select s = new Select(element);
            s.selectByValue(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear(WebElement element, String elementName) {
        try {
            switch (toolName) {
                case "Selenium":
                    WebDriverWait wait = new WebDriverWait(remoteDriver, Duration.ofSeconds(10));
                    wait.until(ExpectedConditions.visibilityOf(element));
                    break;
            }
            element.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void closeDriver() {
//        if (toolName.equalsIgnoreCase("Selenium")) {
//            remoteDriver.quit();
//            remoteDriver.close();
//        }
//    }

    public void moveToElement(WebElement element, String elementName) {
        Actions actions;
        try {
            switch (toolName) {
                case "Selenium":
                    WebDriverWait wait = new WebDriverWait(remoteDriver, Duration.ofSeconds(10));
                    wait.until(ExpectedConditions.visibilityOf(element));
                    actions = new Actions(remoteDriver);
                    actions.moveToElement(element).build().perform();
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean elementIsDisplayed(WebElement element, String elementName) {
        try {
            switch (toolName) {
                case "Selenium":
                    WebDriverWait wait = new WebDriverWait(remoteDriver, Duration.ofSeconds(10));
                    wait.until(ExpectedConditions.visibilityOf(element));
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    try {
        if (element.isDisplayed()) {
            return true;
        } else
            return false;
        }catch (Exception e){
        return false;
    }

    }
}