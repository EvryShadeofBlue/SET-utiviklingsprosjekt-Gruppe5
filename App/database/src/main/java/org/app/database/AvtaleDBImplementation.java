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
        //String loggForOpprettelseQuery = "insert into loggføring (bruker_id, bruker_type, handling, objekt_id, objekt_type) " +
         //       "values (?, 'pårørende', 'avtale opprettet', ?, 'avtale')";
        try (PreparedStatement avtaleStatement = connection.prepareStatement(opprettAvtaleQuery, Statement.RETURN_GENERATED_KEYS)) {
            avtaleStatement.setString(1, avtale.getBeskrivelse());
            avtaleStatement.setObject(2, avtale.getDatoOgTid());
            if (avtale.getGjentakelse() != null) {
                avtaleStatement.setString(3, avtale.getGjentakelse());
            }
            else {
                avtaleStatement.setNull(3, Types.VARCHAR);
            }

            if (avtale.getSluttDato() != null) {
                avtaleStatement.setObject(4, avtale.getSluttDato());
            }
            else {
                avtaleStatement.setNull(4, Types.TIMESTAMP);
            }
            avtaleStatement.setInt(5, avtale.getParorende().getParorendeId());
            avtaleStatement.setInt(6, avtale.getPleietrengende().getPleietrengendeId());
            avtaleStatement.executeUpdate();

            ResultSet generatedKeys = avtaleStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int avtaleId = generatedKeys.getInt(1);
//                try (PreparedStatement loggStatement = connection.prepareStatement(loggForOpprettelseQuery)) {
//                    loggStatement.setInt(1, avtale.getParorende().getParorendeId());
//                    loggStatement.setInt(2, avtaleId);
//                    loggStatement.executeUpdate();
//                }
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public void oppdaterAvtale(Avtale avtale) {
        String oppdaterAvtaleQuery = "update Avtaler set beskrivelse = ?, dato_og_tid = ?, gjentakelse = ?, slutt_dato = ?, pleietrengende_id = ?, parorende_id = ? " +
                "where avtale_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(oppdaterAvtaleQuery)) {
            preparedStatement.setString(1, avtale.getBeskrivelse());
            preparedStatement.setObject(2, avtale.getDatoOgTid());
            if (avtale.getGjentakelse() != null) {
                preparedStatement.setString(3, avtale.getGjentakelse());
            }
            else {
                preparedStatement.setNull(3, Types.VARCHAR);
            }
            if (avtale.getSluttDato() != null) {
                preparedStatement.setObject(4, avtale.getSluttDato());
            }
            else {
                preparedStatement.setNull(4, Types.TIMESTAMP);
            }
            preparedStatement.setInt(5, avtale.getParorende().getParorendeId());
            preparedStatement.setInt(6, avtale.getPleietrengende().getPleietrengendeId());
            preparedStatement.setInt(7, avtale.getAvtaleId());
            preparedStatement.executeUpdate();

            loggService.loggføring(avtale.getParorende().getParorendeId(), "pårørende", "avtale endret", avtale.getAvtaleId(), "avtale");
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            sqlException.getMessage();
        }

    }

    @Override
    public void slettAvtale(int avtaleId) {
        String sletteAvtaleQuery = "delete from Avtaler where avtale_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sletteAvtaleQuery)) {
            preparedStatement.setInt(1, avtaleId);
            preparedStatement.executeUpdate();
        }
        catch (SQLException sqlException) {
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