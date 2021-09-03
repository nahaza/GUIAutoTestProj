package pageWithElements;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.Select;
import ru.yandex.qatools.htmlelements.element.TextBlock;

@FindBy(xpath = "main-header no-print")
public class HeaderMenu extends ParentElements{

    @FindBy(xpath = ".//header//a[text()='Мои курсы']")
    public Button buttonMyCourses;

    @FindBy(xpath = ".//button[@aria-label='Profile']")
    public Button buttonProfile;

    @FindBy(xpath = ".//ul[@class='menu menu_theme_popup-dark menu_right drop-down-content ember-view']")
    public Select dropDownProfile;

    @FindBy(xpath = ".//li[@class='menu-item'][7]//button")
    public Button buttonSignOut;

    @FindBy(xpath = ".//div[@data-theme='confirm']//div[@class='modal-popup__container']")
    public TextBlock signOutModalPopUp;

    @FindBy(xpath = ".//div[@data-theme='confirm']//button[text()='OK']")
    public TextBlock buttonSignOutConfirm;

    @FindBy(xpath = ".//form[@id='registration_form']//button[@class='sign-form__btn button_with-loader ']")
    public Button buttonRegister;

    @FindBy(xpath = ".//form[@id='login_form']//button[@class='sign-form__btn button_with-loader ']")
    public Button buttonLogin;

    @FindBy(xpath = ".//nav[@aria-label='Общая навигация по сайту']")
    public TextBlock headerPanel;

    @FindBy(xpath = ".//footer[@class='page-footer page-footer-modern ember-view page_footer']")
    public TextBlock footer;

    @FindBy(xpath = ".//a[@href='/catalog?auth=login']")
    public Button buttonToProceedLogin;

    @FindBy(xpath = ".//a[@href='/catalog?auth=registration']")
    public Button buttonToProceedRegister;

    public HeaderMenu(WebDriver webDriver) {
        super(webDriver);
    }

    public void clickOnButtonToProceedLogIn() {
        clickOnElement(buttonToProceedLogin);
    }

    public void clickOnButtonToProceedRegister() {
        clickOnElement(buttonToProceedRegister);
    }

    public boolean isSignOutButtonPresent() {
        clickOnElement(buttonProfile);
        return isElementPresent(buttonSignOut);
    }

    public void checkIsSignOutButtonPresent() {
        clickOnElement(buttonProfile);
        webDriverWait10.until(ExpectedConditions.visibilityOf(dropDownProfile));
        Assert.assertTrue("SignOut is not present in the Profile menu", isElementPresent(buttonSignOut));
    }

    public void clickOnSignOutButton() {
        clickOnElement(buttonProfile);
        webDriverWait10.until(ExpectedConditions.visibilityOf(dropDownProfile));
        clickOnElement(buttonSignOut);
        webDriverWait10.until(ExpectedConditions.visibilityOf(signOutModalPopUp));
        clickOnElement(buttonSignOutConfirm);
        Assert.assertTrue("Register button is not present in the Profile menu"
                , isElementPresent(buttonRegister));
    }

    public void clickOnSignOutButtonAfterLogin() {
        clickOnElement(buttonSignOut);
        webDriverWait10.until(ExpectedConditions.visibilityOf(signOutModalPopUp));
        clickOnElement(buttonSignOutConfirm);
        Assert.assertTrue("Login button is not present in the Profile menu"
                , isElementPresent(buttonLogin));
    }

    public void clickOnSignOutButtonAfterRegister() {
        clickOnElement(buttonSignOut);
        webDriverWait10.until(ExpectedConditions.visibilityOf(signOutModalPopUp));
        clickOnElement(buttonSignOutConfirm);
        Assert.assertTrue("Register button is not present in the Profile menu"
                , isElementPresent(buttonRegister));
    }

    public void clickOnSignOutButtonAfterJoinCourse() {
        clickOnElement(buttonProfile);
        webDriverWait10.until(ExpectedConditions.visibilityOf(dropDownProfile));
        clickOnElement(buttonSignOut);
        webDriverWait10.until(ExpectedConditions.visibilityOf(signOutModalPopUp));
        clickOnElement(buttonSignOutConfirm);
        Assert.assertTrue("Login button is not present in the Profile menu"
                , isElementPresent(buttonLogin));
    }

    public void clickOnMyCourseButton() {
        clickOnElement(buttonMyCourses);
        webDriverWait10.until(ExpectedConditions.urlContains("users"));
    }

}