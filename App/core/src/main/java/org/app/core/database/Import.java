package org.app.core.database;

import org.app.core.models.Resources;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class Import {
    String url = Resources.url;
    String user = Resources.user;
    String password = Resources.password;
    public void importParorende(String firstName, String lastName, String number, String email) {

        String insertQuery = "INSERT INTO parorende (fornavn, etternavn, tlf, epost) VALUES (?, ?, ?, ?)";

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            PreparedStatement pstmt = con.prepareStatement(insertQuery);

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, number);
            pstmt.setString(4, email);

            pstmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void importPleietrengende(String firstName, String lastName, String number, int parorende_id) {

        String insertQuery = "INSERT INTO parorende (fornavn, etternavn, parorende_id) VALUES (?, ?, ?)";

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            PreparedStatement pstmt = con.prepareStatement(insertQuery);

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setInt(3, parorende_id);

            pstmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void importAvtaler(String beskrivelse, Date date, int pleietrengende_id, int parorende_id) {

        java.util.Date currentDate = new java.util.Date();
        SimpleDateFormat dateTime = new SimpleDateFormat("dd.MM.yyyy HH.mm");
        String dateSet = dateTime.format(date);
        String dateCreated = dateTime.format(currentDate);


        String insertQuery = "INSERT INTO parorende (dato_opprettet, beskrivelse, dato_og_tid," +
                " pleietrengende_id, parorende_id) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            PreparedStatement pstmt = con.prepareStatement(insertQuery);

            pstmt.setString(1, dateCreated);
            pstmt.setString(2, beskrivelse);
            pstmt.setString(3, dateSet);
            pstmt.setInt(4, pleietrengende_id);
            pstmt.setInt(5,parorende_id);

            pstmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}