import model.Avtale;
import model.AvtaleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.AvtaleRepository;
import java.time.LocalDateTime;



@ExtendWith(MockitoExtension.class)
public class AvtaleTests {

    @Mock
    AvtaleRepository mockAvtaleRepository;

    @Mock
    Avtale mockAvtale;

    @Mock
    AvtaleService avtaleService;

    @Test
    @DisplayName("Opprettelse av avtale")
    public void opprettAvtale() {
        // Arrange
        AvtaleService avtaleService = new AvtaleService(mockAvtaleRepository);
        int avtaleId = 1;
        LocalDateTime datoOgTid = LocalDateTime.of(2024, 10, 13, 14, 0);
        String beskrivelse = "Legebesøk";


        // Act
        avtaleService.opprettAvtale(avtaleId, datoOgTid, beskrivelse, null, null);

        // Assert
        Mockito.verify(mockAvtaleRepository).lagreAvtale(Mockito.any(Avtale.class));
        Mockito.verify(mockAvtaleRepository, Mockito.times(1)).lagreAvtale(Mockito.any(Avtale.class));

    }

    @Test
    @DisplayName("Oppretter avtale med gjentakelse")
    public void opprettGjentakendeAvtale() {
        // Arrange
        AvtaleService avtaleService = new AvtaleService(mockAvtaleRepository);
        int avtaleId = 3;
        LocalDateTime  datoOgTid = LocalDateTime.of(2024, 10, 20, 10, 0);
        String beskrivelse = "Fysioterapi";
        String gjentakelse = "ukentlig";
        LocalDateTime sluttDato = LocalDateTime.of(2025, 4, 20, 10 , 0);

        // Act
        avtaleService.opprettAvtale(avtaleId, datoOgTid, beskrivelse, gjentakelse, sluttDato);

        // Assert
        Mockito.verify(mockAvtaleRepository, Mockito.times(2)).lagreAvtale(Mockito.any(Avtale.class));
    }



    @Test
    @DisplayName("Oppdaterer eksisterende avtale")
    public void oppdaterAvtale() {
        AvtaleService avtaleService = new AvtaleService(mockAvtaleRepository);
        int avtaleId = 1;

        //Arrange
        // Oppretter eksisterende avtale
        String eksisterendeBeskrivelse = "Gammel beskrivelse";
        LocalDateTime eksisterendeDato = LocalDateTime.of(2024, 10, 11, 10, 0);


        Avtale eksisterendeAvtale = new Avtale(1, eksisterendeDato, eksisterendeBeskrivelse);


        Mockito.when(mockAvtaleRepository.hentAvtale(1)).thenReturn(eksisterendeAvtale);

        // Nye verdier
        String nyBeskrivelse = "Ny beskrivelse";
        LocalDateTime nyDatoOgTid = LocalDateTime.of(2024, 10, 12, 14, 0);

        // Act
        Avtale oppdatertAvtale = avtaleService.oppdaterAvtale(1, nyBeskrivelse, nyDatoOgTid);

        // Assert
        Assertions.assertNotNull(oppdatertAvtale);
        Assertions.assertEquals("ny beskrivelse", oppdatertAvtale.getBeksrivelse());
        Assertions.assertEquals(nyDatoOgTid, oppdatertAvtale.getDatoOgTid());

    }

    @Test
    @DisplayName("Sletter eksisterende avtale")
    public void slettAvtale() {
        // Arrange
        AvtaleService avtaleService = new AvtaleService(mockAvtaleRepository);

        Mockito.when(mockAvtaleRepository.hentAvtale(1)).thenReturn(null);

        // Act
        boolean slettet = avtaleService.slettAvtale(1);

        // Assert
        Assertions.assertFalse(slettet);
        Mockito.verify(mockAvtaleRepository, Mockito.never()).slettAvtale(1);


    }
}




