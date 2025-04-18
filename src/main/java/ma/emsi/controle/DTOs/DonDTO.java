package ma.emsi.controle.DTOs;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) pour transférer les données des dons entre les couches
 * Cette classe permet d'isoler l'API des entités de persistance
 */
public class DonDTO {
        /**
         * Identifiant du don
         */
        private Long id;

        /**
         * Nom de la campagne associée au don
         */
        private String nomCampagne;

        /**
         * Nom du donateur, champ obligatoire pour la validation
         */
        @NotBlank(message = "Le nom du donateur est obligatoire")
        private String nomDonateur;

        /**
         * Montant du don, doit être positif et non nul
         */
        @NotNull(message = "Le montant est obligatoire")
        @Positive(message = "Le montant doit être positif")
        private BigDecimal montant;

        /**
         * Date et heure du don
         */
        private LocalDateTime date;

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNomCampagne() {
            return nomCampagne;
        }

        public void setNomCampagne(String nomCampagne) {
            this.nomCampagne = nomCampagne;
        }

        public String getNomDonateur() {
            return nomDonateur;
        }

        public void setNomDonateur(String nomDonateur) {
            this.nomDonateur = nomDonateur;
        }

        public BigDecimal getMontant() {
            return montant;
        }

        public void setMontant(BigDecimal montant) {
            this.montant = montant;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public void setDate(LocalDateTime date) {
            this.date = date;
        }
}
