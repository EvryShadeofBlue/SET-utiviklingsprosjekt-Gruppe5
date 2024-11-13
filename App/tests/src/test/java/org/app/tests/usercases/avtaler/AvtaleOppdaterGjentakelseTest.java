package org.app.tests.usercases.avtaler;

import org.app.core.models.Avtale;
import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.repositories.AvtaleRepository;
import org.app.core.services.AvtaleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class AvtaleOppdaterGjentakelseTest {
    @Mock
    AvtaleRepository mockAvtaleRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;

    @Test
    @DisplayName("Oppdater avtale uten gjentakelse til daglig gjentakelse uten sluttdato")
    public void oppdaterAvtaleGjentakelseUtenSluttdato() {
        //Arrange
        LocalDateTime datoOgTid = LocalDateTime.of(2024, 11, 14, 12, 0);
        Avtale eksisterendeAvtale = new Avtale(datoOgTid, "Besøk hos pleietrengende", mockParorende, mockPleietrengende);
        Avtale nyAvtale = new Avtale();
        nyAvtale.setGjentakelse("daglig");

        Mockito.when(mockAvtaleRepo.oppdaterAvtale(eksisterendeAvtale)).thenReturn(true);

        //Act
        AvtaleService avtaleService = new AvtaleService(mockAvtaleRepo);
        boolean result = avtaleService.oppdaterAvtale(eksisterendeAvtale, nyAvtale);

        //Assert
        Assertions.assertTrue(result, "Oppdatering av avtale til daglig gjentakelse skal være vellykket");
        Assertions.assertEquals("daglig", eksisterendeAvtale.getGjentakelse(), "Gjentakelsen skal være oppdatert");
        Assertions.assertNull(eksisterendeAvtale.getSluttDato(), "Sluttdato skal ikke eksistere");
    }
}
