package courseTest;

import baseTest.BaseTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class CountTestingScoreTest extends BaseTest {
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
    @Ignore
    public void CountTestingScoreTest() throws IOException, InterruptedException {
        loginPage.loginWithValidCred(email, password);
        headerMenu.clickOnMyCourseButton();
        homePage.clickOnTheCourseInTheListCoursesJoinedPreviously(specificCourseTitle)
                .clickOnButtonContinueCourseOnTheCoursePage()
                .doTestsForScoreCounting(specificCourseTitle);
        headerMenu.clickOnProfileAndSignOutButton();
        loginPage.checkIsLoginButtonPresent();
    }
}
