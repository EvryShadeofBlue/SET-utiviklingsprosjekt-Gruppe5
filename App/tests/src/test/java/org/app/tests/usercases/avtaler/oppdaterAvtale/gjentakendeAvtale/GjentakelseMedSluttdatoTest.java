package org.app.tests.usercases.avtaler.oppdaterAvtale.gjentakendeAvtale;

import org.app.core.models.Avtale;
import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.repositories.AvtaleRepository;
import org.app.core.logikk.AvtaleLogikk;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class GjentakelseMedSluttdatoTest {
    @Mock
    AvtaleRepository mockAvtaleRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;

    @Test
    @DisplayName("Oppdater avale uten gjentakelse til daglig gjentakelse med sluttdato")
    public void oppdaterAvtaleTilDagligGjentakelse() {
        //Arrange
        LocalDateTime startDato = LocalDateTime.of(2024, 12, 1, 14, 0);
        LocalDateTime sluttDato = startDato.plusDays(5);
        Avtale eksisterendeAvtale = new Avtale(startDato, "Besøk til pleietrengende", mockParorende, mockPleietrengende);
        Avtale nyAvtale = new Avtale();
        nyAvtale.setGjentakelse("daglig");
        nyAvtale.setSluttDato(sluttDato);

        //Act
        AvtaleLogikk avtaleService = new AvtaleLogikk(mockAvtaleRepo);
        boolean result = avtaleService.oppdaterAvtale(eksisterendeAvtale, nyAvtale);

        //Assert
        Assertions.assertTrue(result, "Oppdatering av avtale til daglig gjentakelse med sluttdato skal være vellykket");
        Assertions.assertEquals("daglig", eksisterendeAvtale.getGjentakelse(), "Gjentakelsen bør være oppdatert");
        Assertions.assertEquals(sluttDato, eksisterendeAvtale.getSluttDato(), "Sluttdato bør være oppdatert");
        Mockito.verify(mockAvtaleRepo, Mockito.times(5)).opprettAvtale(Mockito.any(Avtale.class));
    }
}
