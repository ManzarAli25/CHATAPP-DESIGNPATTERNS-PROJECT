import java.util.HashMap;

public class IDandPasswords {
    public static IDandPasswords instance= new IDandPasswords();

    HashMap<String,String> logininfo = new HashMap<String,String>();

    private IDandPasswords(){

        logininfo.put("Manzar", "manzarpassword");
        logininfo.put("Sameer", "sameerpassword");
        logininfo.put("Saad", "saadpassword");
        logininfo.put("Muneer", "muneerpassword");
    }
    public static IDandPasswords getInstance(){
        return instance;
    }

    public HashMap getLoginInfo(){
        return logininfo;
    }
    public void addUser(String u, String p){
        logininfo.put(u,p);
    }
}
//*
