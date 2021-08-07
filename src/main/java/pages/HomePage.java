package pages;

import libs.TestData;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends ParentPage {
    @FindBy(xpath = ".//button[@aria-label='Profile']")
    private WebElement buttonProfile;

    @FindBy(xpath = ".//button[text()='Выход']")
    private WebElement buttonSignOut;

    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }


    public boolean isSignOutButtonPresent() {
        clickOnElement(buttonProfile);
        return isElementPresent(buttonSignOut);
    }

    public HomePage checkIsSignOutButtonPresent() {
        clickOnElement(buttonProfile);
        Assert.assertTrue("SignOut is not present in the Profile menu", isElementPresent(buttonSignOut));
        return this;
    }

    public HomePage openHomePage() {
        LandingPage landingPage = new LandingPage(webDriver);
        landingPage.openLandingPage();
        if (!isSignOutButtonPresent()) {
            landingPage.fillLoginFormAndSubmit(TestData.getValidEmail(), TestData.getValidPassword());
        }
        return this;
    }
}
