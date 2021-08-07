package pages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ParentPage {
    Logger logger = Logger.getLogger(getClass());
    WebDriver webDriver;
    WebDriverWait webDriverWait10;

    protected ParentPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
        webDriverWait10 = new WebDriverWait(webDriver, 10);
    }

    protected void enterTextToElement(WebElement webElement, String text) {
        try {
            webElement.clear();
            webElement.sendKeys(text);
            logger.info("'" + text + "' was entered in element");
        } catch (Exception e) {
            writeErrorAndStopTest(e);
        }
    }

    protected void clickOnElement(WebElement webElement) {
        try {
            webElement.click();
            logger.info("Element was clicked");
        } catch (Exception e) {
            writeErrorAndStopTest(e);
        }
        webDriverWait10.withMessage("Proceed to next test");
    }

    private void writeErrorAndStopTest(Exception e) {
        logger.error("Cannot work with element" + e);
        Assert.fail("Cannot work with element" + e);
    }

    protected boolean isElementPresent(WebElement webElement) {
        try {
            boolean state = webElement.isDisplayed();
            if (state) {
                logger.info("Element is present");
            } else {
                logger.info("Element is not present");
            }
            return state;
        } catch (Exception e) {
            logger.info("Element is not present");
            return false;
        }
    }

}
