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
    Export exporter = new Export();
    private Parorende parorende;

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

        String loginQuery = "select i.parorende_id, p.fornavn as parorendeFornavn, p.etternavn as parorendeEtternavn, " +
                "pl.pleietrengende_id, pl.fornavn as pleietrengendeFornavn, pl.etternavn as pleietrengendeEtternavn " +
                "from Innlogging i " +
                "join Parorende p on i.parorende_id = p.parorende_id " +
                "left join Pleietrengende pl on p.parorende_id = pl.parorende_id " +
                "where i.epost = ? and i.passord = ?";

        try (Connection connection = DriverManager.getConnection(Resources.url, Resources.user, Resources.password);
        PreparedStatement preparedStatement = connection.prepareStatement(loginQuery)) {
            preparedStatement.setString(1, enteredEmail);
            preparedStatement.setString(2, enteredPassword);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int parorendeId = resultSet.getInt("parorende_id");
                String parorendeFornavn = resultSet.getString("parorendeFornavn");
                String parorendeEtternavn = resultSet.getString("parorendeEtternavn");

                Pleietrengende pleietrengende = null;
                if (resultSet.getObject("pleietrengende_id") != null) {
                    int pleietrengendeId = resultSet.getInt("pleietrengende_id");
                    String pleietrengendeFornavn = resultSet.getString("pleietrengendeFornavn");
                    String pleietrengendeEtternavn = resultSet.getString("pleietrengendeEtternavn");
                    pleietrengende = new Pleietrengende(pleietrengendeId, pleietrengendeFornavn, pleietrengendeEtternavn);
                }

                Parorende parorende = new Parorende(parorendeId, parorendeFornavn, parorendeEtternavn);

                new MainPage(parorende, pleietrengende);
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
