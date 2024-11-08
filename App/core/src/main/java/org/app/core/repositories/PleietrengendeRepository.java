package org.app.core.repositories;

import org.app.core.models.Pleietrengende;

public interface PleietrengendeRepository {
    void lagrePleietrengende(Pleietrengende pleietrengende);

    Pleietrengende finnPleietrengendeAvParorende(int parorendeId);
}
