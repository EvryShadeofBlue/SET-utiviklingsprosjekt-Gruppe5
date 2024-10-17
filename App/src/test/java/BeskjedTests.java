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
    public void opprettBeskjed() {
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
    public void oppdaterBeskjed() {
        // Arrange
        BeskjedService beskjedService = new BeskjedService(mockBeskjedRepository);
        int beskjedId = 1;
        String eksisterendeBeskrivelse = "Gammel beskjed";
        int eksisterendeSynligTidsenhet = 24;

        Beskjed eksisterendeBeskjed = new Beskjed(eksisterendeBeskrivelse, eksisterendeSynligTidsenhet);
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
}
