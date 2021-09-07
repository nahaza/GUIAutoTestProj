package loginTest;

import baseTest.BaseTest;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class InvalidLoginTest extends BaseTest {
    @Test
    @Parameters({
            "veraexpert209+1195675139@gmail.com,2"
            , "1veraexpert209+1195675139@gmail.com,1"
            , "1veraexpert209+1195675139@gmail.com,2"
    })
    @TestCaseName("doLoginWithInvalidCred: email={0}, password={1}")
    public void TC_4_doLoginWithInvalidCred(String email, String password) {
        loginPage.fillLoginFormAndSubmit(email, password)
                .checkIsLoginButtonPresent()
                .checkIsInvalidCredErrorMessagePresent();
    }

    @Test
    @Parameters({
            ","
    })
    public void TC_5_doLoginWithEmptyCred(String email, String password) {
        loginPage.fillLoginFormAndSubmit(email, password)
                .checkIsLoginButtonPresent();
    }

    @Test
    public void TC_5_checkValidationLoginErrorMessages() {
        loginPage.fillLoginFormAndSubmit("", "");
        loginPage.checkValidationEmptyEmailErrorMessage();
        loginPage.enterEmailInLogIn("veraexpert209+1195675139@gmail.coml");
        loginPage.clickOnButtonLogIn();
        loginPage.checkValidationEmptyPasswordErrorMessage();
        loginPage.checkIsLoginButtonPresent();
    }

}
