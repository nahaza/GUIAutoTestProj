package libs;

import java.util.Random;

public class TestData {

    private final static String VALID_EMAIL = "veraexpert209+1195675139@gmail.com";
    private final static String VALID_PASSWORD = "1";


    private String emailToRegister = generateEmailToRegister();

    private String fullNameToRegister = generateFullNameToRegister();

    public String getEmailToRegister() {
        return emailToRegister;
    }

    public static String getValidEmail() {
        return VALID_EMAIL;
    }

    public String getFullNameToRegister() {
        return fullNameToRegister;
    }

    public static String getValidPassword() {
        return VALID_PASSWORD;
    }

    private static int randomInt = new Random().nextInt();

//    private int generateRandomInt(){
//        return new Random().nextInt();
//    }


    private String generateEmailToRegister() {
//        Random random = new Random();
//        int addIntegerToGmail = random.nextInt();
        emailToRegister = "veraexpert209+" + randomInt + "@gmail.com";
        System.out.println(emailToRegister);
        return emailToRegister;

    }

    private String generateFullNameToRegister() {
//        Random random = new Random();
//        int addIntegerToName = random.nextInt();
        fullNameToRegister = "Kesha" + randomInt + "@gmail.com";
        return fullNameToRegister;
    }

}
