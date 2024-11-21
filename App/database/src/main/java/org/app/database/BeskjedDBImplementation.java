package org.app.database;

import org.app.core.models.*;

import org.app.core.repositories.BeskjedRepository;
import org.app.core.repositories.LoggInterface;

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
    private final LoggInterface loggInterface;

    public BeskjedDBImplementation(Connection connection, LoggInterface loggInterface) {
        this.connection = connection;
        this.loggInterface = loggInterface;
    }


    @Override
    public void oppretteBeskjed(Beskjed beskjed) {
        String opprettBeskjedQuery = "INSERT INTO Beskjeder (beskrivelse, dato_tid, synlig_tid, parorende_id, pleietrengende_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement opprettStatement = connection.prepareStatement(opprettBeskjedQuery, Statement.RETURN_GENERATED_KEYS)) {
            opprettStatement.setString(1, Resources.encrypt(beskjed.getBeskrivelse(), Resources.getAESKey()));
            opprettStatement.setObject(2, beskjed.getDatoOgTid());
            opprettStatement.setInt(3, beskjed.getSynligTidsenhet());
            opprettStatement.setInt(4, beskjed.getParorende().getParorendeId());
            opprettStatement.setInt(5, beskjed.getPleietrengende().getPleietrengendeId());

            opprettStatement.executeUpdate();

            ResultSet generatedKeys = opprettStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int beskjedId = generatedKeys.getInt(1);
                loggInterface.loggføring(beskjed.getParorende().getParorendeId(), "pårørende", "beskjed opprettet", beskjedId, "beskjed");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void oppdaterBeskjed(Beskjed beskjed) {
        String oppdaterBeskjedQuery = "UPDATE Beskjeder SET beskrivelse = ?, dato_tid = ?, synlig_tid = ?, parorende_id = ?, pleietrengende_id = ? WHERE beskjed_id = ?";

        try (PreparedStatement oppdaterStatement = connection.prepareStatement(oppdaterBeskjedQuery)) {
            oppdaterStatement.setString(1, Resources.encrypt(beskjed.getBeskrivelse(), Resources.getAESKey()));
            oppdaterStatement.setObject(2, beskjed.getDatoOgTid());
            oppdaterStatement.setInt(3, beskjed.getSynligTidsenhet());
            oppdaterStatement.setInt(4, beskjed.getParorende().getParorendeId());
            oppdaterStatement.setInt(5, beskjed.getPleietrengende().getPleietrengendeId());
            oppdaterStatement.setInt(6, beskjed.getBeskjedId());

            oppdaterStatement.executeUpdate();

            loggInterface.loggføring(beskjed.getParorende().getParorendeId(), "pårørende", "beskjed oppdatert", beskjed.getBeskjedId(), "beskjed");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void slettBeskjed(int beskjedId) {
        int parorendeId = 0;

        String hentParorendeQuery = "SELECT parorende_id FROM Beskjeder WHERE beskjed_id = ?";
        String slettBeskjedQuery = "DELETE FROM Beskjeder WHERE beskjed_id = ?";

        try (PreparedStatement hentParorendeStatement = connection.prepareStatement(hentParorendeQuery)) {
            hentParorendeStatement.setInt(1, beskjedId);
            ResultSet resultSet = hentParorendeStatement.executeQuery();
            if (resultSet.next()) {
                parorendeId = resultSet.getInt("parorende_id");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return;
        }

        try (PreparedStatement slettStatement = connection.prepareStatement(slettBeskjedQuery)) {
            slettStatement.setInt(1, beskjedId);
            int rowsAffected = slettStatement.executeUpdate();

            if (rowsAffected > 0) {
                loggInterface.loggføring(parorendeId, "pårørende", "beskjed slettet", beskjedId, "beskjed");
            }
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

                Beskjed beskjed = new Beskjed(id, datoOgTid, beskrivelse, synligTid, parorende, pleietrengende);
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