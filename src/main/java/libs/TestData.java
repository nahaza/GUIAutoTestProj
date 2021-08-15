package libs;

import java.util.Random;

public class TestData {

    private final static String VALID_EMAIL = "veraexpert209+1195675139@gmail.com";
    private final static String VALID_PASSWORD = "1";


    public String getEmailToRegister() {
        return generateEmailToRegister();
    }

    public String getValidEmail() {
        return VALID_EMAIL;
    }

    public String getFullNameToRegister() {
        return generateFullNameToRegister();
    }

    public static String getValidPassword() {
        return VALID_PASSWORD;
    }


    private int generateRandomInt() {
        return new Random().nextInt();
    }


    private String generateEmailToRegister() {
        return "veraexpert209+" + generateRandomInt() + "@gmail.com";
    }

    private String generateFullNameToRegister() {
        return "Kesha" + generateRandomInt() + "@gmail.com";
    }

}
