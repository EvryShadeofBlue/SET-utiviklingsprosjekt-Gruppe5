package org.app.tests.usercases.avtaler.slettAvtale.feil;

import org.app.core.logikk.avtale.SlettAvtaleLogikk;
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

@ExtendWith(MockitoExtension.class)
public class SlettAvtaleFeilTest {
    @Mock
    AvtaleRepository mockAvtaleRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;

    @Test
    @DisplayName("Prøve å slette en avtale som ikke eksisterer")
    public void slettAvtaleSomIkkeEksisterer() {
        // Arrange
        int avtaleId = 999;
        Mockito.when(mockAvtaleRepo.slettAvtale(avtaleId)).thenReturn(false);

        // Act
        SlettAvtaleLogikk slettAvtaleLogikk = new SlettAvtaleLogikk(mockAvtaleRepo);
        boolean slettResult = slettAvtaleLogikk.slettAvtale(avtaleId);

        // Assert
        Assertions.assertFalse(slettResult, "Sletting av en ikke-eksisterende avtale skal feile");

        Mockito.verify(mockAvtaleRepo, Mockito.times(1)).slettAvtale(avtaleId);
    }

    @Test
    @DisplayName("Feil under sletting av avtale skal returnere false")
    public void slettAvtaleFeilUnderSletting() {
        // Arrange
        int avtaleId = 1;
        Mockito.when(mockAvtaleRepo.slettAvtale(avtaleId)).thenThrow(new RuntimeException("Uventet feil under sletting"));

        // Act
        SlettAvtaleLogikk slettAvtaleLogikk = new SlettAvtaleLogikk(mockAvtaleRepo);

        try {
            slettAvtaleLogikk.slettAvtale(avtaleId);
            Assertions.fail("Forventet at en RuntimeException skulle bli kastet");
        } catch (RuntimeException e) {
            // Assert
            Assertions.assertEquals("Uventet feil under sletting", e.getMessage(), "Feilmeldingen skal være korrekt");
        }

        Mockito.verify(mockAvtaleRepo, Mockito.times(1)).slettAvtale(avtaleId);
    }


}
