package ma.emsi.controle.service;

import ma.emsi.controle.projection.CampagneResume;
import ma.emsi.controle.repository.CampagneRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import ma.emsi.controle.projection.CampagneResume;
import ma.emsi.controle.repository.CampagneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Tests unitaires pour ServiceCampagne.
 * Utilise Mockito pour simuler les dépendances.
 */
@ExtendWith(MockitoExtension.class)
public class ServiceCampagneTest {

        /**
         * Mock du repository des campagnes
         */
        @Mock
        private CampagneRepository campagneRepository;

        /**
         * Injection du mock dans le service à tester
         */
        @InjectMocks
        private ServiceCampagne serviceCampagne;

        /**
         * Mocks des projections de campagnes pour les tests
         */
        @Mock
        private CampagneResume campagneResume1;

        @Mock
        private CampagneResume campagneResume2;

        /**
         * Configuration des mocks avant chaque test
         */
        @BeforeEach
        void setUp() {
            // Configuration des comportements attendus pour le premier mock de CampagneResume
            when(campagneResume1.getId()).thenReturn(1L);
            when(campagneResume1.getNom()).thenReturn("Campagne 1");
            when(campagneResume1.getObjectifMontant()).thenReturn(new BigDecimal("1000.00"));

            // Configuration des comportements attendus pour le second mock de CampagneResume
            when(campagneResume2.getId()).thenReturn(2L);
            when(campagneResume2.getNom()).thenReturn("Campagne 2");
            when(campagneResume2.getObjectifMontant()).thenReturn(new BigDecimal("2000.00"));
        }

        /**
         * Test de la méthode getCampagnesActives.
         * Vérifie que la méthode renvoie correctement les campagnes actives.
         */
        @Test
        void testGetCampagnesActives() {
            // Configuration
            List<CampagneResume> mockCampagnes = Arrays.asList(campagneResume1, campagneResume2);

            // Configuration du comportement du repository mocké
            when(campagneRepository.findActiveCampagnesAsResume(any(LocalDate.class)))
                    .thenReturn(mockCampagnes);

            // Exécution de la méthode à tester
            List<CampagneResume> result = serviceCampagne.getCampagnesActives();

            // Vérification des résultats
            assertEquals(2, result.size());
            assertEquals(1L, result.get(0).getId());
            assertEquals("Campagne 1", result.get(0).getNom());
            assertEquals(new BigDecimal("1000.00"), result.get(0).getObjectifMontant());

            assertEquals(2L, result.get(1).getId());
            assertEquals("Campagne 2", result.get(1).getNom());
            assertEquals(new BigDecimal("2000.00"), result.get(1).getObjectifMontant());
        }

}
