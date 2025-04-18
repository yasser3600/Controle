package ma.emsi.controle.controller;
import ma.emsi.controle.DTOs.DonDTO;
import ma.emsi.controle.projection.CampagneResume;
import ma.emsi.controle.service.ServiceCampagne;
import ma.emsi.controle.service.ServiceDon;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST qui expose les endpoints liés aux campagnes et aux dons.
 */
@RestController
@RequestMapping("/api/campagnes")
public class CampagneController {

        /**
         * Service gérant les campagnes
         */
        private final ServiceCampagne serviceCampagne;

        /**
         * Service gérant les dons
         */
        private final ServiceDon serviceDon;

        /**
         * Constructeur avec injection de dépendances via Spring
         *
         * @param serviceCampagne Service des campagnes
         * @param serviceDon Service des dons
         */
        @Autowired
        public CampagneController(ServiceCampagne serviceCampagne, ServiceDon serviceDon) {
            this.serviceCampagne = serviceCampagne;
            this.serviceDon = serviceDon;
        }

        /**
         * Endpoint pour récupérer toutes les campagnes actives.
         *
         * @return ResponseEntity contenant la liste des résumés des campagnes actives
         */
        @GetMapping("/actives")
        public ResponseEntity<List<CampagneResume>> getCampagnesActives() {
            List<CampagneResume> campagnes = serviceCampagne.getCampagnesActives();
            return new ResponseEntity<>(campagnes, HttpStatus.OK);
        }

        /**
         * Endpoint pour enregistrer un nouveau don pour une campagne spécifique.
         * Le paramètre @Valid garantit que les données du don passent les validations définies dans DonDTO.
         *
         * @param id ID de la campagne
         * @param donDTO Données du don à enregistrer
         * @return ResponseEntity contenant le DTO du don enregistré
         */
        @PostMapping("/{id}/dons")
        public ResponseEntity<DonDTO> enregistrerDon(@PathVariable Long id, @Valid @RequestBody DonDTO donDTO) {
            DonDTO savedDon = serviceDon.enregistrerDon(id, donDTO);
            return new ResponseEntity<>(savedDon, HttpStatus.CREATED);
        }
}
