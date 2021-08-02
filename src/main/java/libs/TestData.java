package libs;

import java.util.Random;

public class TestData {

    public final static String VALID_EMAIL = "";
    public final static String VALID_PASSWORD = "1";


    public String emailToRegister = generateEmailToRegister();

    public String getEmailToRegister() {
        return emailToRegister;
    }

    public static String getValidEmail() {
        return VALID_EMAIL;
    }

    public static String getValidPassword() {
        return VALID_PASSWORD;
    }


    public String generateEmailToRegister(){
        Random random = new Random();
        int addIntegerToGmail = random.nextInt();
        String emailToRegister = "halonza.nataliia+"+addIntegerToGmail+"@gmail.com";
        return emailToRegister;
    }

    public String generateFullNameToRegister(){
        Random random = new Random();
        int addIntegerToName = random.nextInt();
        String fullNameToRegister = "Kesha"+addIntegerToName+"@gmail.com";
        return fullNameToRegister;
    }

}
