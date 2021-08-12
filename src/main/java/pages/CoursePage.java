package pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CoursePage extends ParentPage {

    @FindBy(xpath = ".//aside[@class='course-promo__main-aside']//button[@class='button button_with-loader course-promo-enrollment__join-btn']")
    private WebElement buttonJoinTheCourse;

    @FindBy(xpath = ".//aside[@class='course-promo__main-aside']//span[@class='format-price format-price_free']")
    private WebElement infoCourseIsFree;

    @FindBy(xpath = ".//form[@id='registration_form']//button[text()='Регистрация']")
    private WebElement buttonRegister;

    public CoursePage(WebDriver webDriver) {
        super(webDriver);
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

    public CoursePage checkIsButtonRegisterPresent() {
        Assert.assertTrue("Register button is not displayed", buttonRegister.isDisplayed());
        return this;
    }
}
