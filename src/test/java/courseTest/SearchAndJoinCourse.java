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
}
