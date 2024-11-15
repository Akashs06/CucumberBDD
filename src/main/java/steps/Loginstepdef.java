package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.remote.RemoteWebDriver;
import pagebase.Commonfunctions;
import pagebase.Loginscreen;
import testbase.Testbase1;

import java.io.IOException;


public class Loginstepdef extends Testbase1 {

    Commonfunctions commonfunctions_obj = new Commonfunctions();
    Loginscreen loginScreen_obj = new Loginscreen();
//    Initializationstepdef stepDef = new Initializationstepdef();


    @Given("The user navigates to the login page")
    public void theUserNavigatesToTheLoginPage() throws IOException {
        try {
            String url = getPropertyValue("URL");
            System.out.println(url);
            loginScreen_obj.enterURL(url);
        }catch (Exception e){
            System.out.println("Screen is not launched");
            commonfunctions_obj.takeScreenShot();
        }

    }

    @Then("user clicks username and password")
    public void userClickUsernameAndPassword() throws IOException {
        loginScreen_obj.clickOnLoginlink();
        //System.out.println("user click username and password completed");
        commonfunctions_obj.takeScreenShot();
    }

    @When("user enters all the mandatory fields for {string} user and clicks continue to the home screen")
    public void userEnterAllTheMandatoryFieldForUserAndClickContinueForHomescreen(String args) throws IOException {
        System.out.println(this.getClass().getSimpleName()+ '_' + args);
        String phonenumber = rds.getValue(this.getClass().getSimpleName(), "Phone Number", "DATA");
        //   System.out.println("Phone number : "+ phonenumber);
        loginScreen_obj.enterDetailsToLogin(phonenumber);

    }

    @Then("verify if page is navigating to home screen")
    public void verifyIfPageIsNavigatingToHomescreen() throws IOException {
        System.out.println(this.getClass().getSimpleName());
        loginScreen_obj.clickicon();
    }

    @Then("user clicks on the option")
    public void userClickOnTheOption() throws IOException {
        System.out.println(this.getClass().getSimpleName());
        loginScreen_obj.searchOption();
        String SearchValue = rds.getValue(this.getClass().getSimpleName(), "Search", "DATA");
        loginScreen_obj.enterTextInSearch(SearchValue);
    }



}
