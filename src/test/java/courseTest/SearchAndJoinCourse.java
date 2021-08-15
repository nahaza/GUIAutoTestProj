package courseTest;

import baseTest.BaseTest;
import org.junit.Test;

public class SearchAndJoinCourse extends BaseTest {

    @Test
    public void searchAndJoinUniqueExistentFreeCourseByUnauthorisedUser() {
        landingPage.searchAndJoinUniqueExistentFreeCourseByUnauthorisedUser("C#")
                .checkIsCourseFreeInfoPresent()
                .clickOnButtonJoinTheCourse()
                .checkIsButtonRegisterPresent();
    }

    @Test
    public void searchAndJoinUniqueExistentFreeCourseByLoggedInUser() {
        landingPage.newUserRegisterSuccessful()
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
