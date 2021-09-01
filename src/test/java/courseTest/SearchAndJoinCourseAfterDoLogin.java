package courseTest;

import baseTest.BaseTest;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class SearchAndJoinCourseAfterDoLogin extends BaseTest {
    String email;
    String password;
    String specificCourseTitle = "Программирование на Python";

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
                .searchAndJoinUniqueExistentFreeCourseByLoggedInUser(specificCourseTitle)
                .checkIsRedirectToCoursePage()
                .checkIsCourseFreeInfoPresent()
                .clickOnButtonJoinTheCourseLoggedInUser()
                .checkIsRedirectToLessonPage()
                .checkIsCourseNamePresent(specificCourseTitle)
                .checkIsCourseNextStepPresent()
                .clickOnSignOutButtonAfterJoinCourse();
    }
}
