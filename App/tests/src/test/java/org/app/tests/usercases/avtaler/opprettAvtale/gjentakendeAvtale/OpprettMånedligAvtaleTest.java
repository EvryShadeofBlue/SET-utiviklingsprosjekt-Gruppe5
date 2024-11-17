package org.app.tests.usercases.avtaler.opprettAvtale.gjentakendeAvtale;

import org.app.core.logikk.avtale.OpprettAvtaleLogikk;
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
public class OpprettMånedligAvtaleTest {
    @Mock
    AvtaleRepository mockAvtaleRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;

    @Test
    @DisplayName("Oppretter månedlige avtaler")
    public void opprettMånedligeAvtaler() {
        //Arrange
        LocalDateTime dato = LocalDateTime.of(2024, 11, 1, 9, 0);
        LocalDateTime sluttDato = LocalDateTime.of(2025, 1, 5, 23, 59);
        Avtale avtale = new Avtale(dato, "Månedlig oppfølging", "månedlig", sluttDato, mockParorende, mockPleietrengende);

        Mockito.when(mockAvtaleRepo.opprettAvtale(Mockito.any(Avtale.class))).thenReturn(true);

        //Act
        OpprettAvtaleLogikk opprettAvtaleLogikk = new OpprettAvtaleLogikk(mockAvtaleRepo);
        boolean result = opprettAvtaleLogikk.opprettAvtale(avtale);

        //Assert
        Assertions.assertTrue(result, "Avtalen bør være opprettet");
        Mockito.verify(mockAvtaleRepo, Mockito.times(3)).opprettAvtale(Mockito.any(Avtale.class));
    }
}
