package org.app.core.avtaler;

import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.models.Resources;
import org.app.core.repository.AvtaleRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AvtaleDBImplementation implements AvtaleRepository {
    private Connection connection;

    public AvtaleDBImplementation() {
        try {
            connection = DriverManager.getConnection(Resources.url, Resources.user, Resources.password);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public void oppretteAvtale(Avtale avtale) {
        String opprettAvtaleQuery = "Insert into Avtaler (beskrivelse, dato_og_tid, gjentakelse, slutt_dato, pleietrengende_id, parorende_id) values (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(opprettAvtaleQuery)) {
            preparedStatement.setString(1, avtale.getBeksrivelse());
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
            preparedStatement.executeUpdate();
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
            preparedStatement.setString(1, avtale.getBeksrivelse());
            preparedStatement.setObject(2, avtale.getDatoOgTid());
            preparedStatement.setString(3, avtale.getGjentakelse());
            preparedStatement.setObject(4, avtale.getSluttDato());
            preparedStatement.setInt(5, avtale.getParorende().getParorendeId());
            preparedStatement.setInt(6, avtale.getPleietrengende().getPleietrengendeId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
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
        String avtaleForParorendeQuery = "select * from Avtaler where parorende_id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(avtaleForParorendeQuery)) {
            preparedStatement.setInt(1, parorendeId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int avtaleId = resultSet.getInt("avtale_id");
                String beskrivelse = resultSet.getString("beskrivelse");
                LocalDateTime datoOgTid = resultSet.getTimestamp("dato_og_tid").toLocalDateTime();
                String gjentakelse = resultSet.getString("gjentakelse");
                LocalDateTime sluttdato = resultSet.getTimestamp("slutt_dato").toLocalDateTime();

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
