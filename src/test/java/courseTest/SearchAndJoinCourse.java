package courseTest;

import baseTest.BaseTest;
import org.junit.Test;

public class SearchAndJoinCourse extends BaseTest {

    @Test
    public void searchAndJoinUniqueExistentFreeCourseByUnauthorisedUser(){
        landingPage.searchAndJoinUniqueExistentFreeCourseByUnauthorisedUser("Bioinformatics Contest 2021")
                .checkIsRegisterButtonPresent();
    }
}
