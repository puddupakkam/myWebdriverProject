package scripts;

import library.functions.CommonFunctions;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

@SuppressWarnings("unused")
public class firstTest extends CommonFunctions {
    String testCase = "My First Test";

    @Test(description = "My First Test")
    public void myFirstTest() throws Exception {
        pass("Test Report: My First Test on " + getBrowser() + " browser");
        waitForElementPresent(By.xpath("//input[@name='where']"));
        type(By.xpath("//input[@name='where']"), "Thornleigh");
        click(By.cssSelector("button.rui-search-button"));
        waitForElementPresent(By.xpath("//a[@class='suburbLink']"));
        checkCondition(isElementPresent(By.xpath("//a[@class='suburbLink'][.='Thornleigh, NSW 2120']")), "Thornleigh, NSW 2120 is displayed on the search results page");
        assertTrue(resultcount == 0, testCase + " has failed");
    }


}
