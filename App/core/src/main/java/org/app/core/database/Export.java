package org.app.core.database;

import org.app.core.models.Resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Export {
    String url = Resources.getUrl();
    String user = Resources.getUser();
    String password = Resources.getPassword();

    public void exportParorende() {

        String exportQuery = "SELECT * FROM pasient;";

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(exportQuery);


            while (rs.next()) {
                int id = rs.getInt("pasient_id");
                String firstName = rs.getString("fornavn");
                String lastName = rs.getString("etternavn");

                System.out.println(firstName + " " + lastName);
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
