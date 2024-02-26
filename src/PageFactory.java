import java.awt.event.ActionListener;
import java.util.HashMap;

interface Page extends ActionListener {

}
public class PageFactory {
    public Page getInstance(String str){
        IDandPasswords idAndPasswords =IDandPasswords.getInstance();
        HashMap<String, String> loginInfo = idAndPasswords.getLoginInfo();
        if(str.equals("Signup")){
            return  new SignupPage(loginInfo);}
        else if(str.equals("Login")){
            return new LoginPage(loginInfo);
        }
        else{
            return null;
        }
    }
}
