# Service de Gestion de Campagnes de Dons et Transactions

Ce projet implémente un mini-service REST pour gérer des campagnes de dons et leurs transactions associées. Il a été développé avec Spring Boot, Spring Data JPA et utilise une base de données H2 en mémoire.

## Fonctionnalités implémentées

### Modélisation : Entités JPA
- ✅ `Campagne` : id, nom, objectifMontant, dateDebut, dateFin
- ✅ `Donation` : id, campagne (ManyToOne), nomDonateur, montant, date

### Répertoires
- ✅ `CampagneRepository` (hérite de JpaRepository)
- ✅ `DonRepository` (hérite de JpaRepository)

### DTOs
- ✅ `DonDTO` : id, nomCampagne, nomDonateur, montant, date

### Projection
- ✅ `CampagneResume` : getId(), getNom(), getObjectifMontant()

### Services
- ✅ `ServiceCampagne` : récupération des campagnes actives
- ✅ `ServiceDon` : enregistrement d'un don, transformation d'une entité en DonDTO

### API REST
- ✅ GET `/api/campagnes/actives` → Liste des campagnes actives (projection)
- ✅ POST `/api/campagnes/{id}/dons` → Enregistrement d'un don (reçoit un DonDTO)

### Validation & gestion des erreurs
- ✅ Validation avec @Valid (montant > 0, nom non vide)
- ✅ Gestion des erreurs avec @ControllerAdvice

### Tests
- ✅ Test unitaire du ServiceCampagne
- ✅ Test d'intégration de l'API CampagneController

### Documentation API
- ✅ Documentation avec Swagger/OpenAPI

## Démarrage rapide

### Prérequis
- Java 17+
- Maven

### Lancement
1. Cloner le dépôt
2. Exécuter : `mvn spring-boot:run`
3. L'application est accessible à l'URL: http://localhost:8080
4. La console H2 est accessible à l'URL: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:donationdb`, User: `sa`, Aucun mot de passe)
5. La documentation Swagger est accessible à l'URL: http://localhost:8080/swagger-ui/index.html

## Détails d'implémentation

### Structure du projet
```
src/
├── main/
│   ├── java/com/example/donation/
│   │   ├── config/
│   │   │   ├── DataInitializer.java
│   │   │   └── SwaggerConfig.java
│   │   ├── controller/
│   │   │   └── CampagneController.java
│   │   ├── dto/
│   │   │   └── DonDTO.java
│   │   ├── entity/
│   │   │   ├── Campagne.java
│   │   │   └── Donation.java
│   │   ├── exception/
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── projection/
│   │   │   └── CampagneResume.java
│   │   ├── repository/
│   │   │   ├── CampagneRepository.java
│   │   │   └── DonRepository.java
│   │   ├── service/
│   │   │   ├── ServiceCampagne.java
│   │   │   └── ServiceDon.java
│   │   └── DonationApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/example/donation/
        ├── controller/
        │   └── CampagneControllerIntegrationTest.java
        └── service/
            └── ServiceCampagneTest.java
```

### Fonctionnement

L'application permet de :
1. Créer et gérer des campagnes de dons avec des dates de début et de fin
2. Consulter les campagnes actuellement actives
3. Effectuer des dons pour une campagne spécifique
4. Valider les données des dons et gérer les erreurs de manière élégante

### À noter

- Les dates sont automatiquement gérées pour déterminer si une campagne est active
- La validation des données empêche les dons négatifs ou nuls
- Une campagne inactive ne peut pas recevoir de dons
- Des données de test sont automatiquement chargées au démarrage de l'application
