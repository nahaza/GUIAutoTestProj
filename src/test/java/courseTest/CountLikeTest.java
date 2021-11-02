package courseTest;

import baseTest.BaseTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class CountLikeTest extends BaseTest {
    private String specificCourseTitle = "АА - Активный Английский от Екатерины Зак (для начинающих А0-А1)";
    private String email;
    private String password;
    private Integer lessonNumber = 2;

    @Before
    public void generateCredentials() {
        Map<String, String> newUserCredentials = loginPage.generateCredentialsOfUserWithJoinedFreeCourse(specificCourseTitle);
        email = newUserCredentials.get("Email");
        password = newUserCredentials.get("Password");
    }

    @Test
    public void CountLikes() throws InterruptedException {
        loginPage.loginWithValidCred(email, password);
        headerMenu.clickOnMyCourseButton();
        homePage.clickOnTheCourseInTheListCoursesJoinedPreviously(specificCourseTitle)
                .clickOnButtonContinueCourseOnTheCoursePage()
                .likeLesson(specificCourseTitle, lessonNumber);
        headerMenu.clickOnProfileAndSignOutButton();
        loginPage.checkIsLoginButtonPresent();
    }
}
