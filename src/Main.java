import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        // Add login information to the HashMap
            IDandPasswords idpass = IDandPasswords.getInstance();
            HashMap<String, String> logininfo = idpass.logininfo;
            WelcomePage welcomePage = new WelcomePage();


    }
}