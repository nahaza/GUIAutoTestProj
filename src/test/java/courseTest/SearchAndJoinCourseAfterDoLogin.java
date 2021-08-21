package courseTest;

import baseTest.BaseTest;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class SearchAndJoinCourseAfterDoLogin extends BaseTest {
    String email;
    String password;

    @Before
    public void generateCredentials() {
        Map<String, String> newUserCredentials = loginPage.generateRegisteredNewUserCredentials();
        email = newUserCredentials.get("Email");
        password = newUserCredentials.get("Password");
    }

    @Test
    public void searchAndJoinUniqueExistentFreeCourseByLoggedInUser() {
        loginPage.loginWithValidCred(email, password)
                .checkIsRedirectToHomePage()
                .searchAndJoinUniqueExistentFreeCourseByLoggedInUser("АА - Активный Английский от Екатерины Зак (для начинающих А0-А1)")
                .checkIsRedirectToCoursePage()
                .checkIsCourseFreeInfoPresent()
                .clickOnButtonJoinTheCourseLoggedInUser()
                .checkIsRedirectToLessonPage()
                .checkIsCourseNamePresent("АА - Активный Английский от Екатерины Зак (для начинающих А0-А1)")
                .checkIsCourseNextStepPresent();
    }
}
