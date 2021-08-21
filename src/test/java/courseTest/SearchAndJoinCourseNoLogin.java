package courseTest;

import baseTest.BaseTest;
import org.junit.Test;


public class SearchAndJoinCourseNoLogin extends BaseTest {
    @Test
    public void searchAndJoinUniqueExistentFreeCourseByUnauthorisedUser() {
        loginPage.searchAndJoinUniqueExistentFreeCourseByUnauthorisedUser("C#")
                .checkIsCourseFreeInfoPresent()
                .clickOnButtonJoinTheCourse()
                .checkIsButtonRegisterPresent();
    }
}
