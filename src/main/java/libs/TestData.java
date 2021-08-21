package libs;

import java.util.Random;

public class TestData {

    public String getFullNameToRegister() {
        return generateFullNameToRegister();
    }

    public String getEmailToRegister() {
        return generateEmailToRegister();
    }

    public String getPasswordToRegister() {
        return generatePasswordToRegister();
    }

    private int generateRandomInt() {
        return new Random().nextInt(100000);
    }

    private String generateFullNameToRegister() {
        return "Kesha" + generateRandomInt() + "@gmail.com";
    }

    private String generateEmailToRegister() {
        return "veraexpert209+" + generateRandomInt() + "@gmail.com";
    }

    private String generatePasswordToRegister() {
        return "KLS" + generateRandomInt();
    }
}
