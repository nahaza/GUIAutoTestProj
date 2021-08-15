package pages;

import libs.TestData;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.yandex.qatools.htmlelements.element.*;

public class HomePage extends ParentPage {
    @FindBy(xpath = ".//button[@aria-label='Profile']")
    private Button buttonProfile;

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
        Assert.assertTrue("SignOut is not present in the Profile menu", isElementPresent(buttonSignOut));
        return this;
    }

    public HomePage checkIsRedirectToHomePage(){
        checkUrlWithPattern();
        checkIsSignOutButtonPresent();
        return this;
    }

    public void enterTheCourseName(String courseNameToSearch) {
        enterTextToElement(inputSearchForm, courseNameToSearch);
    }

    public HomePage checkIsCloseInputSearchFormButtonPresent(){
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
}
