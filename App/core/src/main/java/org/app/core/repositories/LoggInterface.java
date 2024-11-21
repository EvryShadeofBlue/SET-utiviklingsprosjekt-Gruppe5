package org.app.core.repositories;

public interface LoggInterface {
    void loggføring(int brukerId, String brukerType, String handling, Integer objektId, String objektType);
}
