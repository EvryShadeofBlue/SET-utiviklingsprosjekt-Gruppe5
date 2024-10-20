import models.Beskjed;
import models.BeskjedService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.BeskjedRepository;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class BeskjedTests {
    @Mock
    BeskjedRepository mockBeskjedRepository;

    @Mock
    Beskjed mockBeskjed;

    @Mock
    BeskjedService beskjedService;

    @Test
    @DisplayName("Opprettelse av beskjed")
    public void testOpprettBeskjed() {
        // Arrange
        BeskjedService beskjedService = new BeskjedService(mockBeskjedRepository);
        String beskrivelse = "Beskjed";
        int synligTidsenhet = 24;

        // Act
        Beskjed opprettetBeskjed = beskjedService.opprettBeskjed(beskrivelse, synligTidsenhet);

        //Assert
        Assertions.assertEquals("Beskjed", opprettetBeskjed.getBeskrivelse());
    }

    @Test
    @DisplayName("Endring av beskjed")
    public void testOppdaterBeskjed() {
        // Arrange
        BeskjedService beskjedService = new BeskjedService(mockBeskjedRepository);
        int beskjedId = 1;
        String eksisterendeBeskrivelse = "Gammel beskjed";
        int eksisterendeSynligTidsenhet = 24;
        LocalDateTime eksisterendeDatoOgTid = LocalDateTime.now();

        Beskjed eksisterendeBeskjed = new Beskjed(eksisterendeDatoOgTid, eksisterendeBeskrivelse, eksisterendeSynligTidsenhet);
        Mockito.when(mockBeskjedRepository.hentBeskjed(beskjedId)).thenReturn(eksisterendeBeskjed);

        // Nye verdier
        String nyBeskrivelse = "Oppdatert beskjed";
        int nySynligTidsenhjet = 12;


        // Act
        boolean result = beskjedService.oppdaterBeskjed(beskjedId, nyBeskrivelse, nySynligTidsenhjet);

        // Assert
        Assertions.assertTrue(result, "Oppdatering av beskjed bør være vellykket. ");

        Beskjed oppdatertBeskjed = mockBeskjedRepository.hentBeskjed(beskjedId);

        Assertions.assertEquals(nyBeskrivelse, oppdatertBeskjed.getBeskrivelse());
        Assertions.assertEquals(nySynligTidsenhjet, oppdatertBeskjed.getSynligTidsenhet());
    }

    @Test
    @DisplayName("Sletting av beskjed")
    public void testSlettBeskjed() {
        // Arrange
        BeskjedRepository mockBeskjedRepository = Mockito.mock(BeskjedRepository.class);
        BeskjedService beskjedService = new BeskjedService(mockBeskjedRepository);
        int eksisterendeBeskjedIdId = 1;
        int ikkeEksisterendeBeskjedId = 2;
        LocalDateTime eksisterendeDatoOgTid = LocalDateTime.now();

        Mockito.when(mockBeskjedRepository.hentBeskjed(eksisterendeBeskjedIdId)).thenReturn(new Beskjed(eksisterendeDatoOgTid,"Beskjed", 24));
        Mockito.doNothing().when(mockBeskjedRepository).slettBeskjed(eksisterendeBeskjedIdId);

        // Act
        boolean slettetEksisterende = beskjedService.slettBeskjed(eksisterendeBeskjedIdId);
        boolean slettetIkkeEksisterende = beskjedService.slettBeskjed(ikkeEksisterendeBeskjedId);

        // Assert
        Assertions.assertEquals(true, slettetEksisterende);
        Assertions.assertEquals(false, slettetIkkeEksisterende);
    }
}
