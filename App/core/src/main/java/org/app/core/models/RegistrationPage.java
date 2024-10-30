package org.app.core.models;

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegistrationPage extends JFrame{
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel mobileLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField mobileField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton backToLoginButton;

    public RegistrationPage() {
        setTitle("Registration Page");
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        GridBagConstraints g1 = new GridBagConstraints();
        g1.insets = new Insets(10, 10, 10, 10);
        g1.fill = GridBagConstraints.HORIZONTAL;
        g1.weightx = 1;

        g1.gridx = 0;
        g1.gridy = 0;
        g1.gridwidth = 1;
        add(firstNameLabel = new JLabel("First Name"), g1);

        g1.gridx = 0;
        g1.gridy = 1;
        g1.gridwidth = 2;
        add(firstNameField = new JTextField(20), g1);

        g1.gridx = 0;
        g1.gridy = 2;
        g1.gridwidth = 1;
        add(lastNameLabel = new JLabel("Last Name"), g1);

        g1.gridx = 0;
        g1.gridy = 3;
        g1.gridwidth = 2;
        add(lastNameField = new JTextField(20), g1);

        g1.gridx = 0;
        g1.gridy = 4;
        g1.gridwidth = 1;
        add(mobileLabel= new JLabel("Mobile Number"), g1);

        g1.gridx = 0;
        g1.gridy = 5;
        g1.gridwidth = 2;
        add(mobileField = new JTextField(20), g1);

        g1.gridx = 0;
        g1.gridy = 6;
        g1.gridwidth = 1;
        add(emailLabel = new JLabel("Email"), g1);

        g1.gridx = 0;
        g1.gridy = 7;
        g1.gridwidth = 2;
        add(emailField = new JTextField(20), g1);

        g1.gridx = 0;
        g1.gridy = 8;
        g1.gridwidth = 1;
        add(passwordLabel = new JLabel("Password"), g1);

        g1.gridx = 0;
        g1.gridy = 9;
        g1.gridwidth = 2;
        add(passwordField = new JPasswordField(20), g1);

        g1.gridx = 1;
        g1.gridy = 10;
        g1.gridwidth = 1;
        g1.anchor = GridBagConstraints.EAST;
        add(registerButton = new JButton("Register"), g1);

        g1.gridx = 0;
        g1.gridy = 10;
        g1.gridwidth = 1;
        g1.anchor = GridBagConstraints.WEST;
        add(backToLoginButton = new JButton("Back to login"), g1);

        setVisible(true);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        backToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPage();
                dispose();

            }
        });
    }
    private void registerUser() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String mobileNumber = mobileField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());


        String insertParorendeQuery = "Insert into Parorende (fornavn, etternavn, tlf, epost) Values (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(Resources.url, Resources.user, Resources.password);
        PreparedStatement parorendeStatement = connection.prepareStatement(insertParorendeQuery, Statement.RETURN_GENERATED_KEYS)) {
            parorendeStatement.setString(1, firstName);
            parorendeStatement.setString(2, lastName);
            parorendeStatement.setString(3, mobileNumber);
            parorendeStatement.setString(4, email);

            int rowsInserted = parorendeStatement.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKey = parorendeStatement.getGeneratedKeys()){
                    if (generatedKey.next()) {
                        int parorendeId = generatedKey.getInt(1);

                        String insertInnloggingQuery = "Insert into Innlogging (epost, passord, parorende_id) Values (?, ?, ?)";
                        try (PreparedStatement innloggingStatement = connection.prepareStatement(insertInnloggingQuery)){
                            innloggingStatement.setString(1, email);
                            innloggingStatement.setString(2, password);
                            innloggingStatement.setInt(3, parorendeId);

                            innloggingStatement.executeUpdate();
                        }
                    }

                }
                JOptionPane.showMessageDialog(this, "Registrering vellykket. ");
                clearFields();

                new MainPage(firstName, "");
                dispose();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(this, "Registrering feilet. " + exception.getMessage());
        }


    }
    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        mobileField.setText("");
        emailField.setText("");
        passwordField.setText("");

    }
}
