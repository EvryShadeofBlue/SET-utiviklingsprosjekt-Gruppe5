package org.app.core.models;

import org.app.core.repository.BeskjedRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        String oppdaterBeskjedQuery = "UPDATE Beskjeder SET beskrivelse = ?, dato_tid = ?, synlig_tid = ?, parorende_id = ?, pleietrengende_id = ? WHERE beskjed_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(oppdaterBeskjedQuery)) {
            preparedStatement.setString(1, beskjed.getBeskrivelse());
            preparedStatement.setObject(2, beskjed.getDatoOgTid());
            preparedStatement.setInt(3, beskjed.getSynligTidsenhet());
            preparedStatement.setInt(4, beskjed.getParorende().getParorendeId());
            preparedStatement.setInt(5, beskjed.getPleietrengende().getPleietrengendeId());
            preparedStatement.setInt(6, beskjed.getBeskjedId());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }


    @Override
    public void slettBeskjed(int beskjedId) {
        String slettBeskjedQuery = "DELETE FROM Beskjeder WHERE beskjed_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(slettBeskjedQuery)) {
            preparedStatement.setInt(1, beskjedId);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }


    @Override
    public Beskjed hentBeskjed(int beskjedId) {
        String hentBeskjedQuery = "SELECT beskjed_id, beskrivelse, dato_tid, synlig_tid, parorende_id, pleietrengende_id FROM Beskjeder WHERE beskjed_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(hentBeskjedQuery)) {
            preparedStatement.setInt(1, beskjedId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("beskjed_id");
                String beskrivelse = resultSet.getString("beskrivelse");
                LocalDateTime datoOgTid = resultSet.getObject("dato_tid", LocalDateTime.class);
                int synligTid = resultSet.getInt("synlig_tid");
                int parorendeId = resultSet.getInt("parorende_id");
                int pleietrengendeId = resultSet.getInt("pleietrengende_id");

                // Opprett Parorende- og Pleietrengende-objekter med kun ID-er
                Parorende parorende = new Parorende();
                parorende.setParorendeId(parorendeId);

                Pleietrengende pleietrengende = new Pleietrengende();
                pleietrengende.setPleietrengendeId(pleietrengendeId);

                // Opprett og returner Beskjed-objektet
                Beskjed beskjed = new Beskjed(id, datoOgTid, beskrivelse, synligTid, pleietrengende, parorende);
                return beskjed;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }




    @Override
    public List<Beskjed> hentBeskjedForParorende(int parorendeId) {
        List<Beskjed> beskjeder = new ArrayList<>();
        String beskjedForParorendeQuery = "select * from Beskjeder where parorende_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(beskjedForParorendeQuery)) {
            preparedStatement.setInt(1, parorendeId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int beskjedId = resultSet.getInt("beskjed_id");
                String beskrivelse = resultSet.getString("beskrivelse");
                LocalDateTime datoOgTid = resultSet.getTimestamp("dato_tid").toLocalDateTime();
                int synligTidsenhet = resultSet.getInt("synlig_tid");

                Beskjed beskjed = new Beskjed(beskjedId, datoOgTid, beskrivelse, synligTidsenhet);
                beskjeder.add(beskjed);
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return beskjeder;
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