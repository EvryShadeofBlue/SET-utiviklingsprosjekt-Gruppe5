package org.app.gui.pages;

import org.app.core.models.Parorende;
import org.app.core.models.Resources;
import org.app.database.PagesDBImplementation;
import org.app.gui.utils.GUIUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginPage extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginPage() {

        setupFrame();
        setupContent();
        setVisible(true);
    }

    private void setupFrame() {
        setTitle("Login Page");
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

    }

    private void setupContent() {
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 10, 10, 10);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;

        g.gridx = 0;
        g.gridy = 0;
        add(GUIUtils.createLabel("E-post:"), g);

        g.gridy++;
        emailField = GUIUtils.createTextField(60);
        add(emailField, g);

        g.gridy++;
        add(GUIUtils.createLabel("Passord:"), g);

        g.gridy++;
        passwordField = new JPasswordField(20);
        add(passwordField, g);

        g.gridy++;
        JButton loginButton = GUIUtils.createButton("Logg inn", new Dimension(100, 30), e -> getLogIn());
        add(loginButton, g);

        g.gridy++;
        JButton registerButton = GUIUtils.createButton("Registrer her", new Dimension(100, 30), e -> openRegistrationPage());
        add(registerButton, g);

        getRootPane().setDefaultButton(loginButton);
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