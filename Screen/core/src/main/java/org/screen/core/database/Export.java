package org.screen.core.database;

import org.screen.core.models.Beskjed;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Export {
    String url = "jdbc:mysql://localhost:3306/project";
    String user = "root";
    String password = "12345";

    public List<Beskjed> exportBeskjeder() {
        List<Beskjed> beskjederList = new ArrayList<>();
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

                Beskjed beskjeder = new Beskjed(id, dateCreated, description,
                        dateTime, visibleTime, pleietrengende_id, parorende_id);
                beskjederList.add(beskjeder);
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return beskjederList;
    }

}
