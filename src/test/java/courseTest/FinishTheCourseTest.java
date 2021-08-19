package courseTest;

import baseTest.BaseTest;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(JUnitParamsRunner.class)
public class FinishTheCourseTest extends BaseTest {

    @Test
    @Parameters({
            "veraexpert209+1523301598@gmail.com,1"
    })
    //the user has joined specific course previously
    public void finishCourse(String email, String password) throws InterruptedException {
        landingPage.loginWithValidCred(email, password)
                .checkIsRedirectToHomePage()
                .clickOnMyCourseButton()
                .clickOnTheCourseInTheListCoursesJoinedPreviously("АА - Активный Английский от Екатерины Зак (для начинающих А0-А1)")
                .checkIsRedirectToCoursePage()
                .clickOnButtonContinueCourseOnTheCoursePage()
                .checkIsRedirectToLessonPage()
        .finishNoExamCourseWithoutDoingTests();


    }
}
