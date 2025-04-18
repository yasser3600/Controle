package ma.emsi.controle.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Entité représentant une campagne de don.
 * Cette classe est mappée à une table dans la base de données via JPA.
 */
@Entity
public class Campagne {

        /**
         * Identifiant unique de la campagne, généré automatiquement.
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        /**
         * Nom de la campagne, champ obligatoire.
         */
        @NotBlank(message = "Le nom de la campagne est obligatoire")
        private String nom;

        /**
         * Montant objectif de la campagne, doit être positif et non nul.
         */
        @NotNull(message = "L'objectif de montant est obligatoire")
        @Positive(message = "L'objectif de montant doit être positif")
        private BigDecimal objectifMontant;

        /**
         * Date de début de la campagne, obligatoire.
         */
        @NotNull(message = "La date de début est obligatoire")
        private LocalDate dateDebut;

        /**
         * Date de fin de la campagne, obligatoire.
         */
        @NotNull(message = "La date de fin est obligatoire")
        private LocalDate dateFin;

        /**
         * Liste des dons associés à cette campagne.
         * Relation OneToMany avec cascade pour propager les opérations à tous les dons associés.
         */
        @OneToMany(mappedBy = "campagne", cascade = CascadeType.ALL)
        private List<Donation> donations = new ArrayList<>();

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public BigDecimal getObjectifMontant() {
            return objectifMontant;
        }

        public void setObjectifMontant(BigDecimal objectifMontant) {
            this.objectifMontant = objectifMontant;
        }

        public LocalDate getDateDebut() {
            return dateDebut;
        }

        public void setDateDebut(LocalDate dateDebut) {
            this.dateDebut = dateDebut;
        }

        public LocalDate getDateFin() {
            return dateFin;
        }

        public void setDateFin(LocalDate dateFin) {
            this.dateFin = dateFin;
        }

        public List<Donation> getDonations() {
            return donations;
        }

        public void setDonations(List<Donation> donations) {
            this.donations = donations;
        }
}
