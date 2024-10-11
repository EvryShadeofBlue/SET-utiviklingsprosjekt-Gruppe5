package models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javax.swing.Box.createVerticalStrut;

public class LoginPage extends JFrame {

    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel registerLabel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginPage() {
        setTitle("Login Page");
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 10, 10, 10);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;

        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 1;
        add(emailLabel = new JLabel("Email:"), g);

        g.gridx = 0;
        g.gridy = 1;
        g.gridwidth = 2;
        add(emailField = new JTextField(60), g);

        g.gridx = 0;
        g.gridy = 2;
        g.gridwidth = 1;
        add(passwordLabel = new JLabel("Password:"), g);

        g.gridx = 0;
        g.gridy = 3;
        g.gridwidth = 2;
        add(passwordField = new JPasswordField(20), g);

        g.gridx = 0;
        g.gridy = 5;
        g.gridwidth = 1;
        add(registerLabel = new JLabel("Register:"), g);

        g.gridx = 1;
        g.gridy = 6;
        g.gridwidth = 1;
        g.anchor = GridBagConstraints.EAST;
        add(loginButton = new JButton("Login"), g);

        g.gridx = 0;
        g.gridy = 7;
        g.gridwidth = 1;
        add(registerLabel = new JLabel("New User"), g);

        g.gridx = 1;
        g.gridy = 8;
        g.gridwidth = 1;
        g.anchor = GridBagConstraints.EAST;
        add(registerButton = new JButton("Register Here"), g);

        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginUser();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegistrationPage();
            }
        });
        setVisible(true);
    }
    private void LoginUser() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        Connection con = DBHelper.getConnection();
        String insertQuery = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (PreparedStatement ps = con.prepareStatement(insertQuery)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful");
                openToDoPage();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Email or Password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "error, Try Again");
        }
    }
    private void openToDoPage(){
        new RegistrationPage();
        dispose();
    }
    public void openRegistrationPage() {
        new RegistrationPage();
        dispose();
    }
}
