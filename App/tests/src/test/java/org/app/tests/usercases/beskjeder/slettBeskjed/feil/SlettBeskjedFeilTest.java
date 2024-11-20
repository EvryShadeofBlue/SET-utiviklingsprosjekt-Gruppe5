package org.app.tests.usercases.beskjeder.slettBeskjed.feil;

import org.app.core.logikk.beskjed.SlettBeskjedLogikk;
import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.repositories.BeskjedRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SlettBeskjedFeilTest {
    @Mock
    BeskjedRepository mockBeskjedRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;

    @Test
    @DisplayName("Sletting feiler når beskjed-ID er ugyldig")
    public void slettUgyldigBeskjed() {
        // Arrange
        int ugyldigBeskjedId = 99;
        Mockito.doThrow(new RuntimeException("Beskjed ikke funnet")).when(mockBeskjedRepo).slettBeskjed(ugyldigBeskjedId);
        SlettBeskjedLogikk slettBeskjedLogikk = new SlettBeskjedLogikk(mockBeskjedRepo);

        // Act
        boolean result = slettBeskjedLogikk.slettBeskjed(ugyldigBeskjedId);

        // Assert
        Assertions.assertFalse(result, "Sletting burde ha feilet for ugyldig beskjed-ID.");
        Mockito.verify(mockBeskjedRepo, Mockito.times(1)).slettBeskjed(ugyldigBeskjedId);
    }

}
