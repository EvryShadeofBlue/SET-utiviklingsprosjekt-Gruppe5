package org.app.database;

import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.models.Resources;

import java.sql.*;

public class PagesDBImplementation {

    static String url = Resources.getUrl();
    static String user = Resources.getUser();
    static String dbPassword = Resources.getPassword();

    public static int insertParorende(String firstName, String lastName, String mobileNumber, String email) throws SQLException {
        String insertParorendeQuery = "INSERT INTO Parorende (fornavn, etternavn, tlf, epost) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(insertParorendeQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, mobileNumber);
            preparedStatement.setString(4, email);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKey = preparedStatement.getGeneratedKeys()) {
                    if (generatedKey.next()) {
                        return generatedKey.getInt(1);
                    }
                }
            }
        }

        throw new SQLException("Could not insert parorende. No ID obtained.");
    }

    public static void insertInnlogging(String email, String password, int parorendeId) throws SQLException {
        String insertInnloggingQuery = "INSERT INTO Innlogging (epost, passord, parorende_id) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, dbPassword);
             PreparedStatement innloggingStatement = connection.prepareStatement(insertInnloggingQuery)) {
            innloggingStatement.setString(1, email);
            innloggingStatement.setString(2, password);
            innloggingStatement.setInt(3, parorendeId);

            innloggingStatement.executeUpdate();
        }
    }

    public static Parorende getParorendeWithLogin(String email, String hashedPassword) throws SQLException {
        String loginQuery = "select i.parorende_id, p.fornavn as parorendeFornavn, p.etternavn as parorendeEtternavn, " +
                "pl.pleietrengende_id, pl.fornavn as pleietrengendeFornavn, pl.etternavn as pleietrengendeEtternavn " +
                "from Innlogging i " +
                "join Parorende p on i.parorende_id = p.parorende_id " +
                "left join Pleietrengende pl on p.parorende_id = pl.parorende_id " +
                "where i.epost = ? and i.passord = ?";

        try (Connection connection = DriverManager.getConnection(url, user,dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(loginQuery)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, hashedPassword);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int parorendeId = resultSet.getInt("parorende_id");
                String parorendeFornavn = resultSet.getString("parorendeFornavn");
                String parorendeEtternavn = resultSet.getString("parorendeEtternavn");

                Pleietrengende pleietrengende = null;
                if (resultSet.getObject("pleietrengende_id") != null) {
                    int pleietrengendeId = resultSet.getInt("pleietrengende_id");
                    String pleietrengendeFornavn = resultSet.getString("pleietrengendeFornavn");
                    String pleietrengendeEtternavn = resultSet.getString("pleietrengendeEtternavn");
                    pleietrengende = new Pleietrengende(pleietrengendeId, pleietrengendeFornavn, pleietrengendeEtternavn);
                }

                Parorende parorende = new Parorende(parorendeId, parorendeFornavn, parorendeEtternavn);
                parorende.setPleietrengende(pleietrengende);
                return parorende;
            }
        }

        return null;
    }

}
