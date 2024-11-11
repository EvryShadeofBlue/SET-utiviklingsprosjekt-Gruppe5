package org.app.database;

import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.services.LoggService;
import org.app.core.models.Resources;
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
    private LoggService loggService;

    public AvtaleDBImplementation() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public void oppretteAvtale(Avtale avtale) {
        String opprettAvtaleQuery = "Insert into Avtaler (beskrivelse, dato_og_tid, gjentakelse, slutt_dato, pleietrengende_id, parorende_id) values (?, ?, ?, ?, ?, ?)";
        String loggForOpprettelseQuery = "insert into loggføring (bruker_id, bruker_type, handling, objekt_id, objekt_type) " +
                "values (?, ?, ?, ?, ?)";

        try (PreparedStatement opprettStatement = connection.prepareStatement(opprettAvtaleQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement loggStatement = connection.prepareStatement(loggForOpprettelseQuery)) {

            opprettStatement.setString(1, avtale.getBeskrivelse());
            opprettStatement.setObject(2, avtale.getDatoOgTid());
            opprettStatement.setObject(3, avtale.getGjentakelse());
            opprettStatement.setObject(4, avtale.getSluttDato());
            opprettStatement.setInt(5, avtale.getParorende().getParorendeId());
            opprettStatement.setInt(6, avtale.getPleietrengende().getPleietrengendeId());
            opprettStatement.executeUpdate();

            ResultSet generatedKeys = opprettStatement.getGeneratedKeys();
            int beskjedId = -1;
            if (generatedKeys.next()) {
                beskjedId = generatedKeys.getInt(1);
            }
            loggStatement.setInt(1, avtale.getParorende().getParorendeId());
            loggStatement.setString(2, "pårørende");
            loggStatement.setString(3, "avtale opprettet");
            loggStatement.setInt(4, beskjedId);
            loggStatement.setString(5, "avtale");
            loggStatement.executeUpdate();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public void oppdaterAvtale(Avtale avtale) {
        String oppdaterAvtaleQuery = "update Avtaler set beskrivelse = ?, dato_og_tid = ?, gjentakelse = ?, slutt_dato = ?, pleietrengende_id = ?, parorende_id = ? " +
                "where avtale_id = ?";
        String loggOppdateringQuery = "insert into loggføring (bruker_id, bruker_type, handling, objekt_id, objekt_type) " +
                "values (?, ?, ?, ?, ?)";
        try (PreparedStatement oppdaterStatement = connection.prepareStatement(oppdaterAvtaleQuery);
             PreparedStatement loggStatement = connection.prepareStatement(loggOppdateringQuery)) {
            oppdaterStatement.setString(1, avtale.getBeskrivelse());
            oppdaterStatement.setObject(2, avtale.getDatoOgTid());
            oppdaterStatement.setString(3, avtale.getGjentakelse());
            oppdaterStatement.setObject(4, avtale.getSluttDato());
            oppdaterStatement.setInt(5, avtale.getParorende().getParorendeId());
            oppdaterStatement.setInt(6, avtale.getPleietrengende().getPleietrengendeId());
            oppdaterStatement.setInt(7, avtale.getAvtaleId());
            oppdaterStatement.executeUpdate();

            loggStatement.setInt(1, avtale.getParorende().getParorendeId());
            loggStatement.setString(2, "pårørende");
            loggStatement.setString(3, "avtale oppdatert");
            loggStatement.setInt(4, avtale.getAvtaleId());
            loggStatement.setString(5, "avtale");
            loggStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public void slettAvtale(int avtaleId) {
        int parorende_id = 0;

        String hentParorendeQuery = "SELECT parorende_id FROM Avtaler WHERE avtale_id = ?";
        String slettBeskjedQuery = "DELETE FROM Avtaler WHERE avtale_id = ?";
        String loggSlettQuery = "insert into loggføring (bruker_id, bruker_type, handling, objekt_id, objekt_type) " +
                "values (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(hentParorendeQuery);
            preparedStatement.setInt(1, avtaleId);
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
            loggStatement.setString(3, "avtale slettet");
            loggStatement.setInt(4, avtaleId);
            loggStatement.setString(5, "avtale");
            loggStatement.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        try {
            PreparedStatement slettStatement = connection.prepareStatement(slettBeskjedQuery);
            slettStatement.setInt(1, avtaleId);
            slettStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public Avtale hentAvtale(int avtaleId) {
        String hentAvtaleQuery = "select avtale_id, beskrivelse, dato_og_tid, gjentakelse, slutt_dato, pleietrengende_id, parorende_id " +
                "from Avtaler where avtale_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(hentAvtaleQuery)) {
            preparedStatement.setInt(1, avtaleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("avtale_id");
                String beskrivelse = resultSet.getString("beskrivelse");
                LocalDateTime datoOgTid = resultSet.getObject("dato_og_tid", LocalDateTime.class);
                String gjentakelse = resultSet.getString("gjentakelse");
                LocalDateTime sluttdato = resultSet.getObject("slutt_dato", LocalDateTime.class);
                int parorendeId = resultSet.getInt("parorende_id");
                int pleietrengendeId = resultSet.getInt("pleietrengende_id");

                Parorende parorende = new Parorende();
                parorende.setParorendeId(parorendeId);
                Pleietrengende pleietrengende = new Pleietrengende();
                pleietrengende.setPleietrengendeId(pleietrengendeId);

                Avtale avtale = new Avtale(id, datoOgTid, beskrivelse, gjentakelse, sluttdato, pleietrengende, parorende);
                return avtale;
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Avtale> hentAvtaleForParorende(int parorendeId) {
        List<Avtale> avtaler = new ArrayList<>();
        String avtaleForParorendeQuery = "select * from Avtaler where parorende_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(avtaleForParorendeQuery)) {
            preparedStatement.setInt(1, parorendeId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int avtaleId = resultSet.getInt("avtale_id");
                String beskrivelse = resultSet.getString("beskrivelse");

                Timestamp datoOgTidTimestamp = resultSet.getTimestamp("dato_og_tid");
                LocalDateTime datoOgTid = (datoOgTidTimestamp != null) ? datoOgTidTimestamp.toLocalDateTime() : null;

                String gjentakelse = resultSet.getString("gjentakelse");

                Timestamp sluttDatoTimestamp = resultSet.getTimestamp("slutt_dato");
                LocalDateTime sluttdato = (sluttDatoTimestamp != null) ? sluttDatoTimestamp.toLocalDateTime() : null;

                Avtale avtale = new Avtale(avtaleId, datoOgTid, beskrivelse, gjentakelse, sluttdato);
                avtaler.add(avtale);
            }
        }
        catch (SQLException sqlException) {
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