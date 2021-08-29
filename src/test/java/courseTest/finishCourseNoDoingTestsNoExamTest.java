package courseTest;

import baseTest.BaseTest;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;



import java.io.IOException;
import java.util.Map;


@RunWith(JUnitParamsRunner.class)
public class finishCourseNoDoingTestsNoExamTest extends BaseTest {
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
    public void finishNoExamCourseNotDoingTests() throws InterruptedException {
        loginPage.loginWithValidCred(email, password)
                .checkIsRedirectToHomePage()
                .clickOnMyCourseButton()
                .clickOnTheCourseInTheListCoursesJoinedPreviously(specificCourseTitle)
                .checkIsRedirectToCoursePage()
                .clickOnButtonContinueCourseOnTheCoursePage()
                .checkIsRedirectToLessonPage()
                .finishNoExamCourseWithoutDoingTests();


    }


    @Test
    @Parameters({
            "veraexpert209+53144@gmail.com, KLS29449"
    })
    //test finishCourse with method finish1LessonDragAndDrop was deleted. This test is not finished completely, but works properly
    public void finishNoExamCourseDoingTests(String email, String password) throws IOException, InterruptedException {
        loginPage.loginWithValidCred(email, password)
                .clickOnMyCourseButton()
                .clickOnTheCourseInTheListCoursesJoinedPreviously(specificCourseTitle)
                .clickOnButtonContinueCourseOnTheCoursePage()
                .finishNoExamCourseWithDoingTests(specificCourseTitle);
    }
}
