import java.sql.*;

public class Import {

    public static void main(String[] args) {
    String url = "jdbc:mysql://localhost:3306/testing";
    String user = "root";
    String password = "12345";

    String insertQuery = "INSERT INTO pasient (fornavn, etternavn) VALUES (?, ?)";

        try {
        Connection con = DriverManager.getConnection(url, user, password);

        PreparedStatement pstmt = con.prepareStatement(insertQuery);

        pstmt.setString(1, "Lois");
        pstmt.setString(2, "Griffin");

        int rowsAffected = pstmt.executeUpdate();

        System.out.println("Rows inserted: " + rowsAffected);

        pstmt.close();
        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}