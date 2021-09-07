package pageWithElements;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.Select;
import ru.yandex.qatools.htmlelements.element.TextBlock;

@FindBy(xpath = "main-header no-print")
public class HeaderMenu extends ParentElements {

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
        actionsWithElements.clickOnElement(buttonToProceedLogin);
    }

    public void clickOnButtonToProceedRegister() {
        actionsWithElements.clickOnElement(buttonToProceedRegister);
    }

    public boolean isSignOutButtonPresent() {
        actionsWithElements.clickOnElement(buttonProfile);
        return actionsWithElements.isElementPresent(buttonSignOut);
    }

    public void checkIsSignOutButtonPresent() {
        actionsWithElements.clickOnElement(buttonProfile);
        actionsWithElements.webDriverWait15.until(ExpectedConditions.visibilityOf(dropDownProfile));
        Assert.assertTrue("SignOut is not present in the Profile menu", actionsWithElements.isElementPresent(buttonSignOut));
    }

    public void clickOnProfileAndSignOutButton() {
        actionsWithElements.clickOnElement(buttonProfile);
        actionsWithElements.webDriverWait15.until(ExpectedConditions.visibilityOf(dropDownProfile));
        actionsWithElements.clickOnElement(buttonSignOut);
        actionsWithElements.webDriverWait15.until(ExpectedConditions.visibilityOf(signOutModalPopUp));
        actionsWithElements.clickOnElement(buttonSignOutConfirm);
    }

    public void clickOnSignOutButton() {
        actionsWithElements.clickOnElement(buttonSignOut);
        actionsWithElements.webDriverWait15.until(ExpectedConditions.visibilityOf(signOutModalPopUp));
        actionsWithElements.clickOnElement(buttonSignOutConfirm);
    }


    public void clickOnMyCourseButton() {
        actionsWithElements.clickOnElement(buttonMyCourses);
        actionsWithElements.webDriverWait15.until(ExpectedConditions.urlContains("users"));
    }

}