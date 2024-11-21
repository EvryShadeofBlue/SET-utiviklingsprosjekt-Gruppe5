package org.app.database;

import org.app.core.models.*;
import org.app.core.repositories.AvtaleRepository;
import org.app.core.repositories.LoggInterface;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AvtaleDBImplementation implements AvtaleRepository {
    String url = Resources.getUrl();
    String user = Resources.getUser();
    String password = Resources.getPassword();

    private Connection connection;
    LoggInterface loggInterface = new LoggDBImplementation(connection);

    public AvtaleDBImplementation(Connection connection, LoggInterface loggInterface) {
        this.connection = connection;
        this.loggInterface = loggInterface;
    }

    public AvtaleDBImplementation() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public boolean opprettAvtale(Avtale avtale) throws NoSuchAlgorithmException {
        String query = "INSERT INTO Avtaler (beskrivelse, dato_og_tid, gjentakelse, slutt_dato, pleietrengende_id, parorende_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement opprettStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            opprettStatement.setString(1, Resources.encrypt(avtale.getBeskrivelse(), Resources.getAESKey()));
            opprettStatement.setObject(2, avtale.getDatoOgTid());
            opprettStatement.setString(3, avtale.getGjentakelse() != null ? avtale.getGjentakelse() : null);
            opprettStatement.setObject(4, avtale.getSluttDato() != null ? avtale.getSluttDato() : null);
            opprettStatement.setInt(5, avtale.getPleietrengende().getPleietrengendeId());
            opprettStatement.setInt(6, avtale.getParorende().getParorendeId());

            int affectedRows = opprettStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating avtale failed, no rows affected");
            }

            ResultSet generatedKeys = opprettStatement.getGeneratedKeys();

            int avtaleId;
            if (generatedKeys.next()) {
                avtaleId = generatedKeys.getInt(1);

                loggInterface.loggføring(avtale.getParorende().getParorendeId(), "pårørende",
                        "avtale opprettet", avtaleId, "avtale");
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean oppdaterAvtale(Avtale avtale) {
        if (avtale.getGjentakelse() != null &&
                !avtale.getGjentakelse().equalsIgnoreCase("Ingen") &&
                !avtale.getGjentakelse().isEmpty()) {
            System.out.println("Avtaler med gjentakelse kan ikke oppdateres.");
            return false;
        }

        String oppdaterAvtaleQuery = "UPDATE Avtaler SET beskrivelse = ?, dato_og_tid = ?, " +
                "pleietrengende_id = ?, parorende_id = ? WHERE avtale_id = ?";

        try (PreparedStatement oppdaterStatement = connection.prepareStatement(oppdaterAvtaleQuery)) {

            oppdaterStatement.setString(1, Resources.encrypt(avtale.getBeskrivelse(), Resources.getAESKey()));
            oppdaterStatement.setObject(2, avtale.getDatoOgTid());
            oppdaterStatement.setInt(3, avtale.getPleietrengende().getPleietrengendeId());
            oppdaterStatement.setInt(4, avtale.getParorende().getParorendeId());
            oppdaterStatement.setInt(5, avtale.getAvtaleId());

            int rowsUpdated = oppdaterStatement.executeUpdate();

            if (rowsUpdated > 0) {
                loggInterface.loggføring(avtale.getParorende().getParorendeId(), "pårørende",
                        "avtale oppdatert", avtale.getAvtaleId(), "avtale");
                return true;
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean slettAvtale(int avtaleId) {
        String slettAvtaleQuery = "DELETE FROM Avtaler WHERE avtale_id = ?";
        String hentParorendeQuery = "SELECT parorende_id FROM Avtaler WHERE avtale_id = ?";

        try (PreparedStatement hentParorendeStatement = connection.prepareStatement(hentParorendeQuery)) {
            hentParorendeStatement.setInt(1, avtaleId);
            ResultSet resultSet = hentParorendeStatement.executeQuery();

            if (resultSet.next()) {
                int parorendeId = resultSet.getInt("parorende_id");

                try (PreparedStatement slettStatement = connection.prepareStatement(slettAvtaleQuery)) {
                    slettStatement.setInt(1, avtaleId);
                    int rowsAffected = slettStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        loggInterface.loggføring(parorendeId, "pårørende", "avtale slettet",
                                avtaleId, "avtale");
                        return true;
                    }
                }
            } else {
                System.out.println("Avtale with ID " + avtaleId + " not found.");
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return false;
    }


    @Override
    public Avtale hentAvtale(int avtaleId) {
        String hentAvtaleQuery = "SELECT avtale_id, beskrivelse, dato_og_tid, gjentakelse, slutt_dato, pleietrengende_id, parorende_id " +
                "FROM Avtaler WHERE avtale_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(hentAvtaleQuery)) {
            preparedStatement.setInt(1, avtaleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("avtale_id");
                String beskrivelse = Resources.decrypt(resultSet.getString("beskrivelse"), Resources.getAESKey());
                LocalDateTime datoOgTid = resultSet.getObject("dato_og_tid", LocalDateTime.class);
                int pleietrengendeId = resultSet.getInt("pleietrengende_id");
                int parorendeId = resultSet.getInt("parorende_id");
                String gjentakelsesType = resultSet.getString("gjentakelse");
                LocalDateTime sluttDato = resultSet.getObject("slutt_dato", LocalDateTime.class);

                Parorende parorende = new Parorende();
                parorende.setParorendeId(parorendeId);

                Pleietrengende pleietrengende = new Pleietrengende();
                pleietrengende.setPleietrengendeId(pleietrengendeId);

                Avtale avtale = new Avtale(id, datoOgTid, beskrivelse, gjentakelsesType, sluttDato, parorende, pleietrengende);
                return avtale;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public List<Avtale> hentAvtalerForParorende(Parorende parorende) {
        List<Avtale> avtaler = new ArrayList<>();

        String hentAvtalerQuery = "SELECT avtale_id, beskrivelse, dato_og_tid, gjentakelse, slutt_dato, pleietrengende_id, parorende_id " +
                "FROM Avtaler WHERE parorende_id = ? ORDER BY dato_og_tid DESC";

        try (PreparedStatement preparedStatement = connection.prepareStatement(hentAvtalerQuery)) {
            preparedStatement.setInt(1, parorende.getParorendeId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int avtaleId = resultSet.getInt("avtale_id");
                String beskrivelse = Resources.decrypt(resultSet.getString("beskrivelse"), Resources.getAESKey());
                LocalDateTime datoOgTid = resultSet.getObject("dato_og_tid", LocalDateTime.class);
                String gjentakelse = resultSet.getString("gjentakelse");
                LocalDateTime sluttDato = resultSet.getObject("slutt_dato", LocalDateTime.class);
                int pleietrengendeId = resultSet.getInt("pleietrengende_id");
                int parorendeId = resultSet.getInt("parorende_id");

                parorende.setParorendeId(parorendeId);

                Pleietrengende pleietrengende = new Pleietrengende();
                pleietrengende.setPleietrengendeId(pleietrengendeId);

                Avtale avtale = new Avtale(avtaleId, datoOgTid, beskrivelse, gjentakelse, sluttDato, parorende, pleietrengende);
                avtaler.add(avtale);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return avtaler;
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

    @Override
    public List<Avtale> hentAlleAvtaler() {
        List<Avtale> avtaler = new ArrayList<>();

        String hentAvtalerQuery = "SELECT avtale_id, dato_og_tid, gjentakelse, slutt_dato FROM" +
                " Avtaler ORDER BY dato_og_tid DESC";

        try (PreparedStatement preparedStatement = connection.prepareStatement(hentAvtalerQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int avtaleId = resultSet.getInt("avtale_id");
                LocalDateTime datoOgTid = resultSet.getObject("dato_og_tid", LocalDateTime.class);
                String gjentakelse = resultSet.getString("gjentakelse");
                LocalDateTime sluttDato = resultSet.getObject("slutt_dato", LocalDateTime.class);

                Avtale avtale = new Avtale(avtaleId, datoOgTid, gjentakelse, sluttDato);
                avtaler.add(avtale);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return avtaler;
    }

}

