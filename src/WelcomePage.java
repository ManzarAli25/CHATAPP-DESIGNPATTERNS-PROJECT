import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class WelcomePage implements ActionListener {
    JFrame frame = new JFrame();
    JButton login = new JButton("Log In");
    JButton register = new JButton("Register");
    IDandPasswords idAndPasswords = IDandPasswords.getInstance();
    HashMap<String, String> loginInfo = idAndPasswords.getLoginInfo();

    public WelcomePage() {
        register.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        login.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        register.setBackground(new Color(226, 205, 16));
        register.setForeground(Color.BLACK);
        login.setBackground(new Color(226, 205, 16));
        login.setForeground(Color.BLACK);
        login.setBounds(250,370,170,50);
        register.setBounds(425,370,170,50);
        login.addActionListener(this);
        register.addActionListener(this);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(226, 205, 16));
        p1.setBounds(0, 0, 900, 70);
        p1.setLayout(null);

        JPanel p2 = new JPanel();
        p2.setBounds(225, 80, 450, 70);
        p2.setBackground(new Color(226, 205, 16));
        p2.setLayout(null);

        JLabel slack = new JLabel("            Communicate, Collaborate.  ");
        slack.setForeground(Color.BLACK);

        slack.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        slack.setBounds(30, 20, 400, 30);
        p2.add(slack);

        frame.add(p1);
        frame.add(p2);
        frame.add(register);
        frame.add(login);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==login){
            PageFactory pageFactory =new PageFactory();
            LoginPage loginPage= (LoginPage) pageFactory.getInstance("Login");
            frame.setVisible(false);
            loginPage.frame.setVisible(true);
        }
        if(e.getSource()==register){
            PageFactory pageFactory =new PageFactory();
            SignupPage signupPage= (SignupPage) pageFactory.getInstance("Signup");
            frame.setVisible(false);
            signupPage.frame.setVisible(true);
        }
    }
}