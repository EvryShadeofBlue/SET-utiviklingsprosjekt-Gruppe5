package Database;

import java.sql.*;

public class Delete {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String password = "12345";

        String column = "pasient_id";
        String table = "pasient";
        int id = 7;
        String idDeleted = String.valueOf(id);

        String deleteQuery = "delete from " + table + " where " + column + "=" + idDeleted;

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            PreparedStatement pstmt = con.prepareStatement(deleteQuery);

            int rowsAffected = pstmt.executeUpdate();

            System.out.println("Rows deleted: " + rowsAffected);

            pstmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}