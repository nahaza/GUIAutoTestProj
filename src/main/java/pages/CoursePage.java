package pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.yandex.qatools.htmlelements.element.*;

public class CoursePage extends ParentPage {

    @FindBy(xpath = ".//aside[@class='course-promo__main-aside']//button[@class='button button_with-loader course-promo-enrollment__join-btn']")
    private Button buttonJoinTheCourse;

    @FindBy(xpath = ".//aside[@class='course-promo__main-aside']//span[@class='format-price format-price_free']")
    private TextBlock infoCourseIsFree;

    @FindBy(xpath = ".//form[@id='registration_form']//button[text()='Регистрация']")
    private Button buttonRegister;

    @FindBy(xpath = ".//button[@aria-label='Profile']")
    private Button buttonProfile;

    @FindBy(xpath = ".//ul[@class='menu menu_theme_popup-dark menu_right drop-down-content ember-view']")
    private Select dropDownProfile;

    @FindBy(xpath = ".//button[text()='Выход']")
    private Button buttonSignOut;

    @FindBy(xpath = ".//div[@class='course-join-button ember-view course-nav__course-join']//a")
    private Button buttonContinueCourseJoinedPreviously;


    public CoursePage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    String getRelativeUrl() {
        return "/course/";
    }

    public CoursePage checkIsRedirectToCoursePage() {
        checkUrlWithPattern();
        checkIsSignOutButtonPresent();
        return this;
    }

    public CoursePage checkIsButtonJoinCoursePresent() {
        Assert.assertTrue("Button joinTheCourse is not displayed", buttonJoinTheCourse.isDisplayed());
        return this;
    }

    public CoursePage checkIsCourseFreeInfoPresent() {
        Assert.assertTrue("Info course is free is not displayed", infoCourseIsFree.isDisplayed());
        return this;
    }


    public CoursePage clickOnButtonJoinTheCourse() {
        try {
            clickOnElement(buttonJoinTheCourse);
        } catch (Exception e) {
            logger.info("The button Join the course was not found. Check if the course is free");
            Assert.fail("The button Join the course was not found. Check if the course is free");
        }
        return this;
    }

    public LessonPage clickOnButtonJoinTheCourseLoggedInUser() {
        try {
            clickOnElement(buttonJoinTheCourse);
            webDriverWait10.until(ExpectedConditions.urlContains("lesson"));
        } catch (Exception e) {
            logger.info("The button Join the course was not found. Check if the course is free");
            Assert.fail("The button Join the course was not found. Check if the course is free");
        }
        return new LessonPage(webDriver);
    }

    public CoursePage checkIsButtonRegisterPresent() {
        Assert.assertTrue("Register button is not displayed", buttonRegister.isDisplayed());
        return this;
    }

    public CoursePage checkIsSignOutButtonPresent() {
        clickOnElement(buttonProfile);
        webDriverWait10.until(ExpectedConditions.visibilityOf(dropDownProfile));
        Assert.assertTrue("SignOut is not present in the Profile menu", isElementPresent(buttonSignOut));
        return this;
    }

    public LessonPage clickOnButtonContinueCourseOnTheCoursePage() {
        clickOnElement(buttonContinueCourseJoinedPreviously);
        webDriverWait10.until(ExpectedConditions.urlContains("lesson"));
        return new LessonPage(webDriver);
    }


}
