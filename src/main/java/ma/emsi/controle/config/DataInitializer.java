package ma.emsi.controle.config;
import ma.emsi.controle.entities.Campagne;
import ma.emsi.controle.entities.Donation;
import ma.emsi.controle.repository.CampagneRepository;
import ma.emsi.controle.repository.DonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Classe pour initialiser des données de test au démarrage de l'application.
 * Implémente CommandLineRunner pour s'exécuter au démarrage.
 */
@Component
public class DataInitializer implements CommandLineRunner {

        private final CampagneRepository campagneRepository;
        private final DonRepository donRepository;

        @Autowired
        public DataInitializer(CampagneRepository campagneRepository, DonRepository donRepository) {
            this.campagneRepository = campagneRepository;
            this.donRepository = donRepository;
        }

        @Override
        public void run(String... args) {
            // Vérifie si la base de données est déjà initialisée
            if (campagneRepository.count() > 0) {
                return;
            }

            // Création de campagnes de test
            Campagne campagne1 = new Campagne();
            campagne1.setNom("Aide aux victimes de catastrophes naturelles");
            campagne1.setObjectifMontant(new BigDecimal("50000.00"));
            campagne1.setDateDebut(LocalDate.now().minusDays(10));
            campagne1.setDateFin(LocalDate.now().plusDays(20));

            Campagne campagne2 = new Campagne();
            campagne2.setNom("Soutien aux étudiants en difficulté");
            campagne2.setObjectifMontant(new BigDecimal("20000.00"));
            campagne2.setDateDebut(LocalDate.now().minusDays(5));
            campagne2.setDateFin(LocalDate.now().plusDays(25));

            Campagne campagne3 = new Campagne();
            campagne3.setNom("Campagne pour la recherche médicale");
            campagne3.setObjectifMontant(new BigDecimal("100000.00"));
            campagne3.setDateDebut(LocalDate.now().plusDays(5));
            campagne3.setDateFin(LocalDate.now().plusDays(35));

            Campagne campagne4 = new Campagne();
            campagne4.setNom("Protection de l'environnement");
            campagne4.setObjectifMontant(new BigDecimal("30000.00"));
            campagne4.setDateDebut(LocalDate.now().minusDays(30));
            campagne4.setDateFin(LocalDate.now().minusDays(5));

            // Sauvegarde des campagnes
            Arrays.asList(campagne1, campagne2, campagne3, campagne4)
                    .forEach(campagneRepository::save);

            // Ajout de quelques dons pour les campagnes actives
            Donation don1 = new Donation();
            don1.setCampagne(campagne1);
            don1.setNomDonateur("Marie Dupont");
            don1.setMontant(new BigDecimal("500.00"));
            don1.setDate(LocalDateTime.now().minusDays(5));

            Donation don2 = new Donation();
            don2.setCampagne(campagne1);
            don2.setNomDonateur("Pierre Martin");
            don2.setMontant(new BigDecimal("1000.00"));
            don2.setDate(LocalDateTime.now().minusDays(3));

            Donation don3 = new Donation();
            don3.setCampagne(campagne2);
            don3.setNomDonateur("Sophie Leclerc");
            don3.setMontant(new BigDecimal("250.00"));
            don3.setDate(LocalDateTime.now().minusDays(2));

            // Sauvegarde des dons
            Arrays.asList(don1, don2, don3)
                    .forEach(donRepository::save);

            System.out.println("Base de données initialisée avec " + campagneRepository.count() + " campagnes et " + donRepository.count() + " dons");
        }
}
