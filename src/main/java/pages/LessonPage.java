package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Button;

public class LessonPage extends ParentPage {

    @FindBy(xpath = ".//button[@aria-label='Profile']")
    private Button buttonProfile;

    @FindBy(xpath = ".//button[text()='Выход']")
    private Button buttonSignOut;

    private String courseNameLinkLocator = ".//a[@title='%s']";

    @FindBy(xpath = ".//button[@class='lesson__next-btn button has-icon']")
    private Button courseNextStep;

    @FindBy(xpath = ".//a[@class='ember-link ember-view attempt__wrapper_next-link button success']")
    private Button buttonNextStepAfterCorrectAnswerSubmission;

    @FindBy(xpath = ".//button[@class='submit-submission']")
    private Button submitAnswer;

    private String listOfCourseModulesLocator = ".//div[@class='lesson-sidebar__toc-inner']";

    private String listOfCourseLessonsLocator = ".//span[@class='lesson-sidebar__lesson-name']";

    private String listOfLessonStepsOnTopBarLocator = ".//div[@class='m-step-pin ember-view player__step-pin']";

    private String listOfLessonQuizesOnTopBarLocator = "svg-icon easy-quiz_icon ember-view step-pin-icon__icon";


    protected LessonPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    String getRelativeUrl() {
        return "/lesson";
    }

    public LessonPage checkIsSignOutButtonPresent() {
        clickOnElement(buttonProfile);
        Assert.assertTrue("SignOut is not present in the Profile menu", isElementPresent(buttonSignOut));
        return this;
    }


    public LessonPage checkIsRedirectToLessonPage() {
        checkUrlWithPattern();
        checkIsSignOutButtonPresent();
        return this;
    }

    public boolean isCourseNameLinkPresent(String courseName) {
        return isElementPresent(webDriver.findElement(By.xpath(String.format(
                courseNameLinkLocator, courseName))), "courseNameLink");
    }

    public LessonPage checkIsCourseNamePresent(String courseName) {
        Assert.assertTrue("CourseName is not present on the Page"
                , isCourseNameLinkPresent(courseName));
        return this;
    }

    public LessonPage checkIsCourseNextStepPresent() {
        Assert.assertTrue("CourseNextStep is not present on the Page"
                , isElementPresent(courseNextStep));
        return this;
    }

    public LessonPage continueAndFinishCourse() {
        //1lesson
        clickOnElement(courseNextStep);
        clickOnElement(submitAnswer);
        //checkCountScore:
        return this;
    }
}
