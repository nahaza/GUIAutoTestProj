package pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LandingPage extends ParentPage{
    @FindBy(xpath = ".//a[text()='Log in']")
    private WebElement buttonLogin;

    public LandingPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void openLandingPage(){
        try {
            webDriver.get("https://stepik.org/catalog");
            logger.info("Landing page was opened");
        }catch (Exception e){
            logger.error("Cannot work with LandingPage" + e);
            Assert.fail("Cannot work with LandingPage");
        }
    }

    public void clickOnButtonLogIn(){
        clickOnElement(buttonLogin);
    }


}
