package org.screen.core.logikk;

import org.screen.core.models.Avtale;
import org.screen.core.models.Beskjed;
import org.screen.core.models.Resources;
import org.screen.core.repositories.DataExportInterface;

import java.util.List;

public class DataLogikk {

    private final DataExportInterface dataExport;

    public DataLogikk(DataExportInterface dataExport) {
        this.dataExport = dataExport;
    }

    public List<Beskjed> getBeskjeder(int pleietrengendeId) {
        return dataExport.exportBeskjeder(pleietrengendeId);
    }

    public List<Avtale> getAvtalerToday(int pleietrengendeId) {
        return dataExport.exportAvtalerToday(pleietrengendeId);
    }

    public List<Avtale> getAvtalerTomorrow(int pleietrengendeId) {
        return dataExport.exportAvtalerTomorrow(pleietrengendeId);
    }

    public String getBeskjederHtml(int pleietrengendeId) throws Exception {
        List<Beskjed> beskjederList = getBeskjeder(pleietrengendeId);
        StringBuilder beskjeder = new StringBuilder("<html>");
        for (Beskjed beskjed : beskjederList) {
            beskjeder.append("").append(beskjed.getDateTime()).append("<br>");
            try {
                beskjeder.append("Beskjed: ").append(Resources.decrypt(beskjed.getDescription(), Resources.getAESKey())).append("<br>");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        beskjeder.append("</html>");
        return beskjeder.toString();
    }

    public String getAvtalerHtml(int pleietrengendeId, boolean today) throws Exception {
        List<Avtale> avtaleList = today ? getAvtalerToday(pleietrengendeId) : getAvtalerTomorrow(pleietrengendeId);
        StringBuilder avtaler = new StringBuilder("<html>");
        for (Avtale avtale : avtaleList) {
            avtaler.append("").append(avtale.getDateTime()).append("<br>");
            try {
                avtaler.append("Avtale: ").append(Resources.decrypt(avtale.getDescription(), Resources.getAESKey())).append("<br>");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        avtaler.append("</html>");
        return avtaler.toString();
    }


}
