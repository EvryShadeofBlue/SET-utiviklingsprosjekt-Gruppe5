package org.app.core.database;

import java.sql.*;

public class Export {
    String url = "jdbc:mysql://localhost:3306/project";
    String user = "root";
    String password = "12345";

    public void exportParorende() {

        String exportQuery = "SELECT * FROM parorende;";

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(exportQuery);


            while (rs.next()) {
                int id = rs.getInt("parorende_id");
                String firstName = rs.getString("fornavn");
                String lastName = rs.getString("etternavn");
                String phoneNumber = rs.getString("tlf");
                String email = rs.getString("epost");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void exportPleietrengende() {

        String exportQuery = "SELECT * FROM pleietrengende;";

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(exportQuery);


            while (rs.next()) {
                int id = rs.getInt("pleietrengende_id");
                String firstName = rs.getString("fornavn");
                String lastName = rs.getString("etternavn");
                int parorende_id = rs.getInt("parorende_id");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void exportAvtaler() {

        String exportQuery = "SELECT * FROM avtaler;";

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(exportQuery);


            while (rs.next()) {
                int id = rs.getInt("avtale_id");
                String dateCreated = rs.getString("dato_opprettet");
                String description = rs.getString("beskrivelse");
                String dateTime = rs.getString("dato_og_tid");
                int pleietrengende_id = rs.getInt("pleietrengende_id");
                int parorende_id = rs.getInt("parorende_id");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void exportBeskjeder() {

        String exportQuery = "SELECT * FROM beskjeder;";

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(exportQuery);


            while (rs.next()) {
                int id = rs.getInt("beskjed_id");
                String dateCreated = rs.getString("dato_tid_opprettet");
                String description = rs.getString("beskrivelse");
                String dateTime = rs.getString("dato_tid");
                int visibleTime = rs.getInt("synlig_tid");
                int pleietrengende_id = rs.getInt("pleietrengende_id");
                int parorende_id = rs.getInt("parorende_id");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void exportHandleliste() {

        String exportQuery = "SELECT * FROM handleliste;";

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(exportQuery);


            while (rs.next()) {
                int id = rs.getInt("handleliste_id");
                int pleietrengende_id = rs.getInt("pleietrengende_id");
                String dateCreated = rs.getString("opprettet_dato");
                String dateUpdated = rs.getString("oppdatert_dato");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void exportHandlelisteVarer() {

        String exportQuery = "SELECT * FROM handlelisteVarer;";

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(exportQuery);


            while (rs.next()) {
                int id = rs.getInt("vare_id");
                int handleliste_id = rs.getInt("handleliste_id");
                String wareName = rs.getString("vare_navn");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean verifyLogin(String email, String pass) {

        String exportQuery = "SELECT * FROM innlogging where epost = ? and passord = ?;";

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = con.prepareStatement(exportQuery);

            pstmt.setString(1, "email");
            pstmt.setString(2, "password");

            ResultSet rs = pstmt.executeQuery();


            if (rs.next()) {
                return true;
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
