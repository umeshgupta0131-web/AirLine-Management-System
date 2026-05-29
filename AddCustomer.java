package airlinemanagementsystem;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class AddCustomer extends JFrame implements ActionListener {

    JTextField tfname, tfphone, tfaadhar, tfnationality, tfaddress;
    JRadioButton rbmale, rbfemale, rbother;

    public AddCustomer() {
        getContentPane().setBackground(Color.PINK);
        setLayout(null);

        JLabel heading = new JLabel("ADD CUSTOMER DETAILS");
        heading.setBounds(220, 20, 500, 35);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 32));
        heading.setForeground(Color.red);
        add(heading);

        JLabel lblname = new JLabel("Name");
        lblname.setBounds(60, 80, 150, 25);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblname);

        tfname = new JTextField();
        tfname.setBounds(190, 80, 150, 25);
        add(tfname);

        JLabel lblnationality = new JLabel("Nationality");
        lblnationality.setBounds(60, 120, 150, 25);
        lblnationality.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblnationality);

        tfnationality = new JTextField();
        tfnationality.setBounds(190, 120, 150, 25);
        add(tfnationality);

        JLabel lblaadhar = new JLabel("Aadhar Number");
        lblaadhar.setBounds(60, 160, 150, 25);
        lblaadhar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblaadhar);

        tfaadhar = new JTextField();
        tfaadhar.setBounds(190, 160, 150, 25);
        add(tfaadhar);
        setNumericLimitFilter(tfaadhar, 12); // Enforce 12-digit numeric only

        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(60, 200, 150, 25);
        lbladdress.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbladdress);

        tfaddress = new JTextField();
        tfaddress.setBounds(190, 200, 150, 25);
        add(tfaddress);

        JLabel lblphone = new JLabel("Phone");
        lblphone.setBounds(60, 240, 150, 25);
        lblphone.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblphone);

        tfphone = new JTextField();
        tfphone.setBounds(190, 240, 150, 25);
        add(tfphone);
        setNumericLimitFilter(tfphone, 10); // Enforce 10-digit numeric only

        // Gender Radio Buttons
        ButtonGroup gendergroup = new ButtonGroup();

        rbmale = new JRadioButton("Male");
        rbmale.setBounds(200, 280, 70, 25);
        rbmale.setBackground(Color.PINK);
        add(rbmale);

        rbfemale = new JRadioButton("Female");
        rbfemale.setBounds(270, 280, 70, 25);
        rbfemale.setBackground(Color.PINK);
        add(rbfemale);

        rbother = new JRadioButton("Other");
        rbother.setBounds(340, 280, 70, 25);
        rbother.setBackground(Color.PINK);
        add(rbother);

        gendergroup.add(rbmale);
        gendergroup.add(rbfemale);
        gendergroup.add(rbother);

        JButton save = new JButton("SAVE");
        save.setBackground(Color.red);
        save.setForeground(Color.WHITE);
        save.setBounds(200, 320, 70, 25);
        save.addActionListener(this);
        add(save);

        ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/emp.png"));
        JLabel lblimage = new JLabel(image);
        lblimage.setBounds(450, 80, 280, 400);
        add(lblimage);

        setSize(900, 600);
        setLocation(300, 100);
        setVisible(true);
    }

    // Action listener for SAVE button
    public void actionPerformed(ActionEvent ae) {
        String name = tfname.getText();
        String nationality = tfnationality.getText();
        String phone = tfphone.getText();
        String address = tfaddress.getText();
        String aadhar = tfaadhar.getText();
        String gender = null;

        if (rbmale.isSelected()) {
            gender = "Male";
        } else if (rbfemale.isSelected()) {
            gender = "Female";
        } else if (rbother.isSelected()) {
            gender = "Other";
        }

        // ✅ Validation
        if (name.isEmpty() || nationality.isEmpty() || phone.isEmpty() || address.isEmpty() || aadhar.isEmpty() || gender == null) {
            JOptionPane.showMessageDialog(this, "All fields must be filled", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (aadhar.length() != 12) {
            JOptionPane.showMessageDialog(this, "Aadhar number must be exactly 12 digits", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (phone.length() != 10) {
            JOptionPane.showMessageDialog(this, "Phone number must be exactly 10 digits", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Conn conn = new Conn();
            String query = "insert into passenger values('" + name + "', '" + nationality + "', '" + phone + "', '" + address + "', '" + aadhar + "', '" + gender + "')";
            conn.s.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Customer Details Added Successfully");
            setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Utility method: Restrict input to digits and max length
    private void setNumericLimitFilter(JTextField field, int maxLength) {
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("\\d+") && fb.getDocument().getLength() + string.length() <= maxLength) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                int newLength = fb.getDocument().getLength() - length + (text != null ? text.length() : 0);
                if (text.matches("\\d*") && newLength <= maxLength) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

    public static void main(String[] args) {
        new AddCustomer();
    }
}
