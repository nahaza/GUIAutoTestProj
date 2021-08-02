package RegistrationTest;

import baseTest.BaseTest;
import libs.TestData;
import org.junit.Test;

public class RegistrationTest extends BaseTest {
    TestData testData = new TestData();
    String emailToRegister = testData.generateEmailToRegister();
    String fullNameToRegister = testData.generateFullNameToRegister();

    @Test
    public void registerNewUser(){
        landingPage.fillRegistrationFormAndSubmit(fullNameToRegister, emailToRegister, TestData.getValidPassword());
    }
}
