package org.app.tests.usercases.beskjeder.opprettBeskjed;

import org.app.core.logikk.beskjed.OpprettBeskjedLogikk;
import org.app.core.models.Beskjed;
import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.repositories.AvtaleRepository;
import org.app.core.repositories.BeskjedRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class OpprettBeskjedTest {
    @Mock
    BeskjedRepository mockBeskjedRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;
    @Test
    @DisplayName("Oppretter gyldig beskjed")
    public void opprettGyldigBeskjed() {
        // Arrange
        Beskjed beskjed = new Beskjed(LocalDateTime.now(), "Møte med lege", 12, mockParorende, mockPleietrengende);

        Mockito.doNothing().when(mockBeskjedRepo).oppretteBeskjed(Mockito.any(Beskjed.class));

        // Act
        OpprettBeskjedLogikk opprettBeskjedLogikk = new OpprettBeskjedLogikk(mockBeskjedRepo);
        Beskjed resultatBeskjed = opprettBeskjedLogikk.opprettBeskjed(beskjed.getDatoOgTid(), beskjed.getBeskrivelse(), beskjed.getSynligTidsenhet(), beskjed.getParorende(), beskjed.getPleietrengende());

        // Assert
        Assertions.assertEquals(beskjed.getBeskrivelse(), resultatBeskjed.getBeskrivelse(), "Beskrivelsen skal samsvare.");
        Assertions.assertEquals(beskjed.getDatoOgTid(), resultatBeskjed.getDatoOgTid(), "Dato og tid skal samsvare.");
        Assertions.assertEquals(beskjed.getSynligTidsenhet(), resultatBeskjed.getSynligTidsenhet(), "Synlig tidsenhet skal samsvare.");
        Assertions.assertEquals(beskjed.getParorende(), resultatBeskjed.getParorende(), "Pårørende skal samsvare.");
        Assertions.assertEquals(beskjed.getPleietrengende(), resultatBeskjed.getPleietrengende(), "Pleietrengende skal samsvare.");
    }


}
