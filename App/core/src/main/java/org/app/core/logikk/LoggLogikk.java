package org.app.core.logikk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoggLogikk {
    private Connection connection;

    public LoggLogikk(Connection connection) {
        this.connection = connection;
    }

    public void loggføring(int brukerId, String brukerType, String handling, Integer objektId, String objektType) {
        String loggføringQuery = "insert into loggføring (bruker_id, bruker_type, handling, objekt_id, objekt_type) " +
                "values (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(loggføringQuery)) {
            preparedStatement.setInt(1, brukerId);
            preparedStatement.setString(2, brukerType);
            preparedStatement.setString(3, handling);
            preparedStatement.setInt(4, objektId);
            preparedStatement.setString(5, objektType);
            preparedStatement.executeUpdate();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

}
