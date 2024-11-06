package org.app.core.repository;

import org.app.core.brukere.Pleietrengende;

public interface PleietrengendeRepository {
    void lagrePleietrengende(Pleietrengende pleietrengende);

    Pleietrengende finnPleietrengendeAvParorende(int parorendeId);
}
