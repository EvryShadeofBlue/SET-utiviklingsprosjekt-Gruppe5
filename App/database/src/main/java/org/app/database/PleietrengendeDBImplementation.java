package org.app.database;

import org.app.core.models.Pleietrengende;
import org.app.core.models.Resources;
import org.app.core.repositories.PleietrengendeRepository;

import java.sql.*;

public class PleietrengendeDBImplementation implements PleietrengendeRepository {

    String url = Resources.getUrl();
    String user = Resources.getUser();
    String password = Resources.getPassword();

    private Connection connection;
    public PleietrengendeDBImplementation() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public Pleietrengende finnPleietrengendeAvParorende(int parorendeId) {
        String finnParorendeQuery = "select pleietrengende_id, fornavn, etternavn from Pleietrengende where parorende_id = ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(finnParorendeQuery)) {
            preparedStatement.setInt(1, parorendeId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int pleietrengendeId = resultSet.getInt("pleietrengende_id");
                String fornavn = resultSet.getString("fornavn");
                String etternavn = resultSet.getString("etternavn");

                Pleietrengende pleietrengende = new Pleietrengende();
                pleietrengende.setPleietrengendeId(pleietrengendeId);
                pleietrengende.setFornavn(fornavn);
                pleietrengende.setEtternavn(etternavn);

                return pleietrengende;
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    @Override
    public void lagrePleietrengende(Pleietrengende pleietrengende) {
        String leggTilPleietrengendeQuery = "insert into Pleietrengende (fornavn, etternavn, parorende_id) " +
                "values (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(leggTilPleietrengendeQuery)) {
            preparedStatement.setString(1, pleietrengende.getFornavn());
            preparedStatement.setString(2, pleietrengende.getEtternavn());
            if (pleietrengende.getParorende() != null) {
                preparedStatement.setInt(3, pleietrengende.getParorende().getParorendeId());
            }
            else {
                throw new IllegalArgumentException("Pårørende må være tilknyttet en pleietrengende. ");
            }

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int pleietrengendeId = generatedKeys.getInt(1);
                    pleietrengende.setPleietrengendeId(pleietrengendeId);
                }
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    public int hentPleietrengendeId(String fornavn, String etternavn) {
        String selectQuery = "SELECT pleietrengende_id FROM Pleietrengende WHERE fornavn = ? AND etternavn = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setString(1, fornavn);
            preparedStatement.setString(2, etternavn);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("pleietrengende_id");
                }
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return -1;
    }
}