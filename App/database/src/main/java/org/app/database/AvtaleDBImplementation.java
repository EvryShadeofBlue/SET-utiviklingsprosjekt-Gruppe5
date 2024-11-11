package org.app.database;

import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.models.Resources;
import org.app.core.services.LoggService;
import org.app.core.repositories.AvtaleRepository;
import org.app.core.models.Avtale;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AvtaleDBImplementation implements AvtaleRepository {
    String url = Resources.getUrl();
    String user = Resources.getUser();
    String password = Resources.getPassword();

    private Connection connection;

    public AvtaleDBImplementation() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public boolean opprettAvtale(Avtale avtale) {
        String query = "INSERT INTO Avtaler (beskrivelse, dato_og_tid, pleietrengende_id, parorende_id) " +
                "VALUES (?, ?, ?, ?)";
        String loggForOpprettelseQuery = "insert into loggføring (bruker_id, bruker_type, handling, objekt_id, objekt_type) " +
                "values (?, ?, ?, ?, ?)";
        try (PreparedStatement opprettStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement loggStatement = connection.prepareStatement(loggForOpprettelseQuery)) {
            opprettStatement.setString(1, avtale.getBeskrivelse());
            opprettStatement.setTimestamp(2, java.sql.Timestamp.valueOf(avtale.getDatoOgTid()));  // Assuming Avtale's dato_og_tid is a LocalDateTime
            opprettStatement.setInt(3, avtale.getPleietrengende().getPleietrengendeId());
            opprettStatement.setInt(4, avtale.getParorende().getParorendeId());

            opprettStatement.executeUpdate();

            ResultSet generatedKeys = opprettStatement.getGeneratedKeys();

            int avtaleId = -1;
            if (generatedKeys.next()) {
                avtaleId = generatedKeys.getInt(1);
            }
            loggStatement.setInt(1, avtale.getParorende().getParorendeId());
            loggStatement.setString(2, "pårørende");
            loggStatement.setString(3, "avtale opprettet");
            loggStatement.setInt(4, avtaleId);
            loggStatement.setString(5, "avtale");
            loggStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean oppdaterAvtale(Avtale avtale) {
        if (avtale.getSluttDato() != null && avtale.getGjentakelse() == null) {
            return false;
        }

        String oppdaterAvtaleQuery = "UPDATE Avtaler SET beskrivelse = ?, dato_og_tid = ?, sluttdato = ?, gjentakelse = ?, pleietrengende_id = ?, parorende_id = ? " +
                "WHERE avtale_id = ?";
        String loggOppdateringQuery = "INSERT INTO loggføring (bruker_id, bruker_type, handling, objekt_id, objekt_type) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement oppdaterStatement = connection.prepareStatement(oppdaterAvtaleQuery);
            PreparedStatement loggStatement = connection.prepareStatement(loggOppdateringQuery)) {
            oppdaterStatement.setString(1, avtale.getBeskrivelse());
            oppdaterStatement.setObject(2, avtale.getDatoOgTid());
            if (avtale.getSluttDato() != null) {
                oppdaterStatement.setObject(3, avtale.getSluttDato());
            }
            else {
                oppdaterStatement.setNull(3, Types.TIMESTAMP);
            }
            oppdaterStatement.setString(4, avtale.getGjentakelse());
            oppdaterStatement.setInt(5, avtale.getPleietrengende().getPleietrengendeId());
            oppdaterStatement.setInt(6, avtale.getParorende().getParorendeId());
            oppdaterStatement.setInt(7, avtale.getAvtaleId());
            int rowsUpdated = oppdaterStatement.executeUpdate();

            if (rowsUpdated > 0) {
                loggStatement.setInt(1, avtale.getParorende().getParorendeId());
                loggStatement.setString(2, "pårørende");
                loggStatement.setString(3, "avtale oppdatert");
                loggStatement.setInt(4, avtale.getAvtaleId());
                loggStatement.setString(5, "avtale");
                loggStatement.executeUpdate();
                return true;
            }
            else {
                return false;
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean slettAvtale(int avtaleId) {
        int parorendeId = 0;

        String hentParorendeQuery = "SELECT parorende_id FROM Avtaler WHERE avtale_id = ?";
        String slettAvtaleQuery = "DELETE FROM Avtaler WHERE avtale_id = ?";
        String loggOppdateringQuery = "INSERT INTO loggføring (bruker_id, bruker_type, handling, objekt_id, objekt_type) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(hentParorendeQuery);
            preparedStatement.setInt(1, avtaleId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                parorendeId = resultSet.getInt("parorende_id");
            }
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }

        try (PreparedStatement loggStatement = connection.prepareStatement(loggOppdateringQuery)) {
            loggStatement.setInt(1, parorendeId);
            loggStatement.setString(2, "pårørende");
            loggStatement.setString(3, "avtale slettet");
            loggStatement.setInt(4, avtaleId);
            loggStatement.setString(5, "avtale");
            loggStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        try (PreparedStatement slettStatement = connection.prepareStatement(slettAvtaleQuery)) {
            slettStatement.setInt(1, avtaleId);
            int rowsAffected = slettStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return false;
    }


    @Override
    public Avtale hentAvtale(int avtaleId) {
        String hentAvtaleQuery = "SELECT avtale_id, beskrivelse, dato_og_tid, pleietrengende_id, parorende_id " +
                "FROM Avtaler WHERE avtale_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(hentAvtaleQuery)) {
            preparedStatement.setInt(1, avtaleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("avtale_id");
                String beskrivelse = resultSet.getString("beskrivelse");
                LocalDateTime datoOgTid = resultSet.getObject("dato_og_tid", LocalDateTime.class);
                int pleietrengendeId = resultSet.getInt("pleietrengende_id");
                int parorendeId = resultSet.getInt("parorende_id");

                Parorende parorende = new Parorende();
                parorende.setParorendeId(parorendeId);

                Pleietrengende pleietrengende = new Pleietrengende();
                pleietrengende.setPleietrengendeId(pleietrengendeId);

                Avtale avtale = new Avtale(id, datoOgTid, beskrivelse, pleietrengende, parorende);
                return avtale;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Avtale> hentAvtalerForParorende(Parorende parorende) {
        List<Avtale> avtaler = new ArrayList<>();

        String hentAvtalerQuery = "SELECT avtale_id, beskrivelse, dato_og_tid, pleietrengende_id, parorende_id " +
                "FROM Avtaler WHERE parorende_id = ? ORDER BY dato_og_tid DESC";

        try (PreparedStatement preparedStatement = connection.prepareStatement(hentAvtalerQuery)) {
            preparedStatement.setInt(1, parorende.getParorendeId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int avtaleId = resultSet.getInt("avtale_id");
                String beskrivelse = resultSet.getString("beskrivelse");
                LocalDateTime datoOgTid = resultSet.getObject("dato_og_tid", LocalDateTime.class);
                int pleietrengendeId = resultSet.getInt("pleietrengende_id");
                int parorendeId = resultSet.getInt("parorende_id");

                //Parorende parorende = new Parorende();
                parorende.setParorendeId(parorendeId);

                Pleietrengende pleietrengende = new Pleietrengende();
                pleietrengende.setPleietrengendeId(pleietrengendeId);

                Avtale avtale = new Avtale(avtaleId, datoOgTid, beskrivelse, pleietrengende, parorende);
                avtaler.add(avtale);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
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
}

