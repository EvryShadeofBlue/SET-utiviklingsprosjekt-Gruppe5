package org.app.core.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoggService {
    private Connection connection;

    public LoggService(Connection connection) {
        this.connection = connection;
    }

    public void loggføring(int brukerId, String brukerType, String handling, Integer objektId, String objektType) {
        String loggføringQuery = "insert into loggføring (bruker_id, bruker_type, handling, objekt_id, objekt_type) " +
                "values (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(loggføringQuery)) {
            preparedStatement.setInt(1, brukerId);
            preparedStatement.setString(2, brukerType);
            preparedStatement.setString(3, handling);
            preparedStatement.setObject(4, objektId);
            preparedStatement.setObject(5, objektType);
            preparedStatement.executeUpdate();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

}
