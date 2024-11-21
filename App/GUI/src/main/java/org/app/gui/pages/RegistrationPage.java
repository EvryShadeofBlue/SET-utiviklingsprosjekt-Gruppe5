package org.app.gui.pages;

import org.app.core.models.Parorende;
import org.app.core.models.Resources;
import org.app.database.PagesDBImplementation;
import org.app.gui.utils.GUIUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegistrationPage extends JFrame{
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField mobileField;
    private JTextField emailField;
    private JPasswordField passwordField;

    public RegistrationPage() {
        setupFrame();
        setupContent();
        setVisible(true);
    }

    private void setupFrame() {
        setTitle("Registration Page");
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

        addFields(g);
        addButtons(g);
    }

    private void addFields(GridBagConstraints g) {
        g.gridx = 0;
        g.gridy = 0;
        add(GUIUtils.createLabel("Fornavn"), g);

        g.gridy++;
        firstNameField = GUIUtils.createTextField(20);
        add(firstNameField, g);

        g.gridy++;
        add(GUIUtils.createLabel("Etternavn"), g);

        g.gridy++;
        lastNameField = GUIUtils.createTextField(20);
        add(lastNameField, g);

        g.gridy++;
        add(GUIUtils.createLabel("Mobil nummer"), g);

        g.gridy++;
        mobileField = GUIUtils.createTextField(20);
        add(mobileField, g);

        g.gridy++;
        add(GUIUtils.createLabel("E-post"), g);

        g.gridy++;
        emailField = GUIUtils.createTextField(20);
        add(emailField, g);

        g.gridy++;
        add(GUIUtils.createLabel("Passord"), g);

        g.gridy++;
        passwordField = new JPasswordField(20);
        add(passwordField, g);
    }

    private void addButtons(GridBagConstraints g) {
        g.gridy++;
        JButton registerButton = GUIUtils.createButton("Registrer", null, e -> {
            try {
                registerUser();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        add(registerButton, g);

        g.gridy++;
        JButton backToLoginButton = GUIUtils.createButton("Tilbake til innlogging", null, e -> {
            new LoginPage();
            dispose();
        });
        add(backToLoginButton, g);
    }

    private void registerUser() throws SQLException {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String mobileNumber = mobileField.getText();
        String email = emailField.getText();
        String pass = new String(passwordField.getPassword());

        if (firstName.isEmpty() || lastName.isEmpty() || mobileNumber.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fyll ut alle feltene");
            return;
        }

        String password = Resources.hashPasswordWithSalt(pass);

        try {
            int parorendeId = PagesDBImplementation.insertParorende(firstName, lastName, mobileNumber, email);
            PagesDBImplementation.insertInnlogging(email, password, parorendeId);

            Parorende parorende = new Parorende(parorendeId, firstName, lastName, mobileNumber, email);

            JOptionPane.showMessageDialog(this, "Registrering vellykket.");
            clearFields();

            new MainPage(parorende, null);
            dispose();

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