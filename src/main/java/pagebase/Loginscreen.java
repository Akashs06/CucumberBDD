package pagebase;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.junit.Assert;

import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import steps.Initializationstepdef;
import testbase.Pagebase;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Loginscreen extends Pagebase {
    public static String toolName = Initializationstepdef.toolName;
    Commonfunctions commonfunctions = new Commonfunctions();

    public Loginscreen() {
        super(toolName);
    }

    @FindBy(xpath = "//div[@class='desktop-user']")
    private WebElement profile;

    @FindBy(xpath = "//a[text() ='login / Signup']")
    private WebElement loginbutton;

    @FindBy(xpath = "//div[@class='welcome-header']")
    private WebElement welcomeheadline;

    @FindBy(xpath = "//input[@class='form-control mobileNumberInput']")
    private WebElement enterphonenumber;

    @FindBy(xpath = "//div[text()='CONTINUE']")
    private WebElement continuebutton;

    @FindBy(xpath = "//div[@class='desktop-logo myntraweb-sprite']")
    private WebElement iconclick;

    @FindBy(xpath = "//input[@class='desktop-searchBar']")
    private WebElement searchbutton;

    @FindBy(xpath = "//ul[@class='desktop-group']/li")
    private List<WebElement> valuesinsearch;


    public void enterURL(String url) {
        try {
            enterURL(url, "URL_Launched");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Exception caught : " + e.getMessage());
        }
    }

    public void clickOnLoginlink() {
        boolean isDisplay = false;

        isDisplay = this.elementIsDisplayed(profile, "Profile");
        if (isDisplay) {
            commonfunctions.drawBorder(remoteDriver, profile);
            Assert.assertEquals(isDisplay, true);
            moveToElement(profile, "profile");
        }
        isDisplay = this.elementIsDisplayed(loginbutton, "Login/Signup_option");
        if (isDisplay) {
            commonfunctions.drawBorder(remoteDriver, loginbutton);
            Assert.assertEquals(isDisplay, true);
            moveToElement(loginbutton, "Login/Signup_option");
            elementClick(loginbutton, "Login/Signup_option");
        }
    }

    public void enterDetailsToLogin(String cred) throws IOException {
        boolean isDisplay = false;
        isDisplay = this.elementIsDisplayed(welcomeheadline, "Welcome_Headline");
        if (isDisplay) {
            Assert.assertEquals(isDisplay, true);
            this.enterText(enterphonenumber, cred, "Enter_Phone_Number");
            commonfunctions.drawBorder(remoteDriver, enterphonenumber);
            commonfunctions.takeScreenShot();
            elementClick(continuebutton, "Continue_Button");

        }

    }

    public void clickicon() throws IOException {
        boolean isDisplay = false;
        isDisplay = this.elementIsDisplayed(welcomeheadline, "Welcome_Headline");
        if (isDisplay) {
            Assert.assertEquals(isDisplay, true);
            elementClick(iconclick, "Myntra_Icon_Click");
            commonfunctions.takeScreenShot();
        }
    }

    public void searchOption() throws IOException {
        elementClick(searchbutton, "Search_Option");
        commonfunctions.drawBorder(remoteDriver, loginbutton);
        commonfunctions.takeScreenShot();
    }

    public void enterTextInSearch(String value) throws IOException {
        enterText(searchbutton, value , "Text_in_Search_Option");
        commonfunctions.drawBorder(remoteDriver, searchbutton);
        commonfunctions.takeScreenShot();
        WebDriverWait wait = new WebDriverWait(remoteDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElements(valuesinsearch));
  //      System.out.println(valuesinsearch.size());
        if(valuesinsearch.size()>0){
            for (WebElement result : valuesinsearch) {
               if (result.getText().equalsIgnoreCase(value)){
                   commonfunctions.drawBorder(remoteDriver, result);
                   result.click();
                   break;
               }
            }
        }else if (valuesinsearch.size() == 0) {
            System.out.println("No search results found.");
        }

    }
}
