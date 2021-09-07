package courseTest;

import baseTest.BaseTest;
import org.junit.Test;


public class SearchAndJoinCourseNoLogin extends BaseTest {
    @Test
    public void TC_6_searchAndJoinUniqueExistentFreeCourseByUnauthorisedUser() {
        loginPage.searchAndJoinUniqueExistentFreeCourseByUnauthorisedUser("C#")
                .checkIsCourseFreeInfoPresent()
                .clickOnButtonJoinTheCourse();
        loginPage.checkIsButtonToRegisterPresent();
    }
}
