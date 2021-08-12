package pages;

import libs.TestData;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


import java.util.List;


public class LandingPage extends ParentPage {

    @FindBy(xpath = ".//nav[@aria-label='Общая навигация по сайту']")
    private WebElement headerPanel;

    @FindBy(xpath = ".//div[@data-kind='full_course_lists']//h1[text()='Онлайн-курсы']")
    private WebElement infoPanel;

    @FindBy(xpath = ".//button[@class='navbar__submenu-toggler st-button_style_none']")
    private WebElement buttonChangeLanguage;

    @FindBy(xpath = ".//button[text()='English']")
    private WebElement buttonEnglishLanguage;

    @FindBy(xpath = ".//a[@href='/catalog?auth=login']")
    private WebElement buttonToProceedLogin;

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

    @FindBy(xpath = ".//div[@class='search-form__input-wrapper']//div[@class='drop-down__body']")
    private WebElement dropDownSearchInput;

    @FindBy(xpath = ".//div[@class='search-form__form']//span[text()='Бесплатные']")
    private WebElement checkBoxFreeCourse;

    @FindBy(xpath = ".//footer[@class='page-footer page-footer-modern ember-view page_footer']")
    private WebElement footer;

    private String specificCourseLocator = ".//div[@data-list-type='search-results']//a[text()='%s']";

    private String listOfCoursesInSearchResultLocator =
            ".//div[@data-list-type='search-results']//a[@class='course-card__title']";

    public LandingPage(WebDriver webDriver) {
        super(webDriver);
    }

    Actions actions = new Actions(webDriver);

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
        scrollToCourseWithSpecificTitleInResults(specificCourseTitle);
        clickOnElement(webDriver.findElement(By.xpath(String.format(
                specificCourseLocator, specificCourseTitle))));
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


    public LandingPage scrollToCourseWithSpecificTitleInResults(String specificCourseTitle) {
        try {
            while (!webDriver.findElement(By.xpath(String.format(
                    specificCourseLocator, specificCourseTitle))).isDisplayed()) {
                List<WebElement> listOfCoursesInSearchResult = webDriver.findElements(By.xpath(
                        listOfCoursesInSearchResultLocator));
                System.out.println("Results count: " + listOfCoursesInSearchResult.size());
                JavascriptExecutor jse = (JavascriptExecutor) webDriver;
                jse.executeScript("arguments[0].scrollIntoView();"
                        , listOfCoursesInSearchResult.get(listOfCoursesInSearchResult.size() - 1));
                webDriverWait10.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(
                        listOfCoursesInSearchResultLocator), listOfCoursesInSearchResult.size()));
            }
        } catch (Exception e) {
            logger.error("The course with the title " + specificCourseTitle + " was not found");
            Assert.fail("The course with the title " + specificCourseTitle + " was not found");
        }
        return this;
    }
}