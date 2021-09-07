package pages;

import libs.TestData;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageWithElements.HeaderMenu;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.TextBlock;
import ru.yandex.qatools.htmlelements.element.TextInput;
import ru.yandex.qatools.htmlelements.element.Select;
import ru.yandex.qatools.htmlelements.element.CheckBox;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class LoginPage extends ParentPage {

    @FindBy(xpath = ".//input[@id='id_login_email']")
    private TextInput inputEmail;

    @FindBy(xpath = ".//input[@id='id_login_password']")
    private TextInput inputPassword;

    @FindBy(xpath = ".//form[@id='login_form']//button[@class='sign-form__btn button_with-loader ']")
    private Button buttonLogin;

    @FindBy(xpath = ".//input[@id='id_registration_full-name']")
    private TextInput inputFullNameRegistrationForm;

    @FindBy(xpath = ".//input[@id='id_registration_email']")
    private TextInput inputEmailRegistrationForm;

    @FindBy(xpath = ".//input[@id='id_registration_password']")
    private TextInput inputPasswordRegistrationForm;

    @FindBy(xpath = ".//form[@id='registration_form']//button[@class='sign-form__btn button_with-loader ']")
    private Button buttonRegister;

    @FindBy(xpath = ".//form[@id='login_form']//li[@role='alert']")
    private TextBlock invalidCredErrorMessage;

    @FindBy(xpath = ".//input[@class='search-form__input ']")
    private TextInput inputSearchForm;

    @FindBy(xpath = ".//button[@class='button_with-loader search-form__submit']")
    private Button buttonSearch;

    @FindBy(xpath = ".//div[@class='search-form__input-wrapper']//div[@class='drop-down__body']")
    private Select dropDownSearchInput;

    @FindBy(xpath = ".//label[@class='form-checkbox'][2]")
    private CheckBox checkBoxFreeCourse;

    @FindBy(xpath = ".//footer[@class='page-footer page-footer-modern ember-view page_footer']")
    private TextBlock footer;

    private String specificCourseLocator = ".//div[@data-list-type='search-results']//a[text()='%s']";

    private String listOfCoursesInSearchResultLocator =
            ".//div[@data-list-type='search-results']//a[@class='course-card__title']";

    public HeaderMenu headerMenu = new HeaderMenu(webDriver);

    TestData testData = new TestData();

    public LoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    String getRelativeUrl() {
        return "/catalog";
    }

    public void openLoginPage() {
        try {
            webDriver.get("https://stepik.org/catalog");
            webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            logger.info("Landing page was opened");
        } catch (Exception e) {
            logger.error("Cannot work with LandingPage" + e);
            Assert.fail("Cannot work with LandingPage");
        }
    }

    public void enterEmailInLogIn(String email) {
        actionsWithElements.enterTextToElement(inputEmail, email);
    }

    public void enterPasswordInLogIn(String password) {
        actionsWithElements.enterTextToElement(inputPassword, password);
    }

    public void clickOnButtonLogIn() {
        actionsWithElements.clickOnElement(buttonLogin);
    }

    public LoginPage fillLoginFormAndSubmit(String email, String password) {
        openLoginPage();
        headerMenu.clickOnButtonToProceedLogIn();
        enterEmailInLogIn(email);
        enterPasswordInLogIn(password);
        clickOnButtonLogIn();
        return this;
    }

    public LoginPage checkIsLoginButtonPresent() {
        Assert.assertTrue("Login button is not present", actionsWithElements.isElementPresent(buttonLogin));
        return this;
    }

    public LoginPage checkIsInvalidCredErrorMessagePresent() {
        Assert.assertTrue("No error message displayed", actionsWithElements.isElementPresent(invalidCredErrorMessage));
        return this;
    }

    public void checkIsButtonToRegisterPresent() {
        Assert.assertTrue("Register button is not present"
                , actionsWithElements.isElementPresent(buttonRegister));
    }

    public void enterFullNameInRegistrationForm(String fullName) {
        actionsWithElements.enterTextToElement(inputFullNameRegistrationForm, fullName);
    }

    public void enterEmailInRegistrationForm(String email) {
        actionsWithElements.enterTextToElement(inputEmailRegistrationForm, email);
    }

    public void enterPasswordInRegistrationForm(String password) {
        actionsWithElements.enterTextToElement(inputPasswordRegistrationForm, password);
    }

    public void clickOnButtonRegister() {
        actionsWithElements.clickOnElement(buttonRegister);
    }

    public void fillRegistrationFormAndSubmit(String fullName, String email, String password) {
        openLoginPage();
        headerMenu.clickOnButtonToProceedRegister();
        enterFullNameInRegistrationForm(fullName);
        enterEmailInRegistrationForm(email);
        enterPasswordInRegistrationForm(password);
        clickOnButtonRegister();
    }

    public Map<String, String> generateRegisteredNewUserCredentials() {
        String fullname = testData.getFullNameToRegister();
        String email = testData.getEmailToRegister();
        String password = testData.getPasswordToRegister();
        Map<String, String> newUserCredentials = new HashMap<>();
        newUserCredentials.put("Username", fullname);
        newUserCredentials.put("Email", email);
        newUserCredentials.put("Password", password);
        fillRegistrationFormAndSubmit(fullname, email, password);
        headerMenu.clickOnProfileAndSignOutButton();
        checkIsButtonToRegisterPresent();
        return newUserCredentials;
    }

    public Map<String, String> generateCredentialsOfUserWithJoinedFreeCourse(String specificCourseTitle) {
        String fullname = testData.getFullNameToRegister();
        String email = testData.getEmailToRegister();
        String password = testData.getPasswordToRegister();
        Map<String, String> newUserCredentials = new HashMap<>();
        newUserCredentials.put("Username", fullname);
        newUserCredentials.put("Email", email);
        newUserCredentials.put("Password", password);
        fillRegistrationFormAndSubmit(fullname, email, password);
        new HomePage(webDriver).checkIsRedirectToHomePage()
                .searchAndJoinUniqueExistentFreeCourseByLoggedInUser(specificCourseTitle)
                .checkIsRedirectToCoursePage()
                .clickOnButtonJoinTheCourseLoggedInUser()
                .checkIsRedirectToLessonPage();
        headerMenu.clickOnProfileAndSignOutButton();
        checkIsButtonToRegisterPresent();
        return newUserCredentials;
    }

    public HomePage loginWithValidCred(String email, String password) {
        fillLoginFormAndSubmit(email, password);
        return new HomePage(webDriver);
    }


    public LoginPage checkValidationEmptyEmailErrorMessage() {
        Assert.assertEquals("Заполните это поле.", inputEmail.getAttribute("validationMessage"));
        return this;
    }

    public LoginPage checkValidationEmptyPasswordErrorMessage() {
        Assert.assertEquals("Заполните это поле.", inputPassword.getAttribute("validationMessage"));
        return this;
    }

    public void enterTheCourseName(String courseNameToSearch) {
        actionsWithElements.enterTextToElement(inputSearchForm, courseNameToSearch);
    }

    public void clickOnSearchButton() {
        actionsWithElements.clickOnElement(buttonSearch);
    }

    public CoursePage searchAndJoinUniqueExistentFreeCourseByUnauthorisedUser(String specificCourseTitle) {
        openLoginPage();
        enterTheCourseName(specificCourseTitle);
        closeDropDownSearchBodyIfIsDisplayed();
        actionsWithElements.clickOnElement(checkBoxFreeCourse);
        clickOnSearchButton();
        actionsWithElements.webDriverWait15.until(ExpectedConditions.urlContains("search"));
        WebElement specificCourseLink = actionsWithElements.scrollToCourseWithSpecificTitleInResults(
                specificCourseLocator, listOfCoursesInSearchResultLocator, specificCourseTitle);
        actionsWithElements.clickOnElement(specificCourseLink, "specificCourseLink");
        actionsWithElements.webDriverWait15.until(ExpectedConditions.urlContains("course"));
        return new CoursePage(webDriver);
    }


    private LoginPage closeDropDownSearchBodyIfIsDisplayed() {
        try {
            if (dropDownSearchInput.isDisplayed()) {
                actionsWithElements.clickOnElement(headerMenu.headerPanel);
            }
        } catch (Exception e) {
            logger.info("No dropDown in searchInput");
        }
        return this;
    }
}