package libs;

import org.aeonbits.owner.ConfigFactory;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.element.TypifiedElement;


import java.util.List;


public class ActionsWithElements {
    Logger logger = Logger.getLogger(getClass());
    WebDriver webDriver;
    public WebDriverWait webDriverWait15;
    public WebDriverWait webDriverWait5;
    public static ConfigProperties configProperties =
            ConfigFactory.create(ConfigProperties.class);


    public ActionsWithElements(WebDriver webDriver) {
        this.webDriver = webDriver;
        webDriverWait15 = new WebDriverWait(webDriver, configProperties.TIME_FOR_DFFAULT_WAIT());
        webDriverWait5 = new WebDriverWait(webDriver, configProperties.TIME_FOR_EXPLICIT_WAIT_LOW());
    }

    public String getElementName(WebElement webElement) {
        String elementName = "";
        if (webElement instanceof TypifiedElement) {
            elementName = " '" + ((TypifiedElement) webElement).getName() + "' ";
        }
        return elementName;
    }

    public void enterTextToElement(WebElement webElement, String text) {
        try {
            webDriverWait15.until(ExpectedConditions.visibilityOf(webElement));
            webElement.clear();
            webElement.sendKeys(text);
            logger.info("'" + text + "' was entered in element " + getElementName(webElement));
        } catch (Exception e) {
            writeErrorAndStopTest(e, getElementName(webElement));
        }
    }

    public void enterTextToElement(WebElement webElement, String text, String elementName) {
        try {
            webDriverWait15.until(ExpectedConditions.visibilityOf(webElement));
            webElement.clear();
            webElement.sendKeys(text);
            logger.info("'" + text + "' was entered in element " + elementName);
        } catch (Exception e) {
            writeErrorAndStopTest(e, elementName);
        }
    }

    public void clickOnElement(WebElement webElement) {
        try {
            webDriverWait15.until(ExpectedConditions.elementToBeClickable(webElement));
            webElement.click();
            logger.info(getElementName(webElement) + " element was clicked");
        } catch (Exception e) {
            writeErrorAndStopTest(e, getElementName(webElement));
        }
    }

    public void clickOnElement(WebElement webElement, String elementName) {
        try {
            webDriverWait15.until(ExpectedConditions.elementToBeClickable(webElement));
            webElement.click();
            logger.info(elementName + " element was clicked");
        } catch (Exception e) {
            writeErrorAndStopTest(e, elementName);
        }
    }

    private void writeErrorAndStopTest(Exception e, String elementName) {
        logger.error("Cannot work with element" + elementName + e);
        Assert.fail("Cannot work with element" + elementName + e);
    }

    public boolean isElementPresent(WebElement webElement) {
        try {
            boolean state = webElement.isDisplayed();
            if (state) {
                logger.info(getElementName(webElement) + " element is present");
            } else {
                logger.info(getElementName(webElement) + " element is not present");
            }
            return state;
        } catch (Exception e) {
            logger.info(getElementName(webElement) + " element is not present");
            return false;
        }
    }

    public boolean isElementPresent(WebElement webElement, String elementName) {
        try {
            boolean state = webElement.isDisplayed();
            if (state) {
                logger.info(elementName + " element is present");
            } else {
                logger.info(elementName + " element is not present");
            }
            return state;
        } catch (Exception e) {
            logger.info(elementName + " element is not present");
            return false;
        }
    }

    public WebElement scrollToCourseWithSpecificTitleInResults(
            String specificCourseLocator
            , String listOfCoursesInSearchResultLocator
            , String specificCourseTitle) {
        try {
            while (!webDriver.findElement(By.xpath(String.format(
                    specificCourseLocator, specificCourseTitle))).isDisplayed()) {
                List<WebElement> listOfCoursesInSearchResult = webDriver.findElements(By.xpath(
                        listOfCoursesInSearchResultLocator));
                System.out.println("Results count: " + listOfCoursesInSearchResult.size());
                JavascriptExecutor jse = (JavascriptExecutor) webDriver;
                jse.executeScript("arguments[0].scrollIntoView();"
                        , listOfCoursesInSearchResult.get(listOfCoursesInSearchResult.size() - 1));
                webDriverWait15.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(
                        listOfCoursesInSearchResultLocator), listOfCoursesInSearchResult.size()));
            }
        } catch (Exception e) {
            logger.error("The course with the title " + specificCourseTitle + " was not found");
            Assert.fail("The course with the title " + specificCourseTitle + " was not found");
        }
        return webDriver.findElement(By.xpath(String.format(
                specificCourseLocator, specificCourseTitle)));
    }

    public void scrollToWebElement(WebElement webElement) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView();", webElement);
        webDriverWait15.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public void dragAndDropElements(WebElement elementFrom, WebElement elementTo) {
        Actions actions = new Actions(webDriver);
        actions.clickAndHold(elementFrom).build().perform();
        actions.moveToElement(elementTo).build().perform();
        actions.moveByOffset(-1, -1).build().perform();
        actions.release().build().perform();
    }

}
