package pages;

import libs.TestData;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;


public class LandingPage extends ParentPage {

    @FindBy(xpath = ".//button[@class='navbar__submenu-toggler st-button_style_none']")
    private WebElement buttonChangeLanguage;

    @FindBy(xpath = ".//button[text()='English']")
    private WebElement buttonEnglishLanguage;

    @FindBy(xpath = ".//a[@href='/catalog?auth=login']")
    private WebElement buttonToProceedLogin;

//    @FindBy(xpath = ".//a[text()='Register']")
//    private WebElement buttonToProceedRegister;

    @FindBy(xpath = ".//a[@href='/catalog?auth=registration']")
    private WebElement buttonToProceedRegister;

    @FindBy(xpath = ".//input[@id='id_login_email']")
    private WebElement inputEmail;

    @FindBy(xpath = ".//input[@id='id_login_password']")
    private WebElement inputPassword;

    @FindBy(xpath = ".//form[@id='login_form']//button[text()='Войти']")
    private WebElement buttonLogin;

    @FindBy(xpath = ".//input[@id='id_registration_full-name']")
    private WebElement inputFullNameRegistrationForm;

    @FindBy(xpath = ".//input[@id='id_registration_email']")
    private WebElement inputEmailRegistrationForm;

    @FindBy(xpath = ".//input[@id='id_registration_password']")
    private WebElement inputPasswordRegistrationForm;

    @FindBy(xpath = ".//form[@id='registration_form']//button[text()='Регистрация']")
    private WebElement buttonRegister;

    @FindBy(xpath = ".//form[@id='login_form']//li[text()='E-mail адрес и/или пароль не верны.']")
    private WebElement invalidCredErrorMessage;

    @FindBy(xpath = ".//input[@class='search-form__input ']")
    private WebElement inputSearchForm;

    @FindBy(xpath = ".//button[@class='button_with-loader search-form__submit']")
    private WebElement buttonSearch;

    @FindBy(xpath = ".//div[@data-list-type='search-results']//a[@class='course-card__title']")
    private List<WebElement> listOfCoursesInSearchResult;

    @FindBy(xpath = ".//aside[@class='course-promo__main-aside']//button[text()='Поступить на курс']")
    private WebElement buttonJoinTheCourse;

    @FindBy(xpath = ".//div[@class='auth-widget sign-form sign-form__shaking ember-view']")
    private WebElement registerModalWindow;

    public LandingPage(WebDriver webDriver) {
        super(webDriver);
    }

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

    public LandingPage checkIsRegisterButtonPresent() {
        Assert.assertTrue(isElementPresent(buttonRegister));
        return this;
    }

    public HomePage loginWithValidCred() {
        fillLoginFormAndSubmit(TestData.getValidEmail(), TestData.getValidPassword());
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

    public void enterTheCourseName(String courseNameToSearch){
        enterTextToElement(inputSearchForm,courseNameToSearch);
    }

    public void clickOnSearchButton(){
        clickOnElement(buttonSearch);
    }

    public void clickOnButtonJoinTheCourse(){
        clickOnElement(buttonJoinTheCourse);
    }


    public  LandingPage searchAndJoinUniqueExistentFreeCourseByUnauthorisedUser(String specificCourseTitle){
        openLandingPage();
        enterTheCourseName(specificCourseTitle);
        clickOnSearchButton();
        for (WebElement courseTitleLink:listOfCoursesInSearchResult) {
            if(courseTitleLink.getText().equals(specificCourseTitle)){
                clickOnElement(courseTitleLink);
            } else {
                logger.error("The course not found");
            }
        }
        clickOnButtonJoinTheCourse();
        new WebDriverWait(webDriver, 10).until(ExpectedConditions.visibilityOf(registerModalWindow));
        return this;
    }
}
