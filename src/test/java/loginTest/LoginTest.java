package loginTest;

import baseTest.BaseTest;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import pages.HomePage;

@RunWith(JUnitParamsRunner.class)
public class LoginTest extends BaseTest {
    @Test
    @Parameters({
            "veraexpert209+1195675139@gmail.com,1"
    })
    @TestCaseName("doLoginWithValidCred")
    public void doLoginWithValidCred(String email, String password) {
        landingPage.loginWithValidCred()
                .checkIsSignOutButtonPresent();
    }
}
