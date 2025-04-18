package ma.emsi.controle.repository;
import ma.emsi.controle.entities.Campagne;
import ma.emsi.controle.projection.CampagneResume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository pour l'accès aux données des campagnes.
 * Hérite de JpaRepository pour bénéficier des méthodes standard de CRUD.
 */
@Repository
public interface CampagneRepository extends JpaRepository<Campagne, Long> {
        /**
         * Recherche les campagnes actives à la date fournie.
         * Une campagne est active si la date actuelle est entre sa date de début et sa date de fin.
         * Retourne les résultats sous forme de projection CampagneResume pour limiter les données transférées.
         *
         * @param currentDate La date pour laquelle vérifier l'activité des campagnes
         * @return Liste des campagnes actives sous forme de résumés
         */
        @Query("SELECT c FROM Campagne c WHERE c.dateDebut <= :currentDate AND c.dateFin >= :currentDate")
        List<CampagneResume> findActiveCampagnesAsResume(LocalDate currentDate);
}
