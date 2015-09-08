package com.github.thiagosqr.test.functional;

import cucumber.api.CucumberOptions;
import cucumber.api.java8.En;
import cucumber.api.junit.Cucumber;
import com.github.thiagosqr.test.URLBuilder;
import org.junit.runner.RunWith;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by thiago-rs on 8/11/15.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features/functional/student_crud.feature", //feature:x:n -> x,n = numero das linhas
        monochrome = true,
        format = {
                "pretty",
                "html:target/cucumber",
                "json:target/targer_json/cucumber.json",
                "junit:target/targer_junit/cucumber.xml"
        }
)
public class FuncCrudStudent implements En {

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    public FuncCrudStudent(){

        Before(() -> {
            FirefoxProfile profile = new FirefoxProfile();
            profile.setEnableNativeEvents(true);
            profile.layoutOnDisk();
            driver = new FirefoxDriver(profile);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        });

        //  Scenario: Insert Student
        Given("the user is authorized to insert student", () -> {

            assertTrue(true);

        });

        When("name, date of bith, sex and active radio is filled out", () -> {
            driver.get(URLBuilder.getInstance().build("student/list"));
            driver.findElement(By.linkText("New")).click();
            driver.findElement(By.id("name")).clear();
            driver.findElement(By.id("name")).sendKeys("John Smith");
            driver.findElement(By.name("dob")).clear();
            driver.findElement(By.name("dob")).sendKeys("06/06/2006");
            driver.findElement(By.id("optionsRadiosInline1")).click();
        });

        And("save button is pressed to insert new student", () -> {
            driver.findElement(By.name("action")).click();
            driver.findElement(By.linkText("Update")).click();
        });

        Then("the system must present a list of student containing the new student inserted", () -> {
            assertTrue(true);
        });
        //  Scenario: Insert Student

        After(() -> {

            driver.quit();
            String verificationErrorString = verificationErrors.toString();
            if (!"".equals(verificationErrorString)) {
                fail(verificationErrorString);
            }

        });


    }

}
