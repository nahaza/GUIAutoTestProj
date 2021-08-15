package pages;

import libs.TestData;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.TextBlock;
import ru.yandex.qatools.htmlelements.element.TextInput;
import ru.yandex.qatools.htmlelements.element.Select;
import ru.yandex.qatools.htmlelements.element.CheckBox;



public class LandingPage extends ParentPage {

    @FindBy(xpath = ".//nav[@aria-label='Общая навигация по сайту']")
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

    @FindBy(xpath = ".//form[@id='login_form']//button[text()='Войти']")
    private Button buttonLogin;

    @FindBy(xpath = ".//input[@id='id_registration_full-name']")
    private TextInput inputFullNameRegistrationForm;

    @FindBy(xpath = ".//input[@id='id_registration_email']")
    private TextInput inputEmailRegistrationForm;

    @FindBy(xpath = ".//input[@id='id_registration_password']")
    private TextInput inputPasswordRegistrationForm;

    @FindBy(xpath = ".//form[@id='registration_form']//button[text()='Регистрация']")
    private Button buttonRegister;

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

    public LandingPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    String getRelativeUrl() {
        return "/catalog";
    }


    Actions actions = new Actions(webDriver);
    TestData testData = new TestData();

    public void openLandingPage() {
        try {
            webDriver.get("https://stepik.org/catalog");
            logger.info("Landing page was opened");
        } catch (Exception e) {
            logger.error("Cannot work with LandingPage" + e);
            Assert.fail("Cannot work with LandingPage");
        }
    }

    public void clickOnButtonToChangeLanguage() {
        clickOnElement(buttonChangeLanguage);
    }

    public void clickOnButtonToSwitchToEnglish() {
        clickOnElement(buttonEnglishLanguage);
    }

    public void checkIsButtonToLogInPresent() {
        Assert.assertTrue(isElementPresent(buttonLogin));
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

    public LandingPage fillLoginFormAndSubmit(String email, String password) {
        openLandingPage();
        clickOnButtonToProceedLogIn();
        enterEmailInLogIn(email);
        enterPasswordInLogIn(password);
        clickOnButtonLogIn();
        return this;
    }


    public LandingPage checkIsLoginButtonPresent() {
        Assert.assertTrue(isElementPresent(buttonLogin));
        return this;
    }

    public LandingPage checkIsInvalidCredErrorMessagePresent() {
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
        openLandingPage();
        clickOnButtonToProceedRegister();
        enterFullNameInRegistrationForm(fullName);
        enterEmailInRegistrationForm(email);
        enterPasswordInRegistrationForm(password);
        clickOnButtonRegister();
    }

    public HomePage newUserRegisterSuccessful() {
        fillRegistrationFormAndSubmit(testData.getFullNameToRegister(), testData.getEmailToRegister(), testData.getValidPassword());
        return new HomePage(webDriver);
    }

    public LandingPage checkIsRegisterButtonPresent() {
        Assert.assertTrue(isElementPresent(buttonRegister));
        return this;
    }

    public HomePage loginWithValidCred() {
        fillLoginFormAndSubmit(testData.getValidEmail(), testData.getValidPassword());
        return new HomePage(webDriver);
    }


    public LandingPage checkValidationEmptyEmailErrorMessage() {
        Assert.assertEquals("Заполните это поле.", webDriver.findElement(By.name("login")).getAttribute("validationMessage"));
        return this;
    }

    public LandingPage checkValidationEmptyPasswordErrorMessage() {
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
        openLandingPage();
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


    private LandingPage closeDropDownSearchBodyIfIsDisplayed() {
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