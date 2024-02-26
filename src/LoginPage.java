import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class LoginPage implements Page{
    //singleton pattern
    JFrame frame = new JFrame();

    JButton loginbutton = new JButton("Login");
    JButton resetbutton = new JButton("Reset");
    JTextField useridfield = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();

    JLabel useridLabel = new JLabel("User ID: ");



    JLabel userPasswordLabel = new JLabel("Password: ");
    JLabel messageLabel = new JLabel();

    HashMap<String,String> logininfo = new HashMap<String,String>();
    String userID;
    JButton startChattingButton = new JButton("Start Chatting");
    JButton joinGroupButton = new JButton("Join Group");
    // declare userID as an instance variable


    public LoginPage(HashMap<String, String> loginInfoOriginal) {
        logininfo = loginInfoOriginal;
        useridLabel.setBounds(50,100,75,25);
        userPasswordLabel.setBounds(50,150,75,25);
        loginbutton.setBounds(125,200,100,25);
        loginbutton.addActionListener(this);
        resetbutton.setBounds(225,200,100,25);

        loginbutton.setBackground(new Color(226, 205, 16));
        loginbutton.setForeground(Color.BLACK);
        loginbutton.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
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
        frame.add(loginbutton);
        frame.add(resetbutton);
        useridLabel.setFont(new Font("SAN_SERIF",Font.BOLD,12));
        userPasswordLabel.setFont(new Font("SAN_SERIF",Font.BOLD,12));
        loginbutton.setFont(new Font("SAN_SERIF",Font.BOLD,12));
        resetbutton.setFont(new Font("SAN_SERIF",Font.BOLD,12));


        messageLabel.setBounds(125,250,250,35);
        messageLabel.setFont(new Font(null,Font.ITALIC,25));
        useridfield.setBounds(125,100,200,25);
        userPasswordField.setBounds(125,150,200,25);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600 , 600);
        frame.setLayout(null);
        frame.setVisible(true);

        logininfo = loginInfoOriginal;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(125, 300, 200, 50);
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 0));


        startChattingButton.setBackground(new Color(226, 205, 16));
        startChattingButton.setForeground(Color.BLACK);
        joinGroupButton.setForeground(Color.BLACK);
        joinGroupButton.setBackground(new Color(226, 205, 16));
        buttonPanel.add(startChattingButton);
        buttonPanel.add(joinGroupButton);

        frame.add(buttonPanel);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(null);
        frame.setVisible(true);
        joinGroupButton.addActionListener(this);
    }


    @Override


    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==resetbutton){
            useridfield.setText("");
            userPasswordField.setText("");
        }
        if(e.getSource()==loginbutton) {
            userID = useridfield.getText(); // set the value of userID
            String password = String.valueOf(userPasswordField.getPassword());

            if (logininfo.containsKey(userID)) {
                if (logininfo.get(userID).equals(password)) {
                    messageLabel.setForeground(Color.GREEN);
                    messageLabel.setText("Login Successful!");


                    // start a new thread for the server and chat page

                } else {
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setText("Incorrect Password.");
                }
            } else {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Username does not exist.");
            }
            if (messageLabel.getText().equals("Login Successful!")){
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Server server = new Server(userID);
                        try{
                            ServerSocket skt = new ServerSocket(6001);
                            while(true){
                                Socket s = skt.accept();
                                DataInputStream din = new DataInputStream(s.getInputStream());
                                server.dout = new DataOutputStream(s.getOutputStream());
                                while(true){
                                    String msg = din.readUTF();
                                    JPanel panel = server.formatLabel(msg);

                                    JPanel left = new JPanel(new BorderLayout());
                                    left.add(panel, BorderLayout.LINE_START);
                                    server.vertical.add(left);
                                    server.f.validate();
                                }
                            }
                        }
                        catch(Exception e1){
                            e1.printStackTrace();
                        }
                    }
                });
                t.start();
            }
        }
        if (e.getSource() == joinGroupButton) {
            // Start the GroupServer
            Thread groupServerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ServerSocket s = new ServerSocket(2004);
                        while (true) {
                            Socket socket = s.accept();
                            GroupServer server = new GroupServer(socket);
                            Thread thread = new Thread(server);
                            thread.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            groupServerThread.start();

            // Display the UserOne screen
            Thread userOneThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    UserOne userOne = new UserOne();
                    Thread t1 = new Thread(userOne);
                    t1.start();
                }
            });
            userOneThread.start();
        }
    }
}
