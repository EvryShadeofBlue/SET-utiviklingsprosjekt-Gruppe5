package org.app.tests.usercases.avtaler.slettAvtale.gjentakendeAvtale;

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
public class SlettDagligAvtaleTest {
    @Mock
    AvtaleRepository mockAvtaleRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;

    @Test
    @DisplayName("Sletting av en daglig gjentakende avtale skal lykkes")
    public void slettDagligGjentakendeAvtale() {
        // Arrange
        int avtaleId = 1;
        LocalDateTime dato = LocalDateTime.of(2024, 11, 18, 12, 0);
        LocalDateTime sluttdato = dato.plusDays(7);
        Avtale avtale = new Avtale(avtaleId, dato, "Daglig trening", "daglig", sluttdato, mockParorende, mockPleietrengende);

        Mockito.when(mockAvtaleRepo.opprettAvtale(Mockito.any(Avtale.class))).thenReturn(true);
        Mockito.when(mockAvtaleRepo.hentAvtale(avtaleId)).thenReturn(avtale);
        Mockito.when(mockAvtaleRepo.slettAvtale(avtaleId)).thenReturn(true);

        // Act
        OpprettAvtaleLogikk opprettAvtaleLogikk = new OpprettAvtaleLogikk(mockAvtaleRepo);
        boolean opprettResult = opprettAvtaleLogikk.opprettAvtale(avtale);
        SlettAvtaleLogikk slettAvtaleLogikk = new SlettAvtaleLogikk(mockAvtaleRepo);
        boolean slettResult = slettAvtaleLogikk.slettAvtale(avtaleId);

        // Assert
        Assertions.assertTrue(opprettResult, "Opprettelsen av avtale skal være vellykket");
        Assertions.assertTrue(slettResult, "Sletting av avtalen skal være vellykket");
        Mockito.verify(mockAvtaleRepo, Mockito.times(1)).opprettAvtale(avtale);
        Mockito.verify(mockAvtaleRepo, Mockito.times(1)).hentAvtale(avtaleId);
        Mockito.verify(mockAvtaleRepo, Mockito.times(1)).slettAvtale(avtaleId);
    }


}
