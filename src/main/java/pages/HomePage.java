package pages;

import libs.TestData;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.yandex.qatools.htmlelements.element.*;

public class HomePage extends ParentPage {

//    @FindBy(xpath = ".//a[@class='ember-link ember-view navbar__link st-link']")
    @FindBy(xpath = ".//header//a[text()='Мои курсы']")
    private Button buttonMyCourses;

    @FindBy(xpath = ".//button[@aria-label='Profile']")
    private Button buttonProfile;

    @FindBy(xpath = ".//ul[@class='menu menu_theme_popup-dark menu_right drop-down-content ember-view']")
    private Select dropDownProfile;

    @FindBy(xpath = ".//button[text()='Выход']")
    private Button buttonSignOut;

    @FindBy(xpath = ".//input[@class='search-form__input ']")
    private TextInput inputSearchForm;

    @FindBy(xpath = ".//button[@class='st-button_style_none search-form__reset']")
    private Button closeInputSearchFormButton;

    @FindBy(xpath = ".//button[@class='button_with-loader search-form__submit']")
    private Button buttonSearch;

    @FindBy(xpath = ".//div[@class='search-form__input-wrapper']//div[@class='drop-down__body']")
    private Select dropDownSearchInput;

    @FindBy(xpath = ".//div[@class='search-form__form']//span[text()='Бесплатные']")
    private CheckBox checkBoxFreeCourse;

    @FindBy(xpath = ".//nav[@aria-label='Общая навигация по сайту']")
    private TextBlock headerPanel;

    @FindBy(xpath = ".//footer[@class='page-footer page-footer-modern ember-view page_footer']")
    private TextBlock footer;

    private String specificCourseLocator = ".//div[@data-list-type='search-results']//a[text()='%s']";

    private String listOfCoursesInSearchResultLocator =
            ".//div[@data-list-type='search-results']//a[@class='course-card__title']";

    private String specificCourseLocatorInMyCourses =
            ".//div[@class='course-widget__main-info']//a[text()='%s']";

    private String listOfCoursesInMyCoursesLocator =
            ".//li[@class='items-list__item course-list__item with-lazy-loading future-course-widget ember-view']";


    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    String getRelativeUrl() {
        return "/catalog?auth=";
    }


    public HomePage openHomePage() {
        LandingPage landingPage = new LandingPage(webDriver);
        TestData testData = new TestData();
        landingPage.openLandingPage();
        if (!isSignOutButtonPresent()) {
            landingPage.fillLoginFormAndSubmit(testData.getValidEmail(), testData.getValidPassword());
        }
        return this;
    }

    public boolean isSignOutButtonPresent() {
        clickOnElement(buttonProfile);
        return isElementPresent(buttonSignOut);
    }

    public HomePage checkIsSignOutButtonPresent() {
        clickOnElement(buttonProfile);
        webDriverWait10.until(ExpectedConditions.visibilityOf(dropDownProfile));
        Assert.assertTrue("SignOut is not present in the Profile menu", isElementPresent(buttonSignOut));
        return this;
    }

    public HomePage checkIsRedirectToHomePage() {
        checkUrlWithPattern();
        checkIsSignOutButtonPresent();
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
        webDriverWait10.until(ExpectedConditions.urlContains("course"));
        webDriverWait10.until(ExpectedConditions.visibilityOf(buttonProfile));

        return new CoursePage(webDriver);
    }

    private HomePage closeDropDownSearchBodyIfIsDisplayed() {
        try {
            if (dropDownSearchInput.isDisplayed()) {
                clickOnElement(headerPanel);
            }
        } catch (Exception e) {
            logger.info("No dropDown in searchInput");
        }
        return this;
    }

    public HomePage clickOnMyCourseButton() {
        clickOnElement(buttonMyCourses);
        webDriverWait10.until(ExpectedConditions.urlContains("users"));
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
