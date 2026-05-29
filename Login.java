package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*; 

public class Login extends JFrame implements ActionListener {

    JButton submit, reset, close;
    JTextField tfusername;
    JPasswordField tfPassword;

    public Login() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(20, 20, 100, 20);
        add(lblusername);

        tfusername = new JTextField();
        tfusername.setBounds(100, 20, 150, 20);
        add(tfusername);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(20, 60, 100, 20);
        add(lblpassword);

        tfPassword = new JPasswordField();
        tfPassword.setBounds(100, 60, 150, 20);
        add(tfPassword);

        reset = new JButton("Reset");
        reset.setBounds(40, 100, 120, 20);
        reset.addActionListener(this);
        add(reset);

        submit = new JButton("Submit");
        submit.setBounds(200, 100, 120, 20);
        submit.addActionListener(this);
        add(submit);

        close = new JButton("Close");
        close.setBounds(360, 100, 120, 20);
        close.addActionListener(this);
        add(close);

        setSize(550, 200);
        setLocation(400, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == submit) {
            String username = tfusername.getText();
            String password = tfPassword.getText();
                try {
                Conn c = new Conn(); 

                String query = "select * from login where username = '" + username + "' and password = '" + password + "'";
                ResultSet rs = c.s.executeQuery(query);

                if (rs.next()) { 
                    new Home();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                    setVisible(false);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (ae.getSource() == close) {
            setVisible(false);
        } else if (ae.getSource() == reset) {
            tfusername.setText("");
            tfPassword.setText("");
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
