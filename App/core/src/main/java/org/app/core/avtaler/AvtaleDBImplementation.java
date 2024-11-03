package org.app.core.avtaler;

import org.app.core.models.Resources;
import org.app.core.repository.AvtaleRepository;

import java.sql.*;
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
        String opprettAvtaleQuery = "Insert into Avtaler (beskrivelse, dato_og_tid, gjentakelse, slutt_dato, parorende_id, pleietrengende_id) values (?, ?, ?, ?, ?, ?)";
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

    }

    @Override
    public Avtale hentAvtale(int avtaleId) {
        return null;
    }

    @Override
    public void slettAvtale(int avtaleId) {

    }

    @Override
    public List<Avtale> hentAvtaleForParorende(int parorendeId) {
        return null;
    }
}
