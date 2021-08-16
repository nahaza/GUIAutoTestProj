package loginTest;

import baseTest.BaseTest;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class LoginTest extends BaseTest {
    @Test
    @Parameters({
            "veraexpert209+1195675139@gmail.com, 1"
    })
    @TestCaseName("doLoginWithValidCred")
    public void doLoginWithValidCred(String email, String password) {
        landingPage.loginWithValidCred(email, password)
                .checkIsSignOutButtonPresent();
    }

    @Test
    @Parameters({
            "veraexpert209+1195675139@gmail.com,2"
            , "1veraexpert209+1195675139@gmail.com,1"
            , "1veraexpert209+1195675139@gmail.com,2"
    })
    @TestCaseName("doLoginWithInvalidCred: email={0}, password={1}")
    public void doLoginWithInvalidCred(String email, String password) {
        landingPage.fillLoginFormAndSubmit(email, password)
                .checkIsLoginButtonPresent()
                .checkIsInvalidCredErrorMessagePresent();
    }

    @Test
    @Parameters({
            ","
    })
    public void doLoginWithEmptyCred(String email, String password) {
        landingPage.fillLoginFormAndSubmit(email, password)
                .checkIsLoginButtonPresent();
    }

    @Test
    public void checkValidationLoginErrorMessages() {
        landingPage.fillLoginFormAndSubmit("", "");
        landingPage.checkValidationEmptyEmailErrorMessage();
        landingPage.enterEmailInLogIn("veraexpert209+1195675139@gmail.coml");
        landingPage.clickOnButtonLogIn();
        landingPage.checkValidationEmptyPasswordErrorMessage();
        landingPage.checkIsLoginButtonPresent();
    }

}
