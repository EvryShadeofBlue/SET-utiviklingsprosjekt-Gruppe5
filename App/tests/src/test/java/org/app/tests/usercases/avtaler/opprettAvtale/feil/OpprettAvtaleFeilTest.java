package org.app.tests.usercases.avtaler.opprettAvtale.feil;

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
public class OpprettAvtaleFeilTest {
    @Mock
    AvtaleRepository mockAvtaleRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;

    @Test
    @DisplayName("Oppretter avtale uten beskrivelse")
    public void opprettAvtaleUtenBeskrivelse() {
        //Arrange
        Avtale avtale = new Avtale(LocalDateTime.now(), null, mockParorende, mockPleietrengende);

        //Act og Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            OpprettAvtaleLogikk opprettAvtaleLogikk = new OpprettAvtaleLogikk(mockAvtaleRepo);
            opprettAvtaleLogikk.opprettAvtale(avtale);
        }, "Beskrivelse kan ikke være tom.");
    }

    @Test
    @DisplayName("Oppretter avtale uten dato og tid")
    public void feilManglendeDatoTid() {
        //Arrange
        Avtale avtale = new Avtale(null, "legetime", mockParorende, mockPleietrengende);

        //Act og Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            OpprettAvtaleLogikk opprettAvtaleLogikk = new OpprettAvtaleLogikk(mockAvtaleRepo);
            opprettAvtaleLogikk.opprettAvtale(avtale);
        }, "Dato og klokkeslett kan ikke være tom.");
    }

    @Test
    @DisplayName("Feil ved manglende dato/tid og beskrivelse")
    public void feilManglendeDatoTidOgBeskrivelse() {
        //Arrange
        Avtale avtale = new Avtale(null, null, mockParorende, mockPleietrengende);

        //Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            OpprettAvtaleLogikk opprettAvtaleLogikk = new OpprettAvtaleLogikk(mockAvtaleRepo);
            opprettAvtaleLogikk.opprettAvtale(avtale);
            }, "Beskrivelse kan ikke være tom");


    }
}

