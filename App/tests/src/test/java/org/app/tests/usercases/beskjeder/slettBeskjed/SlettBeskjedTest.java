package org.app.tests.usercases.beskjeder.slettBeskjed;

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
public class SlettBeskjedTest {
    @Mock
    BeskjedRepository mockBeskjedRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;

    @Test
    @DisplayName("Sletting av eksisterende beskjed lykkes")
    public void slettEksisterendeBeskjed() {
        // Arrange
        int gyldigBeskjedId = 1;
        Mockito.doNothing().when(mockBeskjedRepo).slettBeskjed(gyldigBeskjedId);
        SlettBeskjedLogikk slettBeskjedLogikk = new SlettBeskjedLogikk(mockBeskjedRepo);

        // Act
        boolean result = slettBeskjedLogikk.slettBeskjed(gyldigBeskjedId);

        // Assert
        Assertions.assertTrue(result, "Beskjeden burde ha blitt slettet uten feil.");
        Mockito.verify(mockBeskjedRepo, Mockito.times(1)).slettBeskjed(gyldigBeskjedId);
    }

}
