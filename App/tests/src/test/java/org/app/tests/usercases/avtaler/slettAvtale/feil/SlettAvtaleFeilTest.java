package org.app.tests.usercases.avtaler.slettAvtale.feil;

import org.app.core.logikk.avtale.SlettAvtaleLogikk;
import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.repositories.AvtaleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SlettAvtaleFeilTest {
    @Mock
    AvtaleRepository mockAvtaleRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;

    @Test
    @DisplayName("Prøve å slette en avtale som ikke eksisterer")
    public void slettAvtaleSomIkkeFinnes() {
        // Arrange
        int avtaleId = 999;
        Mockito.when(mockAvtaleRepo.hentAvtale(avtaleId)).thenReturn(null);
        // Act
        SlettAvtaleLogikk slettAvtaleLogikk = new SlettAvtaleLogikk(mockAvtaleRepo);
        boolean result = slettAvtaleLogikk.slettAvtale(avtaleId);

        // Assert
        Assertions.assertFalse(result, "Avtalen ble ikke funnet, så sletting bør feile.");
    }
}
