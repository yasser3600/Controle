package ma.emsi.controle.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entité représentant un don fait à une campagne.
 * Cette classe est mappée à une table dans la base de données via JPA.
 */
@Entity
public class Donation {
        /**
         * Identifiant unique du don, généré automatiquement.
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        /**
         * Référence à la campagne associée à ce don.
         * Établit une relation ManyToOne avec la table des campagnes.
         */
        @ManyToOne
        @JoinColumn(name = "campagne_id")
        private Campagne campagne;

        /**
         * Nom du donateur, champ obligatoire.
         */
        @NotBlank(message = "Le nom du donateur est obligatoire")
        private String nomDonateur;

        /**
         * Montant du don, doit être positif et non nul.
         */
        @NotNull(message = "Le montant est obligatoire")
        @Positive(message = "Le montant doit être positif")
        private BigDecimal montant;

        /**
         * Date et heure auxquelles le don a été effectué.
         */
        private LocalDateTime date;

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Campagne getCampagne() {
            return campagne;
        }

        public void setCampagne(Campagne campagne) {
            this.campagne = campagne;
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
