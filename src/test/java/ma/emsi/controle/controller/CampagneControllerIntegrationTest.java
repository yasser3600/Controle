package ma.emsi.controle.controller;
import ma.emsi.controle.DTOs.DonDTO;
import ma.emsi.controle.entities.Campagne;
import ma.emsi.controle.repository.CampagneRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests d'intégration pour CampagneController.
 * Ces tests vérifient l'intégration entre les différentes couches de l'application.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class CampagneControllerIntegrationTest {

        /**
         * MockMvc pour simuler des requêtes HTTP
         */
        @Autowired
        private MockMvc mockMvc;

        /**
         * ObjectMapper pour la conversion entre objets Java et JSON
         */
        @Autowired
        private ObjectMapper objectMapper;

        /**
         * Repository pour accéder directement à la base de données de test
         */
        @Autowired
        private CampagneRepository campagneRepository;

        // Suite du fichier src/test/java/com/example/donation/controller/CampagneControllerIntegrationTest.java

        /**
         * Configuration de l'environnement de test avant chaque test
         */
        @BeforeEach
        void setUp() {
            // Nettoyage de la base de données
            campagneRepository.deleteAll();

            // Création d'une campagne active
            Campagne campagneActive = new Campagne();
            campagneActive.setNom("Campagne Test Active");
            campagneActive.setObjectifMontant(new BigDecimal("5000.00"));
            campagneActive.setDateDebut(LocalDate.now().minusDays(5));
            campagneActive.setDateFin(LocalDate.now().plusDays(5));
            campagneRepository.save(campagneActive);

            // Création d'une campagne inactive (future)
            Campagne campagneFuture = new Campagne();
            campagneFuture.setNom("Campagne Test Future");
            campagneFuture.setObjectifMontant(new BigDecimal("3000.00"));
            campagneFuture.setDateDebut(LocalDate.now().plusDays(2));
            campagneFuture.setDateFin(LocalDate.now().plusDays(10));
            campagneRepository.save(campagneFuture);

            // Création d'une campagne inactive (passée)
            Campagne campagnePassee = new Campagne();
            campagnePassee.setNom("Campagne Test Passée");
            campagnePassee.setObjectifMontant(new BigDecimal("2000.00"));
            campagnePassee.setDateDebut(LocalDate.now().minusDays(20));
            campagnePassee.setDateFin(LocalDate.now().minusDays(10));
            campagneRepository.save(campagnePassee);
        }

        /**
         * Test de l'endpoint GET /api/campagnes/actives.
         * Vérifie que seules les campagnes actives sont renvoyées.
         */
        @Test
        void testGetCampagnesActives() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/campagnes/actives")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].nom").value("Campagne Test Active"));
        }

        /**
         * Test de l'endpoint POST /api/campagnes/{id}/dons avec des données valides.
         * Vérifie qu'un don peut être correctement enregistré.
         */
        @Test
        void testEnregistrerDonValid() throws Exception {
            // Récupération de l'ID de la campagne active
            Long campagneActiveId = campagneRepository.findAll().stream()
                    .filter(c -> c.getNom().equals("Campagne Test Active"))
                    .findFirst()
                    .map(Campagne::getId)
                    .orElseThrow();

            // Création d'un objet DonDTO valide
            DonDTO donDTO = new DonDTO();
            donDTO.setNomDonateur("Jean Dupont");
            donDTO.setMontant(new BigDecimal("100.00"));

            // Envoi de la requête POST
            mockMvc.perform(MockMvcRequestBuilders.post("/api/campagnes/{id}/dons", campagneActiveId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(donDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.nomCampagne").value("Campagne Test Active"))
                    .andExpect(jsonPath("$.nomDonateur").value("Jean Dupont"))
                    .andExpect(jsonPath("$.montant").value(100.00));
        }

        /**
         * Test de l'endpoint POST /api/campagnes/{id}/dons avec des données invalides.
         * Vérifie que les validations sont bien appliquées.
         */
        @Test
        void testEnregistrerDonInvalid() throws Exception {
            // Récupération de l'ID de la campagne active
            Long campagneActiveId = campagneRepository.findAll().stream()
                    .filter(c -> c.getNom().equals("Campagne Test Active"))
                    .findFirst()
                    .map(Campagne::getId)
                    .orElseThrow();

            // Création d'un objet DonDTO invalide (montant négatif)
            DonDTO donDTO = new DonDTO();
            donDTO.setNomDonateur("Jean Dupont");
            donDTO.setMontant(new BigDecimal("-50.00"));

            // Envoi de la requête POST
            mockMvc.perform(MockMvcRequestBuilders.post("/api/campagnes/{id}/dons", campagneActiveId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(donDTO)))
                    .andExpect(status().isBadRequest());
        }

        /**
         * Test de l'endpoint POST /api/campagnes/{id}/dons pour une campagne inexistante.
         * Vérifie que l'API renvoie une erreur 404 Not Found.
         */
        @Test
        void testEnregistrerDonCampagneInexistante() throws Exception {
            // ID inexistant
            Long campagneInexistanteId = 999L;

            // Création d'un objet DonDTO valide
            DonDTO donDTO = new DonDTO();
            donDTO.setNomDonateur("Jean Dupont");
            donDTO.setMontant(new BigDecimal("100.00"));

            // Envoi de la requête POST
            mockMvc.perform(MockMvcRequestBuilders.post("/api/campagnes/{id}/dons", campagneInexistanteId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(donDTO)))
                    .andExpect(status().isNotFound());
        }

        /**
         * Test de l'endpoint POST /api/campagnes/{id}/dons pour une campagne inactive.
         * Vérifie que l'API renvoie une erreur 400 Bad Request.
         */
        @Test
        void testEnregistrerDonCampagneInactive() throws Exception {
            // Récupération de l'ID d'une campagne inactive (future)
            Long campagneInactiveId = campagneRepository.findAll().stream()
                    .filter(c -> c.getNom().equals("Campagne Test Future"))
                    .findFirst()
                    .map(Campagne::getId)
                    .orElseThrow();

            // Création d'un objet DonDTO valide
            DonDTO donDTO = new DonDTO();
            donDTO.setNomDonateur("Jean Dupont");
            donDTO.setMontant(new BigDecimal("100.00"));

            // Envoi de la requête POST
            mockMvc.perform(MockMvcRequestBuilders.post("/api/campagnes/{id}/dons", campagneInactiveId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(donDTO)))
                    .andExpect(status().isBadRequest());
        }

}
