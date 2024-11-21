package org.app.database;

import org.app.core.repositories.LoggInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoggDBImplementation implements LoggInterface {

    private final Connection connection;

    public LoggDBImplementation(Connection connection) {
        this.connection = connection;
    }
    @Override
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
