package org.screen.database;

import org.screen.core.models.Avtale;
import org.screen.core.models.Beskjed;
import org.screen.core.models.Resources;
import org.screen.core.repositories.DataExportInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Export implements DataExportInterface {
    String url = Resources.getUrl();
    String user = Resources.getUser();
    String password = Resources.getPassword();

    @Override
    public List<Beskjed> exportBeskjeder(int pleietrengende_id) {
        List<Beskjed> beskjederList = new ArrayList<>();
        String exportQuery = "SELECT beskrivelse, dato_tid, synlig_tid " +
                "FROM Beskjeder " +
                "WHERE DATE(dato_tid) = CURDATE() " +
                "AND pleietrengende_id = " + pleietrengende_id + " " +
                "ORDER BY dato_tid ASC;";

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = con.prepareStatement(exportQuery);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String description = rs.getString("beskrivelse");
                String dateTime = rs.getString("dato_tid");
                int visibleTime = rs.getInt("synlig_tid");

                Beskjed beskjeder = new Beskjed(description, dateTime, visibleTime);
                beskjederList.add(beskjeder);
            }

            rs.close();
            pstmt.close();
            con.close();

        } catch (SQLException e) {
            System.err.println("SQL error occurred.");
            e.printStackTrace();
        }

        return beskjederList;
    }

    @Override
    public List<Avtale> exportAvtalerToday(int pleietrengende_id) {
        List<Avtale> avtaleList = new ArrayList<>();
        String exportQuery = "SELECT beskrivelse, TIME(dato_og_tid) AS tid " +
                "FROM Avtaler " +
                "WHERE DATE(dato_og_tid) = CURDATE() " +
                "AND pleietrengende_id = " + pleietrengende_id + " " +
                "ORDER BY dato_og_tid ASC;";

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = con.prepareStatement(exportQuery);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String description = rs.getString("beskrivelse");
                String dateTime = rs.getString("tid");

                Avtale avtaler = new Avtale(description, dateTime);
                avtaleList.add(avtaler);
            }

            rs.close();
            pstmt.close();
            con.close();

        } catch (SQLException e) {
            System.err.println("SQL error occurred.");
            e.printStackTrace();
        }

        return avtaleList;
    }

    @Override
    public List<Avtale> exportAvtalerTomorrow(int pleietrengende_id) {
        List<Avtale> avtaleList = new ArrayList<>();
        String exportQuery = "SELECT beskrivelse, TIME(dato_og_tid) AS tid " +
                "FROM Avtaler " +
                "WHERE DATE(dato_og_tid) = DATE_ADD(CURDATE(), INTERVAL 1 DAY) " +
                "AND pleietrengende_id = " + pleietrengende_id + " " +
                "ORDER BY dato_og_tid ASC;";

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = con.prepareStatement(exportQuery);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String description = rs.getString("beskrivelse");
                String dateTime = rs.getString("tid");

                Avtale avtaler = new Avtale(description, dateTime);
                avtaleList.add(avtaler);
            }

            rs.close();
            pstmt.close();
            con.close();

        } catch (SQLException e) {
            System.err.println("SQL error occurred.");
            e.printStackTrace();
        }

        return avtaleList;
    }

}
