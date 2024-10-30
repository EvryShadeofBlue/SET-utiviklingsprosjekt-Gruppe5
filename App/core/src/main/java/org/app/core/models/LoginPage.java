package org.app.core.models;

import org.app.core.database.Export;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

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
        //setLayout(null);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getLogIn();
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
    public void getLogIn() {
        String enteredEmail = emailField.getText();
        String enteredPassword = new String(passwordField.getPassword());

        String loginQuery = "select p.fornavn as parorendeFornavn, p.etternavn as parorendeEtternavn, " +
                "pl.fornavn as pleietrengendeFornavn, pl.etternavn as pleietrengendeEtternavn " +
                "from Innlogging i " +
                "join Parorende p on i.parorende_id = p.parorende_id " +
                "join Pleietrengende pl on p.parorende_id = pl.parorende_id " +
                "where i.epost = ? and i.passord = ?";

        try (Connection connection = DriverManager.getConnection(Resources.url, Resources.user, Resources.password);
        PreparedStatement preparedStatement = connection.prepareStatement(loginQuery)) {
            preparedStatement.setString(1, enteredEmail);
            preparedStatement.setString(2, enteredPassword);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String parorendeFornavn = resultSet.getString("parorendeFornavn");
                String parorendeEtternavn = resultSet.getString("parorendeEtternavn");
                String pleietrengendeFornavn = resultSet.getString("pleietrengendeFornavn");
                String pleietrengendeEtternavn = resultSet.getString("pleietrengendeEtternavn");

                new MainPage(parorendeFornavn + " " + parorendeEtternavn,
                        pleietrengendeFornavn + " " + pleietrengendeEtternavn);
                dispose();
            }
            else {
                JOptionPane.showMessageDialog(this, "Feil e-post eller passord. Vennligst prøv igjen. ");
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
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
