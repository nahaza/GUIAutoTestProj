package pages;

import libs.ConfigProperties;
import org.aeonbits.owner.ConfigFactory;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.element.TypifiedElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;

public abstract class ParentPage {
    Logger logger = Logger.getLogger(getClass());
    WebDriver webDriver;
    WebDriverWait webDriverWait10, webDriverWait5;
    public static ConfigProperties configProperties =
            ConfigFactory.create(ConfigProperties.class);
    protected final String baseUrl = configProperties.base_url();

    protected ParentPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(
                new HtmlElementDecorator(
                        new HtmlElementLocatorFactory(webDriver))
                , this);
        webDriverWait10 = new WebDriverWait(webDriver, configProperties.TIME_FOR_DFFAULT_WAIT());
        webDriverWait5 = new WebDriverWait(webDriver, configProperties.TIME_FOR_EXPLICIT_WAIT_LOW());
    }

    abstract String getRelativeUrl();

    protected void checkUrl() {
        Assert.assertEquals("Invalid page"
                , baseUrl + getRelativeUrl()
                , webDriver.getCurrentUrl());
    }

    protected void checkUrlWithPattern() {
        Assert.assertThat("Invalid page"
                , webDriver.getCurrentUrl()
                , containsString(baseUrl + getRelativeUrl()));
    }

    private String getElementName(WebElement webElement) {
        String elementName = "";
        if (webElement instanceof TypifiedElement) {
            elementName = " '" + ((TypifiedElement) webElement).getName() + "' ";
        }
        return elementName;
    }

    protected void enterTextToElement(WebElement webElement, String text) {
        try {
            webElement.clear();
            webElement.sendKeys(text);
            webDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            logger.info("'" + text + "' was entered in element " + getElementName(webElement));
        } catch (Exception e) {
            writeErrorAndStopTest(e);
        }
    }

    protected void enterTextToElement(WebElement webElement, String text, String elementName) {
        try {
            webElement.clear();
            webElement.sendKeys(text);
            webDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            logger.info("'" + text + "' was entered in element " + elementName);
        } catch (Exception e) {
            writeErrorAndStopTest(e);
        }
    }

    protected void clickOnElement(WebElement webElement) {
        try {
            webElement.click();
            webDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            logger.info(getElementName(webElement) + " element was clicked");
        } catch (Exception e) {
            writeErrorAndStopTest(e);
        }
    }

    protected void clickOnElement(WebElement webElement, String elementName) {
        try {
            webElement.click();
            webDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            logger.info(elementName + " element was clicked");
        } catch (Exception e) {
            writeErrorAndStopTest(e);
        }
    }

    private void writeErrorAndStopTest(Exception e) {
        logger.error("Cannot work with element" + e);
        Assert.fail("Cannot work with element" + e);
    }

    protected boolean isElementPresent(WebElement webElement) {
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

    protected boolean isElementPresent(WebElement webElement, String elementName) {
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

    protected void selectTextInDropDownByClickOnOption(WebElement dropDown, WebElement dropDownOptionToBeSelected, String text) {
        try {
            clickOnElement(dropDown);
            webDriverWait10.until(ExpectedConditions.visibilityOf(dropDownOptionToBeSelected));
            clickOnElement(dropDownOptionToBeSelected);
            logger.info("' " + text + "' was selected in DropDown " + getElementName(dropDown));
        } catch (Exception e) {
            writeErrorAndStopTest(e);
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
                webDriverWait10.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(
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
        webDriverWait10.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public void dragAndDropElements(WebElement elementFrom, WebElement elementTo) {
        Actions actions = new Actions(webDriver);
        actions.clickAndHold(elementFrom).build().perform();
        actions.moveToElement(elementTo).build().perform();
        actions.moveByOffset(-1, -1).build().perform();
        actions.release().build().perform();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }


    public void selectCheckboxOptionWithValue(String textOfOptionsToBeSelected, String optionsToBeSelectedLocator) {
        String[] listOfOptionsTextToBeSelected = textOfOptionsToBeSelected.split(";");
        List<WebElement> optionsToBeSelected = webDriver.findElements(By.xpath(optionsToBeSelectedLocator));
        for (WebElement element : optionsToBeSelected) {
            if (textOfOptionsToBeSelected.contains(element.getText())) {
                clickOnElement(element, element.getText());
            }
        }
    }

}
