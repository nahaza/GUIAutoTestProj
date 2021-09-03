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

    @FindBy(xpath = ".//form[@id='registration_form']//button[@class='sign-form__btn button_with-loader ']")
    private Button buttonRegister;

    @FindBy(xpath = ".//form[@id='login_form']//button[@class='sign-form__btn button_with-loader ']")
    private Button buttonLogin;

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
        enterTextToElement(inputSearchForm, courseNameToSearch);
    }

    public HomePage checkIsCloseInputSearchFormButtonPresent() {
        Assert.assertTrue("CloseInputSearchFormButton is not present"
                , isElementPresent(closeInputSearchFormButton));
        return this;
    }

    public void clickOnSearchButton() {
        clickOnElement(buttonSearch);
    }

    public CoursePage searchAndJoinUniqueExistentFreeCourseByLoggedInUser(String specificCourseTitle) {
        enterTheCourseName(specificCourseTitle);
        checkIsCloseInputSearchFormButtonPresent();
        closeDropDownSearchBodyIfIsDisplayed();
        clickOnElement(checkBoxFreeCourse);
        clickOnSearchButton();
        webDriverWait10.until(ExpectedConditions.urlContains("search"));
        WebElement specificCourseLink = scrollToCourseWithSpecificTitleInResults(
                specificCourseLocator, listOfCoursesInSearchResultLocator, specificCourseTitle);
        clickOnElement(specificCourseLink, "specificCourseLink");
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        webDriverWait10.until(ExpectedConditions.urlContains("course"));
        webDriverWait10.until(ExpectedConditions.visibilityOf(headerMenu.buttonProfile));

        return new CoursePage(webDriver);
    }

    private HomePage closeDropDownSearchBodyIfIsDisplayed() {
        try {
            if (dropDownSearchInput.isDisplayed()) {
                clickOnElement(headerMenu.headerPanel);
            }
        } catch (Exception e) {
            logger.info("No dropDown in searchInput");
        }
        return this;
    }

    public CoursePage clickOnTheCourseInTheListCoursesJoinedPreviously(String specificCourseTitle) {
        WebElement specificCourseLink = scrollToCourseWithSpecificTitleInResults(
                specificCourseLocatorInMyCourses, listOfCoursesInMyCoursesLocator, specificCourseTitle);
        clickOnElement(specificCourseLink, "specificCourseLink");
        webDriverWait10.until(ExpectedConditions.urlContains("course"));
        return new CoursePage(webDriver);
    }
}
