package org.app.tests.usercases.avtaler.opprettAvtale;

import org.app.core.logikk.avtale.OpprettAvtaleLogikk;
import org.app.core.models.Avtale;
import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.repositories.AvtaleRepository;
import org.app.core.logikk.avtale.AvtaleLogikk;
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
        OpprettAvtaleLogikk opprettAvtaleLogikk = new OpprettAvtaleLogikk(mockAvtaleRepo);
        boolean result = opprettAvtaleLogikk.opprettAvtale(avtale);

        //Assert
        Assertions.assertTrue(result, "Opprettelsen av avtalen bør vellykket.");
    }

    @Test
    @DisplayName("Feil ved manglende beskrivelse")
    public void feilManglendeBeskrivelse() {
        //Arrange
        Avtale avtale = new Avtale(LocalDateTime.now(), "", mockParorende, mockPleietrengende);

        //Act
        OpprettAvtaleLogikk opprettAvtaleLogikk = new OpprettAvtaleLogikk(mockAvtaleRepo);
        boolean result = opprettAvtaleLogikk.opprettAvtale(avtale);

        //Assert
        Assertions.assertFalse(result, "Avtalen skal ikke kunne opprettes uten beskrivelse.");
        Mockito.verify(mockAvtaleRepo, Mockito.never()).opprettAvtale(Mockito.any(Avtale.class));
    }

    @Test
    @DisplayName("Feil ved manglende dato/tid")
    public void feilManglendeDatoTid() {
        //Arrange
        Avtale avtale = new Avtale(null, "legetime", mockParorende, mockPleietrengende);

        //Act
        OpprettAvtaleLogikk opprettAvtaleLogikk = new OpprettAvtaleLogikk(mockAvtaleRepo);
        boolean result = opprettAvtaleLogikk.opprettAvtale(avtale);

        //Assert
        Assertions.assertFalse(result, "Avtalen skal ikke kunne opprettes uten dato/tid.");
        Mockito.verify(mockAvtaleRepo, Mockito.never()).opprettAvtale(Mockito.any(Avtale.class));
    }

    @Test
    @DisplayName("Feil ved manglende dato/tid og beskrivelse")
    public void feilManglendeDatoTidOgBeskrivelse() {
        //Arrange
        Avtale avtale = new Avtale(null, "", mockParorende, mockPleietrengende);

        //Act
        OpprettAvtaleLogikk opprettAvtaleLogikk = new OpprettAvtaleLogikk(mockAvtaleRepo);
        boolean result = opprettAvtaleLogikk.opprettAvtale(avtale);

        //Assert
        Assertions.assertFalse(result, "Avtalen skal ikke kunne opprettes uten dato/tid og beskrivelse.");
        Mockito.verify(mockAvtaleRepo, Mockito.never()).opprettAvtale(Mockito.any(Avtale.class));
    }
}
