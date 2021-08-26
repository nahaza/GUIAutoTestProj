package courseTest;

import baseTest.BaseTest;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import libs.ExcelDriver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import pages.ParentPage;


import java.io.IOException;
import java.util.Map;


@RunWith(JUnitParamsRunner.class)
public class finishCourseNoDoingTestsNoExamTest extends BaseTest {
    private String specificCourseTitle = "АА - Активный Английский от Екатерины Зак (для начинающих А0-А1)";
//
//    private String email;
//    private String password;
//
//    @Before
//    public void generateCredentials() {
//        Map<String, String> newUserCredentials = loginPage.generateCredentialsOfUserWithJoinedFreeCourse(specificCourseTitle);
//        email = newUserCredentials.get("Email");
//        password = newUserCredentials.get("Password");
//    }
//
//    @Test
//    public void finishNoExamCourseNotDoingTests() throws InterruptedException {
//        loginPage.loginWithValidCred(email, password)
//                .checkIsRedirectToHomePage()
//                .clickOnMyCourseButton()
//                .clickOnTheCourseInTheListCoursesJoinedPreviously(specificCourseTitle)
//                .checkIsRedirectToCoursePage()
//                .clickOnButtonContinueCourseOnTheCoursePage()
//                .checkIsRedirectToLessonPage()
//                .finishNoExamCourseWithoutDoingTests();
//
//
//    }

    @Test
    @Parameters({
            "veraexpert209+53144@gmail.com, KLS29449"
    })
    public void finish1LessonDragAndDrop(String email, String password) throws IOException {
        Map<String, String > dataForTests = ExcelDriver.getData(ParentPage.configProperties.DATA_FILE(), "lesson1_step2");
        loginPage.loginWithValidCred(email, password)
                .clickOnMyCourseButton()
                .clickOnTheCourseInTheListCoursesJoinedPreviously(specificCourseTitle)
                .clickOnButtonContinueCourseOnTheCoursePage()
                .clickOnStep2()
                .doTheTestDragAndDrop();
    }
}
