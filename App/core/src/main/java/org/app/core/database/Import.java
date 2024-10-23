package org.app.core.database;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class Import {
    String url = "jdbc:mysql://localhost:3306/project";
    String user = "root";
    String password = "12345";


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

        String insertQuery = "INSERT INTO pleietrengende (fornavn, etternavn, parorende_id) VALUES (?, ?, ?)";

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


        String insertQuery = "INSERT INTO Avtaler (dato_opprettet, beskrivelse, dato_og_tid," +
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
    public void importBeskjeder(String beskrivelse, Date date, int synlig_tid, int pleietrengende_id, int parorende_id) {

        java.util.Date currentDate = new java.util.Date();
        SimpleDateFormat dateTime = new SimpleDateFormat("dd.MM.yyyy HH.mm");
        String dateSet = dateTime.format(date);
        String dateCreated = dateTime.format(currentDate);


        String insertQuery = "INSERT INTO Beskjeder (dato_opprettet, beskrivelse, dato_og_tid," +
                "synlig_tid, pleietrengende_id, parorende_id) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            PreparedStatement pstmt = con.prepareStatement(insertQuery);

            pstmt.setString(1, dateCreated);
            pstmt.setString(2, beskrivelse);
            pstmt.setString(3, dateSet);
            pstmt.setInt(4, synlig_tid);
            pstmt.setInt(5, pleietrengende_id);
            pstmt.setInt(6,parorende_id);

            pstmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void importHandleliste(int pleietrengende_id) {

        java.util.Date currentDate = new java.util.Date();
        SimpleDateFormat dateTime = new SimpleDateFormat("dd.MM.yyyy HH.mm");
        String dateCreated = dateTime.format(currentDate);


        String insertQuery = "INSERT INTO Handleliste (pleietrengende_id, opprettet_dato," +
                " oppdatert_dato) VALUES (?, ?, ?)";

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            PreparedStatement pstmt = con.prepareStatement(insertQuery);

            pstmt.setInt(1, pleietrengende_id);
            pstmt.setString(2, dateCreated);
            pstmt.setString(3, dateCreated);

            pstmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void importHandlelisteVarer(int handleliste_id, String vare_navn) {

        java.util.Date currentDate = new java.util.Date();
        SimpleDateFormat dateTime = new SimpleDateFormat("dd.MM.yyyy HH.mm");
        String dateUpdate = dateTime.format(currentDate);


        String insertQuery = "INSERT INTO HandlelisteVarer (handleliste_id, vare_navn) VALUES (?, ?)";
        String updateQuery = "INSERT INTO Handleliste (oppdatert_dato) VALUES (?)";

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            PreparedStatement updt = con.prepareStatement(updateQuery);

            pstmt.setInt(1, handleliste_id);
            pstmt.setString(2, vare_navn);
            updt.setString(1, dateUpdate);

            pstmt.close();
            updt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void importInnlogging(String epost, String passord, int parorende_id) {

        String insertQuery = "INSERT INTO Innlogging (epost, passord," +
                " parorende_id) VALUES (?, ?, ?)";

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            PreparedStatement pstmt = con.prepareStatement(insertQuery);

            pstmt.setString(1, epost);
            pstmt.setString(2, passord);
            pstmt.setInt(3, parorende_id);

            pstmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}