import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Server implements ActionListener {
    JTextField text1;
    JPanel a1;
    String userID;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    static Mediator mediator;


    Server(String userID) {
        this.userID = userID;
        f.setLayout(null);
        JPanel p1 =new JPanel();
        p1.setBackground(new Color(226, 205, 16));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        f.add(p1);

        JLabel back = new JLabel("GO BACK");
        back.setBounds(5,20,25,25);
        p1.add(back);
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }
        });





        JLabel name = new JLabel(userID);
        name.setBounds(110,15,100,18);
        name.setForeground(Color.BLACK);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));

        p1.add(name);
        a1 = new JPanel();
        a1.setBounds(5,75,425,570);
        f.add(a1);

        text1 = new JTextField();
        text1.setBounds(5,665,310,40);
        text1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(text1);

        JButton send = new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(226, 205, 16));
        send.setForeground(Color.BLACK);
        send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        send.addActionListener(this);
        f.add(send);


        f.setSize(450,700);
        f.setLocation(200,50);
        f.setUndecorated(true);

        f.getContentPane().setBackground(Color.BLACK);
        f.setVisible(true);

        mediator = new ChatMediator();
        ServerParticipant serverParticipant = new ServerParticipant(userID, mediator);
        mediator.registerServer(serverParticipant);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String out = text1.getText();
            a1.setLayout(new BorderLayout());

            JPanel p2 = formatLabel(out);

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);

            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);


            text1.setText("");


            f.repaint();
            f.invalidate();
            f.validate();
        }
        catch(Exception e2){
            e2.printStackTrace();

        }
    }
    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));


        JLabel output = new JLabel("<html><p style='width: 150px'>" + out + "</p></html>");         output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211, 165));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;

    }

    public static void main(String[] args) {
        Server server = new Server("Client");
        try{
            ServerSocket skt = new ServerSocket(6001);
            while(true){
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
                while(true){
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();


                }
            }
        }
        catch(Exception e1){
            e1.printStackTrace();

        }
    }
}