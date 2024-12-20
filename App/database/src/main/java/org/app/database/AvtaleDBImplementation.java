package org.app.database;

import org.app.core.models.*;
import org.app.core.repositories.AvtaleRepository;

import javax.crypto.SecretKey;
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
        String loggForOpprettelseQuery = "insert into loggføring (bruker_id, bruker_type, handling, objekt_id, objekt_type) " +
                "values (?, ?, ?, ?, ?)";
        try (PreparedStatement opprettStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement loggStatement = connection.prepareStatement(loggForOpprettelseQuery)) {
            opprettStatement.setString(1, Cryption.encrypt(avtale.getBeskrivelse(), Cryption.getAESKey()));
            opprettStatement.setObject(2, avtale.getDatoOgTid());
            if (avtale.getGjentakelse() != null) {
                opprettStatement.setString(3, avtale.getGjentakelse());
            }
            else {
                opprettStatement.setNull(3, Types.VARCHAR);
            }
            if (avtale.getSluttDato() != null) {
                opprettStatement.setObject(4, avtale.getSluttDato());
            }
            else {
                opprettStatement.setNull(4, Types.TIMESTAMP);
            }
            opprettStatement.setInt(5, avtale.getPleietrengende().getPleietrengendeId());
            opprettStatement.setInt(6, avtale.getParorende().getParorendeId());

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

        String loggOppdateringQuery = "INSERT INTO loggføring (bruker_id, bruker_type, handling, objekt_id, objekt_type) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement oppdaterStatement = connection.prepareStatement(oppdaterAvtaleQuery);
             PreparedStatement loggStatement = connection.prepareStatement(loggOppdateringQuery)) {

            oppdaterStatement.setString(1, Cryption.encrypt(avtale.getBeskrivelse(), Cryption.getAESKey()));
            oppdaterStatement.setObject(2, avtale.getDatoOgTid());
            oppdaterStatement.setInt(3, avtale.getPleietrengende().getPleietrengendeId());
            oppdaterStatement.setInt(4, avtale.getParorende().getParorendeId());
            oppdaterStatement.setInt(5, avtale.getAvtaleId());

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
            return false;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
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
        String hentAvtaleQuery = "SELECT avtale_id, beskrivelse, dato_og_tid, gjentakelse, slutt_dato, pleietrengende_id, parorende_id " +
                "FROM Avtaler WHERE avtale_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(hentAvtaleQuery)) {
            preparedStatement.setInt(1, avtaleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("avtale_id");
                String beskrivelse = Cryption.decrypt(resultSet.getString("beskrivelse"), Cryption.getAESKey());
                LocalDateTime datoOgTid = resultSet.getObject("dato_og_tid", LocalDateTime.class);
                int pleietrengendeId = resultSet.getInt("pleietrengende_id");
                int parorendeId = resultSet.getInt("parorende_id");
                String gjentakelsesType = resultSet.getString("gjentakelse");
                LocalDateTime sluttDato = resultSet.getObject("slutt_dato", LocalDateTime.class);

                Parorende parorende = new Parorende();
                parorende.setParorendeId(parorendeId);

                Pleietrengende pleietrengende = new Pleietrengende();
                pleietrengende.setPleietrengendeId(pleietrengendeId);

                Avtale avtale = new Avtale(id, datoOgTid, beskrivelse, gjentakelsesType, sluttDato,pleietrengende, parorende);
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
                String beskrivelse = Cryption.decrypt(resultSet.getString("beskrivelse"), Cryption.getAESKey());
                LocalDateTime datoOgTid = resultSet.getObject("dato_og_tid", LocalDateTime.class);
                String gjentakelse = resultSet.getString("gjentakelse");
                LocalDateTime sluttDato = resultSet.getObject("slutt_dato", LocalDateTime.class);
                int pleietrengendeId = resultSet.getInt("pleietrengende_id");
                int parorendeId = resultSet.getInt("parorende_id");

                parorende.setParorendeId(parorendeId);

                Pleietrengende pleietrengende = new Pleietrengende();
                pleietrengende.setPleietrengendeId(pleietrengendeId);

                Avtale avtale = new Avtale(avtaleId, datoOgTid, beskrivelse, gjentakelse, sluttDato, pleietrengende, parorende);
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

