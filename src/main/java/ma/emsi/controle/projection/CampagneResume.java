package ma.emsi.controle.projection;
import java.math.BigDecimal;

/**
 * Interface de projection pour retourner un sous-ensemble des informations de campagne.
 * Utilisée pour optimiser les transferts de données en ne renvoyant que les champs nécessaires.
 */
public interface CampagneResume {
        /**
         * @return L'identifiant de la campagne
         */
        Long getId();

        /**
         * @return Le nom de la campagne
         */
        String getNom();

        /**
         * @return Le montant objectif de la campagne
         */
        BigDecimal getObjectifMontant();
}
