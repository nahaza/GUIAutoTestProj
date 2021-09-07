package courseTest;

import baseTest.BaseTest;
import junitparams.JUnitParamsRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.io.IOException;
import java.util.Map;


@RunWith(JUnitParamsRunner.class)
public class FinishCourse extends BaseTest {
    private String specificCourseTitle = "АА - Активный Английский от Екатерины Зак (для начинающих А0-А1)";

    private String email;
    private String password;

    @Before
    public void generateCredentials() {
        Map<String, String> newUserCredentials = loginPage.generateCredentialsOfUserWithJoinedFreeCourse(specificCourseTitle);
        email = newUserCredentials.get("Email");
        password = newUserCredentials.get("Password");
    }

    @Test
    public void TC_8_finishNoExamCourseNotDoingTests() throws InterruptedException {
        loginPage.loginWithValidCred(email, password)
                .checkIsRedirectToHomePage();
        headerMenu.clickOnMyCourseButton();
        homePage.clickOnTheCourseInTheListCoursesJoinedPreviously(specificCourseTitle)
                .checkIsRedirectToCoursePage()
                .clickOnButtonContinueCourseOnTheCoursePage()
                .checkIsRedirectToLessonPage()
                .finishNoExamCourseWithoutDoingTests();
        headerMenu.clickOnProfileAndSignOutButton();
        loginPage.checkIsLoginButtonPresent();


    }


    @Test
    public void TC_9_finishNoExamCourseDoingTests() throws IOException, InterruptedException {
        loginPage.loginWithValidCred(email, password);
        headerMenu.clickOnMyCourseButton();
        homePage.clickOnTheCourseInTheListCoursesJoinedPreviously(specificCourseTitle)
                .clickOnButtonContinueCourseOnTheCoursePage()
                .finishNoExamCourseWithDoingTests(specificCourseTitle);
        headerMenu.clickOnProfileAndSignOutButton();
        loginPage.checkIsLoginButtonPresent();
    }
}
