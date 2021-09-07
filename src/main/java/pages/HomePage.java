package pages;


import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageWithElements.HeaderMenu;
import ru.yandex.qatools.htmlelements.element.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HomePage extends ParentPage {


    @FindBy(xpath = ".//input[@class='search-form__input ']")
    private TextInput inputSearchForm;

    @FindBy(xpath = ".//button[@class='st-button_style_none search-form__reset']")
    private Button closeInputSearchFormButton;

    @FindBy(xpath = ".//button[@class='button_with-loader search-form__submit']")
    private Button buttonSearch;

    @FindBy(xpath = ".//div[@class='search-form__input-wrapper']//div[@class='drop-down__body']")
    private Select dropDownSearchInput;

    @FindBy(xpath = ".//label[@class='form-checkbox'][2]")
    private CheckBox checkBoxFreeCourse;

    private String specificCourseLocator = ".//div[@data-list-type='search-results']//a[text()='%s']";

    private String listOfCoursesInSearchResultLocator =
            ".//div[@data-list-type='search-results']//a[@class='course-card__title']";

    private String specificCourseLocatorInMyCourses =
            ".//div[@class='course-widget__main-info']//a[text()='%s']";

    private String listOfCoursesInMyCoursesLocator =
            ".//li[@class='items-list__item course-list__item with-lazy-loading future-course-widget ember-view']";

    public HeaderMenu headerMenu = new HeaderMenu(webDriver);

    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    String getRelativeUrl() {
        return "/catalog?auth=";
    }


    public HomePage openHomePage() {
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.openLoginPage();
        if (!headerMenu.isSignOutButtonPresent()) {
            Map<String, String> newUserCredentials = loginPage.generateRegisteredNewUserCredentials();
            String email = newUserCredentials.get("Email");
            String password = newUserCredentials.get("Password");
            loginPage.fillLoginFormAndSubmit(email, password);
        }
        return this;
    }

    public HomePage checkIsRedirectToHomePage() {
        checkUrlWithPattern();
        headerMenu.checkIsSignOutButtonPresent();
        return this;
    }

    public void enterTheCourseName(String courseNameToSearch) {
        actionsWithElements.enterTextToElement(inputSearchForm, courseNameToSearch);
    }

    public HomePage checkIsCloseInputSearchFormButtonPresent() {
        Assert.assertTrue("CloseInputSearchFormButton is not present"
                , actionsWithElements.isElementPresent(closeInputSearchFormButton));
        return this;
    }

    public void clickOnSearchButton() {
        actionsWithElements.clickOnElement(buttonSearch);
    }

    public CoursePage searchAndJoinUniqueExistentFreeCourseByLoggedInUser(String specificCourseTitle) {
        enterTheCourseName(specificCourseTitle);
        checkIsCloseInputSearchFormButtonPresent();
        closeDropDownSearchBodyIfIsDisplayed();
        actionsWithElements.clickOnElement(checkBoxFreeCourse);
        clickOnSearchButton();
        actionsWithElements.webDriverWait15.until(ExpectedConditions.urlContains("search"));
        WebElement specificCourseLink = actionsWithElements.scrollToCourseWithSpecificTitleInResults(
                specificCourseLocator, listOfCoursesInSearchResultLocator, specificCourseTitle);
        actionsWithElements.clickOnElement(specificCourseLink, "specificCourseLink");
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        actionsWithElements.webDriverWait15.until(ExpectedConditions.urlContains("course"));
        actionsWithElements.webDriverWait15.until(ExpectedConditions.visibilityOf(headerMenu.buttonProfile));

        return new CoursePage(webDriver);
    }

    private HomePage closeDropDownSearchBodyIfIsDisplayed() {
        try {
            if (dropDownSearchInput.isDisplayed()) {
                actionsWithElements.clickOnElement(headerMenu.headerPanel);
            }
        } catch (Exception e) {
            logger.info("No dropDown in searchInput");
        }
        return this;
    }

    public CoursePage clickOnTheCourseInTheListCoursesJoinedPreviously(String specificCourseTitle) {
        WebElement specificCourseLink = actionsWithElements.scrollToCourseWithSpecificTitleInResults(
                specificCourseLocatorInMyCourses, listOfCoursesInMyCoursesLocator, specificCourseTitle);
        actionsWithElements.clickOnElement(specificCourseLink, "specificCourseLink");
        actionsWithElements.webDriverWait15.until(ExpectedConditions.urlContains("course"));
        return new CoursePage(webDriver);
    }
}
