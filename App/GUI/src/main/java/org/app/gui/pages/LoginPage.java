package org.app.gui.pages;

import org.app.core.models.Pleietrengende;
import org.app.core.models.Parorende;
import org.app.core.models.Resources;
import org.app.database.PagesDBImplementation;

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
    private Parorende parorende;

    String url = Resources.getUrl();
    String user = Resources.getUser();
    String password = Resources.getPassword();

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
        add(emailLabel = new JLabel("E-post:"), g);

        g.gridx = 0;
        g.gridy = 1;
        g.gridwidth = 2;
        add(emailField = new JTextField(60), g);

        g.gridx = 0;
        g.gridy = 2;
        g.gridwidth = 1;
        add(passwordLabel = new JLabel("Passord:"), g);

        g.gridx = 0;
        g.gridy = 3;
        g.gridwidth = 2;
        add(passwordField = new JPasswordField(20), g);

        g.gridx = 0;
        g.gridy = 5;
        g.gridwidth = 1;
        add(registerLabel = new JLabel("Registrer:"), g);

        g.gridx = 1;
        g.gridy = 6;
        g.gridwidth = 1;
        g.anchor = GridBagConstraints.EAST;
        add(loginButton = new JButton("Logg inn"), g);

        g.gridx = 0;
        g.gridy = 7;
        g.gridwidth = 1;
        add(registerLabel = new JLabel("Ny bruker"), g);

        g.gridx = 1;
        g.gridy = 8;
        g.gridwidth = 1;
        g.anchor = GridBagConstraints.EAST;
        add(registerButton = new JButton("Registrer her"), g);

        getRootPane().setDefaultButton(loginButton);

        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        String enteredPass = new String(passwordField.getPassword());
        String enteredPassword = Resources.hashPasswordWithSalt(enteredPass);

        try {
            Parorende parorende = PagesDBImplementation.getParorendeWithLogin(enteredEmail, enteredPassword);

            if (parorende != null) {
                JOptionPane.showMessageDialog(this, "Innlogging vellykket!");
                new MainPage(parorende, parorende.getPleietrengende());
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Feil e-post eller passord. Vennligst prøv igjen.");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(this, "Innlogging feilet. " + sqlException.getMessage());
        }
    }

    private void openRegistrationPage() {
        new RegistrationPage();
        dispose();
    }
}