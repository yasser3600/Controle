package ma.emsi.controle.service;
import ma.emsi.controle.projection.CampagneResume;
import ma.emsi.controle.repository.CampagneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Service qui gère la logique métier liée aux campagnes de dons.
 */
@Service
public class ServiceCampagne {

        /**
         * Repository pour accéder aux données des campagnes
         */
        private final CampagneRepository campagneRepository;

        /**
         * Constructeur avec injection de dépendances via Spring
         *
         * @param campagneRepository Repository des campagnes
         */
        @Autowired
        public ServiceCampagne(CampagneRepository campagneRepository) {
            this.campagneRepository = campagneRepository;
        }

        /**
         * Récupère la liste des campagnes actuellement actives.
         * Une campagne est active si la date du jour est comprise entre sa date de début et sa date de fin.
         *
         * @return Liste des résumés des campagnes actives
         */
        public List<CampagneResume> getCampagnesActives() {
            LocalDate currentDate = LocalDate.now();
            return campagneRepository.findActiveCampagnesAsResume(currentDate);
        }
}
