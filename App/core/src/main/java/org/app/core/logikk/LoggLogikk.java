package org.app.core.logikk;

import org.app.core.repositories.LoggInterface;

public class LoggLogikk {

    private final LoggInterface loggInterface;

    public LoggLogikk(LoggInterface loggInterface) {
        this.loggInterface = loggInterface;
    }

    public void loggføring(int brukerId, String brukerType, String handling, Integer objektId, String objektType) {
        loggInterface.loggføring(brukerId, brukerType, handling, objektId, objektType);
    }

}
