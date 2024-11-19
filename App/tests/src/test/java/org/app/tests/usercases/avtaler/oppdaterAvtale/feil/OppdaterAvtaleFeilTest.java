package org.app.tests.usercases.avtaler.oppdaterAvtale.feil;

import org.app.core.logikk.avtale.OppdaterAvtaleLogikk;
import org.app.core.models.Avtale;
import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.repositories.AvtaleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class OppdaterAvtaleFeilTest {
    @Mock
    AvtaleRepository mockAvtaleRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;

    String beskrivelse = "Tannlegetime";

    @Test
    @DisplayName("Kan ikke oppdatere gjentakelse fra ingen til daglig, ukentlig eller månedlig")
    public void kanIkkeOppdatereGjentakelseFraIngenTilGyldig() {
        // Arrange
        LocalDateTime startDatoTid = LocalDateTime.of(2024, 11, 20, 14, 0);
        Avtale eksisterendeAvtale = new Avtale(startDatoTid, beskrivelse, mockParorende, mockPleietrengende);
        eksisterendeAvtale.setGjentakelse("Ingen");

        // Act
        Avtale nyAvtaleMedDagligGjentakelse = new Avtale(startDatoTid, beskrivelse, mockParorende, mockPleietrengende);
        nyAvtaleMedDagligGjentakelse.setGjentakelse("Daglig");

        // Assert
        OppdaterAvtaleLogikk oppdaterAvtaleLogikk = new OppdaterAvtaleLogikk(mockAvtaleRepo);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            oppdaterAvtaleLogikk.oppdaterAvtale(eksisterendeAvtale, nyAvtaleMedDagligGjentakelse);
        }, "Det skal ikke være mulig å oppdatere gjentakelsen fra ingen til daglig.");

        // Act
        Avtale nyAvtaleMedUkentligGjentakelse = new Avtale(startDatoTid, beskrivelse, mockParorende, mockPleietrengende);
        nyAvtaleMedUkentligGjentakelse.setGjentakelse("Ukentlig");

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            oppdaterAvtaleLogikk.oppdaterAvtale(eksisterendeAvtale, nyAvtaleMedUkentligGjentakelse);
        }, "Det skal ikke være mulig å oppdatere gjentakelsen fra ingen til ukentlig.");

        // Act
        Avtale nyAvtaleMedManedligGjentakelse = new Avtale(startDatoTid, beskrivelse, mockParorende, mockPleietrengende);
        nyAvtaleMedManedligGjentakelse.setGjentakelse("Månedlig");

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            oppdaterAvtaleLogikk.oppdaterAvtale(eksisterendeAvtale, nyAvtaleMedManedligGjentakelse);
        }, "Det skal ikke være mulig å oppdatere gjentakelsen fra ingen til månedlig.");
    }

    @Test
    void kanIkkeSetteSluttdatoForIkkeGjentakendeAvtale() {
        // Arrange
        Avtale eksisterendeAvtale = new Avtale();
        eksisterendeAvtale.setGjentakelse("Ingen");
        eksisterendeAvtale.setDatoOgTid(LocalDateTime.of(2024, 11, 20, 14, 0));
        eksisterendeAvtale.setBeskrivelse("Opprinnelig beskrivelse");

        Avtale nyAvtale = new Avtale();
        nyAvtale.setSluttDato(LocalDateTime.of(2024, 12, 20, 23, 59));
        nyAvtale.setDatoOgTid(LocalDateTime.of(2024, 11, 21, 14, 0));

        // Act & Assert
        OppdaterAvtaleLogikk oppdaterAvtaleLogikk = new OppdaterAvtaleLogikk(mockAvtaleRepo);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            oppdaterAvtaleLogikk.oppdaterAvtale(eksisterendeAvtale, nyAvtale);
        }, "Det skal ikke være mulig å legge til sluttdato i en ikke-gjentakende avtale.");
    }

    @Test
    @DisplayName("Kan ikke oppdatere en avtale uten beskrivelse")
    void kanIkkeOppdatereMedTomBeskrivelse() {
        // Arrange
        Avtale eksisterendeAvtale = new Avtale();
        eksisterendeAvtale.setGjentakelse("Ingen");
        eksisterendeAvtale.setDatoOgTid(LocalDateTime.of(2024, 11, 20, 14, 0));
        eksisterendeAvtale.setBeskrivelse("Opprinnelig beskrivelse");

        Avtale nyAvtale = new Avtale();
        nyAvtale.setDatoOgTid(LocalDateTime.of(2024, 11, 21, 14, 0));
        nyAvtale.setBeskrivelse(null);

        // Act & Assert
        OppdaterAvtaleLogikk oppdaterAvtaleLogikk = new OppdaterAvtaleLogikk(mockAvtaleRepo);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            oppdaterAvtaleLogikk.oppdaterAvtale(eksisterendeAvtale, nyAvtale);
        }, "Beskrivelsen kan ikke være tom.");
    }

    @Test
    @DisplayName("Kan ikke oppdater en avtale uten dato og tid")
    void kanIkkeOppdatereUtenDatoOgTid() {
        // Arrange
        Avtale eksisterendeAvtale = new Avtale();
        eksisterendeAvtale.setGjentakelse("Ingen");
        eksisterendeAvtale.setDatoOgTid(LocalDateTime.of(2024, 11, 20, 14, 0));
        eksisterendeAvtale.setBeskrivelse("Opprinnelig beskrivelse");

        Avtale nyAvtale = new Avtale();
        nyAvtale.setDatoOgTid(null);
        nyAvtale.setBeskrivelse("Oppdatert beskrivelse");

        // Act & Assert
        OppdaterAvtaleLogikk oppdaterAvtaleLogikk = new OppdaterAvtaleLogikk(mockAvtaleRepo);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            oppdaterAvtaleLogikk.oppdaterAvtale(eksisterendeAvtale, nyAvtale);
        }, "Dato og tid må spesifiseres for oppdateringen.");
    }
}
