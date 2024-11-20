package org.app.tests.usercases.avtaler.opprettAvtale;

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
public class OpprettAvtaleTest {
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
        OpprettAvtaleLogikk opprettAvtaleLogikk = new OpprettAvtaleLogikk(mockAvtaleRepo);
        boolean result = opprettAvtaleLogikk.opprettAvtale(avtale);

        //Assert
        Assertions.assertTrue(result, "Opprettelsen av avtalen bør vellykket.");
    }

    @Test
    @DisplayName("Oppretter en avtale med fremtidig dato")
    public void opprettAvtaleMedFremtidigDato() {
        //Arrange
        LocalDateTime fremtidigDato = LocalDateTime.now().plusDays(7);
        Avtale avtale = new Avtale(fremtidigDato, "Fysioterapeut", mockParorende, mockPleietrengende);
        Mockito.when(mockAvtaleRepo.opprettAvtale(Mockito.any(Avtale.class))).thenReturn(true);

        //Act
        OpprettAvtaleLogikk opprettAvtaleLogikk = new OpprettAvtaleLogikk(mockAvtaleRepo);
        boolean result = opprettAvtaleLogikk.opprettAvtale(avtale);

        //Assert
        Assertions.assertTrue(result, "Avtalen med fremditig dato bør være opprettet");
    }

    @Test
    @DisplayName("Oppretter avtale med spesialtegn i beskrivelse")
    public void opprettAvtaleMedSpesialtegnBeskrivelse() {
        //Arrange
        String beskrivelse = "Legetime: #haster!";
        Avtale avtale = new Avtale(LocalDateTime.now(), beskrivelse, mockParorende, mockPleietrengende);
        Mockito.when(mockAvtaleRepo.opprettAvtale(Mockito.any(Avtale.class))).thenReturn(true);

        //Act
        OpprettAvtaleLogikk opprettAvtaleLogikk = new OpprettAvtaleLogikk(mockAvtaleRepo);
        boolean result = opprettAvtaleLogikk.opprettAvtale(avtale);

        //Assert
        Assertions.assertTrue(result, "Avtalen med spesialtegn bør være opprettet");
    }
}
