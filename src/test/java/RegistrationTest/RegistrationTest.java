package registrationTest;

import baseTest.BaseTest;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class RegistrationTest extends BaseTest {


    @Test
    public void newUserRegistrationSuccessful() {
        landingPage.newUserRegisterSuccessful()
        .checkIsRedirectToHomePage();
    }

    @Test
    @Parameters({
            "1a, 1@, 1",
            "1 a, 1 @, 1",
            "1ag,1@., 1",
            "Vera, ddf@drf., 1",
            ",,"

    })
    public void newUserRegistrationFailed(String fullname, String email, String password){
        landingPage.fillRegistrationFormAndSubmit(fullname, email, password);
        landingPage.checkIsButtonToRegisterPresent();

    }
}
