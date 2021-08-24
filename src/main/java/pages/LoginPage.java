package pages;

import libs.TestData;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.TextBlock;
import ru.yandex.qatools.htmlelements.element.TextInput;
import ru.yandex.qatools.htmlelements.element.Select;
import ru.yandex.qatools.htmlelements.element.CheckBox;

import java.util.HashMap;
import java.util.Map;


public class LoginPage extends ParentPage {

    @FindBy(xpath = ".//nav[@class='navbar']")
    private TextBlock headerPanel;

    @FindBy(xpath = ".//div[@data-kind='full_course_lists']//h1[text()='Онлайн-курсы']")
    private TextBlock infoPanel;

    @FindBy(xpath = ".//button[@class='navbar__submenu-toggler st-button_style_none']")
    private Button buttonChangeLanguage;

    @FindBy(xpath = ".//button[text()='English']")
    private Button buttonEnglishLanguage;

    @FindBy(xpath = ".//a[@href='/catalog?auth=login']")
    private Button buttonToProceedLogin;

    @FindBy(xpath = ".//a[@href='/catalog?auth=registration']")
    private Button buttonToProceedRegister;

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

    @FindBy(xpath = ".//button[@aria-label='Profile']")
    private Button buttonProfile;

    @FindBy(xpath = ".//ul[@class='menu menu_theme_popup-dark menu_right drop-down-content ember-view']")
    private Select dropDownProfile;

    @FindBy(xpath = ".//button[text()='Выход']")
    private Button buttonSignOut;

    @FindBy(xpath = ".//div[@data-theme='confirm']//div[@class='modal-popup__container']")
    private TextBlock signOutModalPopUp;

    @FindBy(xpath = ".//div[@data-theme='confirm']//button[text()='OK']")
    private TextBlock buttonSignOutConfirm;

    @FindBy(xpath = ".//form[@id='login_form']//li[text()='E-mail адрес и/или пароль не верны.']")
    private TextBlock invalidCredErrorMessage;

    @FindBy(xpath = ".//input[@class='search-form__input ']")
    private TextInput inputSearchForm;

    @FindBy(xpath = ".//button[@class='button_with-loader search-form__submit']")
    private Button buttonSearch;

    @FindBy(xpath = ".//div[@class='search-form__input-wrapper']//div[@class='drop-down__body']")
    private Select dropDownSearchInput;

    @FindBy(xpath = ".//div[@class='search-form__form']//span[text()='Бесплатные']")
    private CheckBox checkBoxFreeCourse;

    @FindBy(xpath = ".//footer[@class='page-footer page-footer-modern ember-view page_footer']")
    private TextBlock footer;

    private String specificCourseLocator = ".//div[@data-list-type='search-results']//a[text()='%s']";

    private String listOfCoursesInSearchResultLocator =
            ".//div[@data-list-type='search-results']//a[@class='course-card__title']";

    public LoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    String getRelativeUrl() {
        return "/catalog";
    }


    TestData testData = new TestData();

    public void openLoginPage() {
        try {
            webDriver.get("https://stepik.org/catalog");
            logger.info("Landing page was opened");
        } catch (Exception e) {
            logger.error("Cannot work with LandingPage" + e);
            Assert.fail("Cannot work with LandingPage");
        }
    }

    public void clickOnButtonToProceedLogIn() {
        clickOnElement(buttonToProceedLogin);
    }

    public void enterEmailInLogIn(String email) {
        enterTextToElement(inputEmail, email);
    }

    public void enterPasswordInLogIn(String password) {
        enterTextToElement(inputPassword, password);
    }

    public void clickOnButtonLogIn() {
        clickOnElement(buttonLogin);
    }

    public LoginPage fillLoginFormAndSubmit(String email, String password) {
        openLoginPage();
        clickOnButtonToProceedLogIn();
        enterEmailInLogIn(email);
        enterPasswordInLogIn(password);
        clickOnButtonLogIn();
        return this;
    }

    public LoginPage checkIsLoginButtonPresent() {
        Assert.assertTrue(isElementPresent(buttonLogin));
        return this;
    }

    public LoginPage checkIsInvalidCredErrorMessagePresent() {
        Assert.assertTrue(isElementPresent(invalidCredErrorMessage));
        return this;
    }

    public void checkIsButtonToRegisterPresent() {
        Assert.assertTrue(isElementPresent(buttonRegister));
    }

    public void clickOnButtonToProceedRegister() {
        clickOnElement(buttonToProceedRegister);
    }

    public void enterFullNameInRegistrationForm(String fullName) {
        enterTextToElement(inputFullNameRegistrationForm, fullName);
    }

    public void enterEmailInRegistrationForm(String email) {
        enterTextToElement(inputEmailRegistrationForm, email);
    }

    public void enterPasswordInRegistrationForm(String password) {
        enterTextToElement(inputPasswordRegistrationForm, password);
    }

    public void clickOnButtonRegister() {
        clickOnElement(buttonRegister);
    }

    public void fillRegistrationFormAndSubmit(String fullName, String email, String password) {
        openLoginPage();
        clickOnButtonToProceedRegister();
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
        new HomePage(webDriver).clickOnSignOutButton();
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
        new HomePage(webDriver).clickOnSignOutButton();
        return newUserCredentials;
    }

    public HomePage loginWithValidCred(String email, String password) {
        fillLoginFormAndSubmit(email, password);
        return new HomePage(webDriver);
    }


    public LoginPage checkValidationEmptyEmailErrorMessage() {
        Assert.assertEquals("Заполните это поле.", webDriver.findElement(By.name("login")).getAttribute("validationMessage"));
        return this;
    }

    public LoginPage checkValidationEmptyPasswordErrorMessage() {
        Assert.assertEquals("Заполните это поле.", webDriver.findElement(By.name("password")).getAttribute("validationMessage"));
        return this;
    }

    public void enterTheCourseName(String courseNameToSearch) {
        enterTextToElement(inputSearchForm, courseNameToSearch);
    }

    public void clickOnSearchButton() {
        clickOnElement(buttonSearch);
    }

    public CoursePage searchAndJoinUniqueExistentFreeCourseByUnauthorisedUser(String specificCourseTitle) {
        openLoginPage();
        enterTheCourseName(specificCourseTitle);
        closeDropDownSearchBodyIfIsDisplayed();
        clickOnElement(checkBoxFreeCourse);
        clickOnSearchButton();
        webDriverWait10.until(ExpectedConditions.urlContains("search"));
        WebElement specificCourseLink = scrollToCourseWithSpecificTitleInResults(
                specificCourseLocator, listOfCoursesInSearchResultLocator, specificCourseTitle);
        clickOnElement(specificCourseLink, "specificCourseLink");
        webDriverWait10.until(ExpectedConditions.urlContains("course"));
        return new CoursePage(webDriver);
    }


    private LoginPage closeDropDownSearchBodyIfIsDisplayed() {
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