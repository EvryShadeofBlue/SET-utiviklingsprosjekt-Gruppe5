package org.screen.core.database;

import org.screen.core.models.Beskjed;
import org.screen.core.models.Resources;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Export {
    String url = Resources.getUrl();
    String user = Resources.getUser();
    String password = Resources.getPassword();

    public List<Beskjed> exportBeskjederToday(int pleietrengende_id) {
        List<Beskjed> beskjederList = new ArrayList<>();
        String exportQuery = "select beskrivelse, dato_tid, synlig_tid\n" +
                "from Beskjeder\n" +
                "where pleietrengende_id = ?\n" +
                "and DATE(dato_tid) = CURDATE()\n" +
                "order by dato_tid asc;";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = con.prepareStatement(exportQuery);
            pstmt.setInt(1, pleietrengende_id);

            ResultSet rs = pstmt.executeQuery(exportQuery);

            while (rs.next()) {
                String description = rs.getString("beskrivelse");
                String dateTime = rs.getString("dato_tid");
                int visibleTime = rs.getInt("synlig_tid");

                Beskjed beskjeder = new Beskjed(description, dateTime, visibleTime);
                beskjederList.add(beskjeder);
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return beskjederList;
    }

}
