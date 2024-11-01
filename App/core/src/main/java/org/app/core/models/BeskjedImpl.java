package org.app.core.models;

import org.app.core.repository.BeskjedRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BeskjedImpl implements BeskjedRepository {
    private Connection connection;

    public BeskjedImpl() {
        try {
            connection = DriverManager.getConnection(Resources.url, Resources.user, Resources.password);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    @Override
    public void oppretteBeskjed(Beskjed beskjed) {
        String opprettBeskjedQuery = "Insert into Beskjeder (beskrivelse, dato_tid, synlig_tid, parorende_id, pleietrengende_id) Values (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(opprettBeskjedQuery)) {
            preparedStatement.setString(1, beskjed.getBeskrivelse());
            preparedStatement.setObject(2, beskjed.getDatoOgTid());
            preparedStatement.setInt(3, beskjed.getSynligTidsenhet());

            preparedStatement.setInt(4, beskjed.getParorende().getParorendeId());
            preparedStatement.setInt(5, beskjed.getPleietrengende().getPleietrengendeId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public void oppdaterBeskjed(Beskjed beskjed) {

    }

    @Override
    public void slettBeskjed(int beskjedId) {

    }

    @Override
    public Beskjed hentBeskjed(int beskjedId) {
        return null;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}