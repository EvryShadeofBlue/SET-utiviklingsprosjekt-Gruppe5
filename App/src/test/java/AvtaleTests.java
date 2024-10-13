import model.Avtale;
import model.AvtaleService;
import net.bytebuddy.asm.Advice;
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
    @DisplayName("Oppdaterer eksisterende avtale")
    public void oppdaterAvtale() {
        //Arrange
        LocalDateTime gammelDato = LocalDateTime.of(2024, 10, 11, 10, 0);
        LocalDateTime nyDato = LocalDateTime.of(2024, 10, 12, 14, 0);
        Avtale avtale = new Avtale(1, gammelDato, "gammel beskrivelse");
        AvtaleService avtaleService = new AvtaleService(mockAvtaleRepository);

        Mockito.when(mockAvtaleRepository.hentAvtale(1)).thenReturn(avtale);

        // Act
        Avtale oppdatertAvtale = avtaleService.oppdaterAvtale(1, "ny beskrivelse", nyDato);

        // Assert
        Assertions.assertNotNull(oppdatertAvtale);
        Assertions.assertEquals("ny beskrivelse", oppdatertAvtale.getBeksrivelse());
        Assertions.assertEquals(nyDato, oppdatertAvtale.getDatoOgTid());

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




