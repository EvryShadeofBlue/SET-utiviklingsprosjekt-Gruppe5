import java.sql.*;

public class Delete {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/testing";
        String user = "root";
        String password = "12345";

        String deleteQuery = "delete from pasient where pasient_id=4";

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
