package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Export {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String password = "12345";

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
