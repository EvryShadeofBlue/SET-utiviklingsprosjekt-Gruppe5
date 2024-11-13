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
public class AvtaleOpprettTest {
    @Mock
    AvtaleRepository mockAvtaleRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;

    @Test
    @DisplayName("Oppretter gyldig avtale")
    public void opprettGyldigAvtale() {
        //Arrange
        Avtale avtale = new Avtale(LocalDateTime.now(), "Legetime", mockParorende, mockPleietrengende);
        Mockito.when(mockAvtaleRepo.opprettAvtale(Mockito.any(Avtale.class))).thenReturn(true);

        //Act
        AvtaleService avtaleService = new AvtaleService(mockAvtaleRepo);
        boolean result = avtaleService.opprettAvtale(avtale);

        //Assert
        Assertions.assertTrue(result, "Opprettelsen av avtalen bør vellykket.");
    }

    @Test
    @DisplayName("Feil ved manglende beskrivelse")
    public void feilManglendeBeskrivelse() {
        //Arrange
        Avtale avtale = new Avtale(LocalDateTime.now(), "", mockParorende, mockPleietrengende);

        //Act
        AvtaleService avtaleService = new AvtaleService(mockAvtaleRepo);
        boolean result = avtaleService.opprettAvtale(avtale);

        //Assert
        Assertions.assertTrue(result, "Avtalen skal ikke kunne opprettes uten beskrivelse.");
        Mockito.verify(mockAvtaleRepo, Mockito.never()).opprettAvtale(Mockito.any(Avtale.class));
    }

    @Test
    @DisplayName("Feil ved manglende dato/tid")
    public void feilManglendeDatoTid() {
        //Arrange
        Avtale avtale = new Avtale(null, "", mockParorende, mockPleietrengende);

        //Act
        AvtaleService avtaleService = new AvtaleService(mockAvtaleRepo);
        boolean result = avtaleService.opprettAvtale(avtale);

        //Assert
        Assertions.assertTrue(result, "Avtalen skal ikke kunne opprettes uten dato/tid.");
        Mockito.verify(mockAvtaleRepo, Mockito.never()).opprettAvtale(Mockito.any(Avtale.class));
    }


}
