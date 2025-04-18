package ma.emsi.controle.repository;
import ma.emsi.controle.entities.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour l'accès aux données des dons.
 * Hérite de JpaRepository pour bénéficier des méthodes standard de CRUD.
 */
@Repository
public interface DonRepository extends JpaRepository<Donation, Long> {

        // Les méthodes de base du JpaRepository sont suffisantes pour le moment,
        // donc aucune méthode personnalisée n'est ajoutée
}
