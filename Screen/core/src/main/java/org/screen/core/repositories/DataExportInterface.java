package org.screen.core.repositories;

import org.screen.core.models.Avtale;
import org.screen.core.models.Beskjed;

import java.util.List;

public interface DataExportInterface {

    List<Beskjed> exportBeskjeder(int pleietrengendeId);
    List<Avtale> exportAvtalerToday(int pleietrengendeId);
    List<Avtale> exportAvtalerTomorrow(int pleietrengendeId);

}
