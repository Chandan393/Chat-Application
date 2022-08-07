
package chattingapplication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.net.*;

public class Server implements ActionListener{
    
    
     JTextField text; //Declaring TextField Globally So that it can be used inside as well as outside the Constuctor
     JPanel t1;//Declaring Globally so that msg can be append above it 
     static Box vertical = Box.createVerticalBox();
      static JFrame  f = new JFrame();
     static DataOutputStream msg_out;
    
    public Server() {
         
         f.setLayout(null);
         
         JPanel p1 = new JPanel(); //Panel p1 = Heading Area
         p1.setBackground(Color.DARK_GRAY);
         p1.setBounds(0, 0,450,70); //setBounds() helps to pass specific Coordinates
         p1.setLayout(null);
         f.add(p1);
         
         ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/arrow.png"));
         Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
         ImageIcon i3 = new ImageIcon(i2);
         JLabel backArrow = new JLabel(i3);
         backArrow.setBounds(5, 20, 25, 25);
         p1.add(backArrow);
         
         //Action on Back Arrow to terminate the Chat
         backArrow.addMouseListener(new MouseAdapter() {
             public void mouseClicked(MouseEvent ae){
                 System.exit(0);
             }
         });
         
         ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/Tom.png"));
         Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
         ImageIcon i6 = new ImageIcon(i5);
         JLabel dp = new JLabel(i6);
         dp.setBounds(40, 10, 50, 50);
         p1.add(dp);
         
         ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
         Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
         ImageIcon i9 = new ImageIcon(i8);
         JLabel video = new JLabel(i9);
         video.setBounds(300, 20, 30, 30);
         p1.add(video);
         
         ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
         Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
         ImageIcon i12 = new ImageIcon(i11);
         JLabel phone = new JLabel(i12);
         phone.setBounds(360, 20, 35, 30);
         p1.add(phone);
         
         ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
         Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
         ImageIcon i15 = new ImageIcon(i14);
         JLabel dot = new JLabel(i15);
         dot.setBounds(420, 20, 10, 25);
         p1.add(dot);
         
         JLabel name = new JLabel("Tom");
         name.setBounds(110, 15, 100, 18);
         name.setForeground(Color.WHITE);
         name.setFont(new Font("SAN_SERIF",Font.BOLD, 18));
         p1.add(name);
         
          JLabel status = new JLabel("Active Now");
         status.setBounds(110, 40, 100, 18);
         status.setForeground(Color.WHITE);
         status.setFont(new Font("SAN_SERIF",Font.BOLD, 14));
         p1.add(status);
         
         t1 = new JPanel(); // t1 = text Area
         t1.setBounds(5, 75, 440, 570);  
         f.add(t1);
         
         //MESSAGE  TEXTING AREA
        text =  new JTextField();
        text.setBounds(5, 655, 310 , 40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN, 16));
        f.add(text);
    
         JButton send = new JButton("Send");
         send.setBounds(320, 655, 123, 40);
         send.setForeground(Color.WHITE);
         send.setBackground(Color.DARK_GRAY);
         
         send.addActionListener(this);
         
         send.setFont(new Font("SAN_SERIF",Font.PLAIN, 16));
        f. add(send);
         
         
         
         f.setSize(450, 700);
         f.setLocation(150,20);
         f.setUndecorated(true);
         f.getContentPane().setBackground(Color.BLACK);
         
         
         
         
         f.setVisible(true);
                
            
}
     @Override
    public void actionPerformed(ActionEvent ae) {
        try {
         String outmsg = text.getText();
         
         
         
         JPanel p2 = formatLabel(outmsg);
        
         
         t1.setLayout(new BorderLayout());
         
         JPanel right =  new JPanel(new BorderLayout());
         //text added in line end using BorderLayout
         right.add(p2, BorderLayout.LINE_END); //added a panel p2 ,since String cannot be added
         vertical.add(right); // msg will be printed one after another vertically
         vertical.add(Box.createVerticalStrut(15));//Spaces b/w messages
         t1.add(vertical, BorderLayout.PAGE_START);
         
         msg_out.writeUTF(outmsg);
         
         text.setText("");
         
         
         
         
         f.repaint();
         f.invalidate();
         f.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    public static JPanel formatLabel(String outmsg) {
        JPanel panel =  new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + outmsg +"</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(Color.GRAY);
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        
        panel.add(output);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        
        panel.add(time);
        
        
        return panel;
        
    }
    
    public static void main(String[] args) {
        new Server();
        
        try {
            ServerSocket server = new ServerSocket(3399);
            while(true) {
                Socket socket = server.accept();
                DataInputStream msg_in = new DataInputStream(socket.getInputStream());
                msg_out = new DataOutputStream(socket.getOutputStream());
                
                while(true) {
                    String msg = msg_in.readUTF();
                    JPanel panel = formatLabel(msg);
                    
                    
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    
                    vertical.add(left);
                    f.validate();                                             
                }
                
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }

    
    
}
