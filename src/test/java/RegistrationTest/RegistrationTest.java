package registrationTest;

import baseTest.BaseTest;
import libs.TestData;
import org.junit.Test;

public class RegistrationTest extends BaseTest {
    TestData testData = new TestData();
    String emailToRegister = testData.getEmailToRegister();
    String fullNameToRegister = testData.getFullNameToRegister();

    @Test
    public void registerNewUser() {
        landingPage.fillRegistrationFormAndSubmit(fullNameToRegister, emailToRegister, TestData.getValidPassword());
    }
}
