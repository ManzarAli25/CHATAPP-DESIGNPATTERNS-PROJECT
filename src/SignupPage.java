import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class SignupPage implements ActionListener,Page { //also singleton pattern
    JFrame frame = new JFrame();

    JButton registerbutton = new JButton("Register");
    JButton resetbutton = new JButton("Reset");
    JTextField useridfield = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();

    JLabel useridLabel = new JLabel("User ID: ");
    JLabel userPasswordLabel = new JLabel("Password: ");
    JLabel messageLabel = new JLabel();

    HashMap<String,String> logininfo;

    public SignupPage(HashMap<String, String> loginInfoOriginal) {
        logininfo = loginInfoOriginal;

        useridLabel.setBounds(50,100,75,25);
        userPasswordLabel.setBounds(50,150,75,25);
        registerbutton.setBounds(125,200,100,25);
        registerbutton.addActionListener(this);
        resetbutton.setBounds(225,200,100,25);

        registerbutton.setBackground(new Color(226, 205, 16));
        registerbutton.setForeground(Color.BLACK);
        registerbutton.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        resetbutton.setBackground(new Color(226, 205, 16));
        resetbutton.setForeground(Color.BLACK);
        resetbutton.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        resetbutton.addActionListener(this);

        JPanel p1 =new JPanel();
        p1.setBackground(new Color(226, 205, 16));
        p1.setBounds(0,0,600,70);
        p1.setLayout(null);
        frame.add(p1);

        frame.add(useridLabel);
        frame.add(userPasswordLabel);
        frame.add(messageLabel);
        frame.add(useridfield);
        frame.add(userPasswordField);
        frame.add(registerbutton);
        frame.add(resetbutton);
        useridLabel.setFont(new Font("SAN_SERIF",Font.BOLD,12));
        userPasswordLabel.setFont(new Font("SAN_SERIF",Font.BOLD,12));
        registerbutton.setFont(new Font("SAN_SERIF",Font.BOLD,12));
        resetbutton.setFont(new Font("SAN_SERIF",Font.BOLD,12));

        messageLabel.setBounds(125,250,250,35);
        messageLabel.setFont(new Font(null,Font.ITALIC,25));
        useridfield.setBounds(125,100,200,25);
        userPasswordField.setBounds(125,150,200,25);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600 , 600);
        frame.setLayout(null);
        frame.setVisible(true);
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==resetbutton){
            useridfield.setText("");
            userPasswordField.setText("");
        }
        if(e.getSource()==registerbutton){
            String userID = useridfield.getText(); // get the value of userID
            String password = String.valueOf(userPasswordField.getPassword());
            if(userID.equals("") || password.equals("")) {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Enter both User ID and Password.");
                return;
            }
            if(logininfo.containsKey(userID)){
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("User ID already exists.");
            }
            else{

                IDandPasswords idAndPasswords = IDandPasswords.getInstance();
                idAndPasswords.addUser(userID, password);
                messageLabel.setForeground(Color.GREEN);
                messageLabel.setText("Registration Successful!");
                frame.setVisible(false);

                PageFactory pageFactory =new PageFactory();
                LoginPage loginPage= (LoginPage) pageFactory.getInstance("Login");
                loginPage.frame.setVisible(true);



            }
        }
    }
}