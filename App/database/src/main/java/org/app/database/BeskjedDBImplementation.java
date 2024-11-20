package org.app.database;

import org.app.core.models.*;

import org.app.core.repositories.BeskjedRepository;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BeskjedDBImplementation implements BeskjedRepository {

    String url = Resources.getUrl();
    String user = Resources.getUser();
    String password = Resources.getPassword();

    private Connection connection;
    private Parorende parorende;

    public BeskjedDBImplementation() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    @Override
    public void oppretteBeskjed(Beskjed beskjed) {
        String opprettBeskjedQuery = "Insert into Beskjeder (beskrivelse, dato_tid, synlig_tid, parorende_id, pleietrengende_id) Values (?, ?, ?, ?, ?)";
        String loggForOpprettelseQuery = "insert into loggføring (bruker_id, bruker_type, handling, objekt_id, objekt_type) " +
                "values (?, ?, ?, ?, ?)";
        try (PreparedStatement opprettStatement = connection.prepareStatement(opprettBeskjedQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement loggStatement = connection.prepareStatement(loggForOpprettelseQuery)) {

            opprettStatement.setString(1, Resources.encrypt(beskjed.getBeskrivelse(), Resources.getAESKey()));
            opprettStatement.setObject(2, beskjed.getDatoOgTid());
            opprettStatement.setInt(3, beskjed.getSynligTidsenhet());

            opprettStatement.setInt(4, beskjed.getParorende().getParorendeId());
            opprettStatement.setInt(5, beskjed.getPleietrengende().getPleietrengendeId());
            opprettStatement.executeUpdate();

            ResultSet generatedKeys = opprettStatement.getGeneratedKeys();
            int beskjedId = -1;
            if (generatedKeys.next()) {
                beskjedId = generatedKeys.getInt(1);
            }
            loggStatement.setInt(1, beskjed.getParorende().getParorendeId());
            loggStatement.setString(2, "pårørende");
            loggStatement.setString(3, "beskjed opprettet");
            loggStatement.setInt(4, beskjedId);
            loggStatement.setString(5, "beskjed");
            loggStatement.executeUpdate();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void oppdaterBeskjed(Beskjed beskjed) {
        String oppdaterBeskjedQuery = "UPDATE Beskjeder SET beskrivelse = ?, dato_tid = ?, synlig_tid = ?, parorende_id = ?, pleietrengende_id = ? WHERE beskjed_id = ?";
        String loggOppdateringQuery = "insert into loggføring (bruker_id, bruker_type, handling, objekt_id, objekt_type) " +
                "values (?, ?, ?, ?, ?)";
        try (PreparedStatement oppdaterStatement = connection.prepareStatement(oppdaterBeskjedQuery);
             PreparedStatement loggStatement = connection.prepareStatement(loggOppdateringQuery)) {
            oppdaterStatement.setString(1, Resources.encrypt(beskjed.getBeskrivelse(), Resources.getAESKey()));
            oppdaterStatement.setObject(2, beskjed.getDatoOgTid());
            oppdaterStatement.setInt(3, beskjed.getSynligTidsenhet());
            oppdaterStatement.setInt(4, beskjed.getParorende().getParorendeId());
            oppdaterStatement.setInt(5, beskjed.getPleietrengende().getPleietrengendeId());
            oppdaterStatement.setInt(6, beskjed.getBeskjedId());
            oppdaterStatement.executeUpdate();

            loggStatement.setInt(1, beskjed.getParorende().getParorendeId());
            loggStatement.setString(2, "pårørende");
            loggStatement.setString(3, "beskjed oppdatert");
            loggStatement.setInt(4, beskjed.getBeskjedId());
            loggStatement.setString(5, "beskjed");
            loggStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void slettBeskjed(int beskjedId) {
        int parorende_id = 0;

        String hentParorendeQuery = "SELECT parorende_id FROM Beskjeder WHERE beskjed_id = ?";
        String slettBeskjedQuery = "DELETE FROM Beskjeder WHERE beskjed_id = ?";
        String loggSlettQuery = "insert into loggføring (bruker_id, bruker_type, handling, objekt_id, objekt_type) " +
                "values (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(hentParorendeQuery);
            preparedStatement.setInt(1, beskjedId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int parorendeId = resultSet.getInt("parorende_id");
                Parorende parorende = new Parorende();
                parorende.setParorendeId(parorendeId);
                parorende_id = parorende.getParorendeId();
            }
        } catch(SQLException sqlException){
                sqlException.printStackTrace();
        }

        try {

            PreparedStatement loggStatement = connection.prepareStatement(loggSlettQuery);

            loggStatement.setInt(1, parorende_id);
            loggStatement.setString(2, "pårørende");
            loggStatement.setString(3, "beskjed slettet");
            loggStatement.setInt(4, beskjedId);
            loggStatement.setString(5, "beskjed");
            loggStatement.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        try {
            PreparedStatement slettStatement = connection.prepareStatement(slettBeskjedQuery);
            slettStatement.setInt(1, beskjedId);
            slettStatement.executeUpdate();
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
                String beskrivelse = Resources.decrypt(resultSet.getString("beskrivelse"), Resources.getAESKey());
                LocalDateTime datoOgTid = resultSet.getObject("dato_tid", LocalDateTime.class);
                int synligTid = resultSet.getInt("synlig_tid");
                int parorendeId = resultSet.getInt("parorende_id");
                int pleietrengendeId = resultSet.getInt("pleietrengende_id");

                Parorende parorende = new Parorende();
                parorende.setParorendeId(parorendeId);

                Pleietrengende pleietrengende = new Pleietrengende();
                pleietrengende.setPleietrengendeId(pleietrengendeId);

                Beskjed beskjed = new Beskjed(id, datoOgTid, beskrivelse, synligTid, pleietrengende, parorende);
                return beskjed;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
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
                String beskrivelse = Resources.decrypt(resultSet.getString("beskrivelse"), Resources.getAESKey());
                LocalDateTime datoOgTid = resultSet.getTimestamp("dato_tid").toLocalDateTime();
                int synligTidsenhet = resultSet.getInt("synlig_tid");

                Beskjed beskjed = new Beskjed(beskjedId, datoOgTid, beskrivelse, synligTidsenhet);
                beskjeder.add(beskjed);
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
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

    public List<Beskjed> hentAlleBeskjeder() {
        List<Beskjed> beskjeder = new ArrayList<>();

        String hentBeskjederQuery = "SELECT beskjed_id, dato_tid, synlig_tid FROM Beskjeder ORDER BY dato_tid DESC";

        try (PreparedStatement preparedStatement = connection.prepareStatement(hentBeskjederQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int beskjedId = resultSet.getInt("beskjed_id");
                LocalDateTime datoOgTid = resultSet.getObject("dato_tid", LocalDateTime.class);
                int synligTidsenhet = resultSet.getInt("synlig_tid");

                Beskjed beskjed = new Beskjed(beskjedId, datoOgTid, synligTidsenhet);
                beskjeder.add(beskjed);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return beskjeder;
    }

}