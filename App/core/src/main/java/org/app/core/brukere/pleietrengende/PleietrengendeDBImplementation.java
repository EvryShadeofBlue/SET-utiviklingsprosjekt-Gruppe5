package org.app.core.brukere.pleietrengende;

import org.app.core.brukere.pleietrengende.Pleietrengende;
import org.app.core.models.Resources;
import org.app.core.repository.PleietrengendeRepository;

import java.sql.*;

public class PleietrengendeDBImplementation implements PleietrengendeRepository {
    private Connection connection;
    public PleietrengendeDBImplementation() {
        try {
            connection = DriverManager.getConnection(Resources.url, Resources.user, Resources.password);
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
}
