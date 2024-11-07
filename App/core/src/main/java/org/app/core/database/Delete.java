package org.app.core.database;

import org.app.core.models.Resources;

import java.sql.*;

public class Delete {
    String url = Resources.getUrl();
    String user = Resources.getUser();
    String password = Resources.getPassword();

    public void deleteParorende(int parorende_id) {

        String id = String.valueOf(parorende_id);

        String deleteQuery = "delete from parorende where parorende_id=" + id;

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            PreparedStatement pstmt = con.prepareStatement(deleteQuery);

            pstmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deletePleietrengende(int pleietrengende_id) {

        String id = String.valueOf(pleietrengende_id);

        String deleteQuery = "delete from pleietrengende where pleietrengende_id=" + id;

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            PreparedStatement pstmt = con.prepareStatement(deleteQuery);

            pstmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteAvtaler(int avtaler_id) {

        String id = String.valueOf(avtaler_id);

        String deleteQuery = "delete from avtaler where avtaler_id=" + id;

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            PreparedStatement pstmt = con.prepareStatement(deleteQuery);

            pstmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteBeskjeder(int beskjeder_id) {

        String id = String.valueOf(beskjeder_id);

        String deleteQuery = "delete from beskjder where beskjeder_id=" + id;

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            PreparedStatement pstmt = con.prepareStatement(deleteQuery);

            pstmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteHandleliste(int handleliste_id) {

        String id = String.valueOf(handleliste_id);

        String deleteQuery = "delete from handleliste where handleliste_id=" + id;

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            PreparedStatement pstmt = con.prepareStatement(deleteQuery);

            pstmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteHandlelisteVarer(int vare_id) {

        String id = String.valueOf(vare_id);

        String deleteQuery = "delete from handlelisteVarer where vare_id=" + id;

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            PreparedStatement pstmt = con.prepareStatement(deleteQuery);

            pstmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteInnlogging(int innlogging_id) {

        String id = String.valueOf(innlogging_id);

        String deleteQuery = "delete from innlogging where innlogging_id=" + id;

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            PreparedStatement pstmt = con.prepareStatement(deleteQuery);

            pstmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
