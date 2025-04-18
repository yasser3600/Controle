package ma.emsi.controle.service;
import ma.emsi.controle.DTOs.DonDTO;
import ma.emsi.controle.entities.Campagne;
import ma.emsi.controle.entities.Donation;
import ma.emsi.controle.repository.CampagneRepository;
import ma.emsi.controle.repository.DonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * Service qui gère la logique métier liée aux dons.
 */
@Service
public class ServiceDon {

        /**
         * Repository pour accéder aux données des dons
         */
        private final DonRepository donRepository;

        /**
         * Repository pour accéder aux données des campagnes
         */
        private final CampagneRepository campagneRepository;

        /**
         * Constructeur avec injection de dépendances via Spring
         *
         * @param donRepository Repository des dons
         * @param campagneRepository Repository des campagnes
         */
        @Autowired
        public ServiceDon(DonRepository donRepository, CampagneRepository campagneRepository) {
            this.donRepository = donRepository;
            this.campagneRepository = campagneRepository;
        }

        /**
         * Enregistre un nouveau don pour une campagne spécifiée.
         * Vérifie si la campagne existe et si elle est active avant d'enregistrer le don.
         *
         * @param campagneId Identifiant de la campagne
         * @param donDTO Données du don à enregistrer
         * @return Le DTO du don enregistré avec les informations complétées (id, date, nom de campagne)
         * @throws EntityNotFoundException Si la campagne n'existe pas
         * @throws IllegalStateException Si la campagne n'est pas active
         */
        @Transactional
        public DonDTO enregistrerDon(Long campagneId, DonDTO donDTO) {
            // Recherche de la campagne par son ID
            Campagne campagne = campagneRepository.findById(campagneId)
                    .orElseThrow(() -> new EntityNotFoundException("Campagne non trouvée avec l'ID: " + campagneId));

            LocalDateTime currentDate = LocalDateTime.now();
            LocalDate today = LocalDate.now();

            // Vérification de l'activité de la campagne
            if (today.isBefore(campagne.getDateDebut()) || today.isAfter(campagne.getDateFin())) {
                throw new IllegalStateException("Cette campagne n'est pas active actuellement");
            }

            // Création et initialisation d'un nouvel objet Donation
            Donation donation = new Donation();
            donation.setCampagne(campagne);
            donation.setNomDonateur(donDTO.getNomDonateur());
            donation.setMontant(donDTO.getMontant());
            donation.setDate(currentDate);

            // Sauvegarde du don dans la base de données
            Donation savedDonation = donRepository.save(donation);

            // Conversion de l'entité en DTO pour le retour
            return convertToDTO(savedDonation);
        }

        /**
         * Convertit une entité Donation en DonDTO.
         *
         * @param donation L'entité à convertir
         * @return Le DTO correspondant
         */
        public DonDTO convertToDTO(Donation donation) {
            DonDTO donDTO = new DonDTO();
            donDTO.setId(donation.getId());
            donDTO.setNomCampagne(donation.getCampagne().getNom());
            donDTO.setNomDonateur(donation.getNomDonateur());
            donDTO.setMontant(donation.getMontant());
            donDTO.setDate(donation.getDate());
            return donDTO;
        }
}
