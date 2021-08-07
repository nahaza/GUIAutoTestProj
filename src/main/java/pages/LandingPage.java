package pages;

import libs.TestData;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    public void fillLoginFormAndSubmit(String email, String password) {
        openLandingPage();
        clickOnButtonToProceedLogIn();
        enterEmailInLogIn(email);
        enterPasswordInLogIn(password);
        clickOnButtonLogIn();
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

    public HomePage loginWithValidCred() {
        fillLoginFormAndSubmit(TestData.getValidEmail(), TestData.getValidPassword());
        return new HomePage(webDriver);
    }

}
