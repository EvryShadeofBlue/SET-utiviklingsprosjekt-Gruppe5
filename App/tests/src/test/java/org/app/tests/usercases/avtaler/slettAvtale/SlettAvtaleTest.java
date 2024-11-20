package org.app.tests.usercases.avtaler.slettAvtale;

import org.app.core.logikk.avtale.OpprettAvtaleLogikk;
import org.app.core.logikk.avtale.SlettAvtaleLogikk;
import org.app.core.models.Avtale;
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

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class SlettAvtaleTest {
    @Mock
    AvtaleRepository mockAvtaleRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;

    @Test
    @DisplayName("Sletter gyldig avtale")
    public void slettGyldigAvtale() {
        // Arrange
        int avtaleId = 1;
        Avtale avtale = new Avtale(LocalDateTime.now(), "Legetime", mockParorende, mockPleietrengende);
        Mockito.when(mockAvtaleRepo.hentAvtale(avtaleId)).thenReturn(avtale);
        Mockito.when(mockAvtaleRepo.slettAvtale(avtaleId)).thenReturn(true);

        // Act
        SlettAvtaleLogikk slettAvtaleLogikk = new SlettAvtaleLogikk(mockAvtaleRepo);
        boolean result = slettAvtaleLogikk.slettAvtale(avtaleId);

        // Assert
        Assertions.assertTrue(result, "Avtalen bør slettes vellykket.");
    }

}
