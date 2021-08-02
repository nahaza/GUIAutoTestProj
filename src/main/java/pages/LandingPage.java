package pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LandingPage extends ParentPage{

    @FindBy(xpath = ".//a[text()='Log in']")
    private WebElement buttonToProceedLogin;

    @FindBy(xpath = ".//a[text()='Register']")
    private WebElement buttonToProceedRegister;

    @FindBy(xpath = ".//input[@id='id_login_email']")
    private WebElement inputEmail;

    @FindBy(xpath = ".//input[@id='id_login_password']")
    private WebElement inputPassword;

    @FindBy(xpath = ".//button[text()='Log in']")
    private WebElement buttonLogin;

    @FindBy(xpath = ".//input[@id='id_registration_full-name']")
    private WebElement inputFullNameRegistrationForm;

    @FindBy(xpath = ".//input[@id='id_registration_email']")
    private WebElement inputEmailRegistrationForm;

    @FindBy(xpath = ".//input[@id='id_registration_password']")
    private WebElement inputPasswordRegistrationForm;

    @FindBy(xpath = ".//button[text()='Register']")
    private WebElement buttonRegister;

    public LandingPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void openLandingPage(){
        try {
            webDriver.get("https://stepik.org/catalog");
            logger.info("Landing page was opened");
        }catch (Exception e){
            logger.error("Cannot work with LandingPage" + e);
            Assert.fail("Cannot work with LandingPage");
        }
    }

    public void clickOnButtonToProceedLogIn(){
        clickOnElement(buttonToProceedLogin);
    }

    public void enterEmailInLogIn(String email){
        enterTextToElement(inputEmail, email);
    }

    public void enterPasswordInLogIn(String password){
        enterTextToElement(inputPassword, password);
    }

    public void clickOnButtonLogIn(){
        clickOnElement(buttonLogin);
    }

    public void fillLoginFormAndSubmit(String email, String password){
        openLandingPage();
        clickOnButtonToProceedLogIn();
        enterEmailInLogIn(email);
        enterPasswordInLogIn(password);
        clickOnButtonLogIn();
    }

    public void clickOnButtonToProceedRegister(){
        clickOnElement(buttonToProceedRegister);
    }

    public void enterFullNameInRegistrationForm(String fullName){
        enterTextToElement(inputFullNameRegistrationForm, fullName);
    }

    public void enterEmailInRegistrationForm(String email){
        enterTextToElement(inputEmailRegistrationForm, email);
    }

    public void enterPasswordInRegistrationForm(String password){
        enterTextToElement(inputPasswordRegistrationForm, password);
    }

    public void clickOnButtonRegister(){
        clickOnElement(buttonRegister);
    }

    public void fillRegistrationFormAndSubmit(String fullName, String email, String password){
        openLandingPage();
        clickOnButtonToProceedRegister();
        enterFullNameInRegistrationForm(fullName);
        enterEmailInRegistrationForm(email);
        enterPasswordInRegistrationForm(password);
        clickOnButtonRegister();
    }

}
