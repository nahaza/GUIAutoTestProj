package loginTest;

import baseTest.BaseTest;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;


public class ValidLoginTest extends BaseTest {
    private String email;
    private String password;

    @Before
    public void generateCredentials() {
        Map<String, String> newUserCredentials = loginPage.generateRegisteredNewUserCredentials();
        email = newUserCredentials.get("Email");
        password = newUserCredentials.get("Password");
    }


    @Test
    public void doLoginWithValidCred() {
        loginPage.loginWithValidCred(email, password)
                .checkIsRedirectToHomePage();
    }
}