# Module RH - Documentation Complète

## 📋 Table des matières

1. [Vue d'ensemble](#vue-densemble)
2. [Architecture](#architecture)
3. [Design Patterns](#design-patterns)
4. [Entités](#entités)
5. [Services](#services)
6. [API REST](#api-rest)
7. [Frontend Thymeleaf](#frontend-thymeleaf)
8. [Guide d'utilisation](#guide-dutilisation)

---

## 🎯 Vue d'ensemble

Le module RH (Ressources Humaines) est un module complet de gestion du personnel pour la plateforme SaaS hôtelière. Il permet de gérer :

- **Employés** : Gestion complète du personnel
- **Recrutements** : Suivi des candidatures et processus de recrutement
- **Formations** : Gestion des formations et compétences
- **Congés** : Gestion des demandes et approbations de congés
- **Contrats de travail** : Gestion des contrats
- **Temps de travail** : Suivi et validation des heures travaillées
- **Fiches de paie** : Gestion de la paie et des bulletins de salaire

---

## 🏗️ Architecture

### Structure des packages

```
module/rh/
├── controller/
│   ├── EmployeController.java          # API REST pour les employés
│   ├── RecrutementController.java      # API REST pour les recrutements
│   ├── FormationController.java         # API REST pour les formations
│   ├── CongeController.java            # API REST pour les congés
│   ├── ContratTravailController.java   # API REST pour les contrats
│   ├── TempsTravailController.java     # API REST pour les temps de travail
│   ├── FichePaieController.java        # API REST pour les fiches de paie
│   └── web/
│       └── RhWebController.java         # Contrôleur Thymeleaf
├── service/
│   ├── EmployeService.java
│   ├── RecrutementService.java
│   ├── FormationService.java
│   ├── CongeService.java
│   ├── ContratTravailService.java
│   ├── TempsTravailService.java
│   └── FichePaieService.java
├── model/
│   ├── personnel/
│   │   └── EmployeModel.java
│   ├── recrutement/
│   │   └── RecrutementModel.java
│   ├── formation/
│   │   └── FormationModel.java
│   ├── conge/
│   │   └── CongeModel.java
│   ├── contrat/
│   │   └── ContratTravailModel.java
│   ├── temps/
│   │   └── TempsTravailModel.java
│   └── paie/
│       └── FichePaieModel.java
├── dto/
│   ├── request/
│   │   ├── employe/
│   │   ├── recrutement/
│   │   ├── formation/
│   │   ├── conge/
│   │   ├── contrat/
│   │   ├── temps/
│   │   └── paie/
│   └── response/
│       ├── employe/
│       ├── recrutement/
│       ├── formation/
│       ├── conge/
│       ├── contrat/
│       ├── temps/
│       └── paie/
├── mapper/
│   ├── EmployeMapper.java
│   ├── RecrutementMapper.java
│   ├── FormationMapper.java
│   ├── CongeMapper.java
│   ├── ContratTravailMapper.java
│   ├── TempsTravailMapper.java
│   └── FichePaieMapper.java
├── repository/
│   ├── EmployeRepository.java
│   ├── RecrutementRepository.java
│   ├── FormationRepository.java
│   ├── CongeRepository.java
│   ├── ContratTravailRepository.java
│   ├── TempsTravailRepository.java
│   └── FichePaieRepository.java
└── pattern/
    ├── factory/
    │   └── EmployeFactory.java
    ├── strategy/
    │   ├── ValidationStrategy.java
    │   └── DefaultValidationStrategy.java
    ├── facade/
    │   └── RhFacade.java
    └── template/
        └── AbstractRhService.java
```

---

## 🎨 Design Patterns

### 1. Repository Pattern
**Utilisation** : Accès aux données via JPA Repository

```java
public interface EmployeRepository extends JpaRepository<EmployeModel, Long> {
    Optional<EmployeModel> findByUuid(String uuid);
    List<EmployeModel> findByOrganisationIdAndActifTrue(Long organisationId);
}
```

### 2. Service Layer Pattern
**Utilisation** : Logique métier centralisée dans les services

```java
@Service
@Transactional
public class EmployeService {
    // Logique métier pour les employés
}
```

### 3. DTO Pattern
**Utilisation** : Séparation entre couche présentation et domaine

- `CreateEmployeRequest` : Données d'entrée
- `UpdateEmployeRequest` : Données de mise à jour
- `EmployeResponse` : Données de sortie avec audit et trace

### 4. Mapper Pattern (MapStruct)
**Utilisation** : Transformation automatique entre DTO et entités

```java
@Mapper(componentModel = "spring")
public interface EmployeMapper {
    EmployeModel toEntity(CreateEmployeRequest request);
    EmployeResponse toResponse(EmployeModel entity);
}
```

### 5. Factory Pattern
**Utilisation** : Création d'entités complexes avec validation

```java
@Component
public class EmployeFactory {
    public EmployeModel createEmploye(CreateEmployeRequest request, ...) {
        // Logique de création avec validation
    }
}
```

### 6. Strategy Pattern
**Utilisation** : Validation métier flexible

```java
public interface ValidationStrategy {
    void validate(CreateEmployeRequest request);
}

@Component
public class DefaultValidationStrategy implements ValidationStrategy {
    // Validation par défaut
}
```

### 7. Facade Pattern
**Utilisation** : Interface simplifiée pour accéder aux services

```java
@Service
public class RhFacade {
    public RhDashboard getDashboard(Long employeId, Long organisationId) {
        // Agrége les données de plusieurs services
    }
}
```

### 8. Template Method Pattern
**Utilisation** : Algorithme commun pour les opérations CRUD

```java
public abstract class AbstractRhService<T extends BaseEntity, ID> {
    public T create(T entity, ...) {
        validateBeforeCreate(entity);
        initializeEntity(entity, ...);
        T saved = saveEntity(entity);
        afterCreate(saved);
        return saved;
    }
}
```

---

## 📦 Entités

### EmployeModel
Gestion complète des employés avec :
- Informations personnelles (nom, prénom, date de naissance, etc.)
- Informations professionnelles (poste, département, salaire, etc.)
- Informations bancaires et administratives
- Conformité RGPD

### RecrutementModel
Suivi des candidatures :
- Informations candidat
- Statut de candidature
- Évaluation et notes
- Dates d'entretien

### FormationModel
Gestion des formations :
- Titre et organisme
- Dates et coût
- Statut et certificats
- Lien avec l'employé

### CongeModel
Gestion des congés :
- Type de congé
- Dates début/fin
- Statut (en attente, approuvé, rejeté)
- Solde avant/après
- Approbation

### ContratTravailModel
Gestion des contrats :
- Type de contrat
- Dates début/fin
- Salaire et heures
- Statut

### TempsTravailModel
Suivi des temps de travail :
- Heures normales et supplémentaires
- Date et type de jour
- Validation

### FichePaieModel
Gestion de la paie :
- Mois/année
- Salaire brut/net
- Cotisations et impôts
- Statut de paiement

---

## 🔧 Services

### EmployeService
- `create()` : Création d'un employé
- `update()` : Mise à jour
- `getByUuid()` / `getById()` : Récupération
- `getAll()` / `getAllPaginated()` : Liste avec pagination
- `getByDepartement()` : Filtrage par département
- `delete()` : Suppression (soft delete)
- `activate()` / `deactivate()` : Activation/désactivation

### CongeService
- `create()` : Création d'une demande de congé
- `approve()` : Approbation
- `reject()` : Rejet
- `getByEmploye()` : Liste des congés d'un employé
- `getByStatut()` : Filtrage par statut

### TempsTravailService
- `create()` : Enregistrement des heures
- `validate()` : Validation des temps
- `getByDate()` : Filtrage par date

### FichePaieService
- `create()` : Création avec vérification d'unicité
- `getByEmployeAndPeriod()` : Récupération par période

---

## 🌐 API REST

### Base URL
```
/api/v1/rh
```

### Headers requis
- `X-Organisation-Id` : ID de l'organisation (obligatoire)
- `X-Hotel-Id` : ID de l'hôtel (optionnel)
- `X-Username` : Nom d'utilisateur (pour création/modification)

### Endpoints Employés

#### Créer un employé
```http
POST /api/v1/rh/employes
Content-Type: application/json

{
  "matricule": "EMP001",
  "nom": "Dupont",
  "prenom": "Jean",
  "email": "jean.dupont@example.com",
  ...
}
```

#### Récupérer un employé
```http
GET /api/v1/rh/employes/{uuid}
```

#### Liste paginée
```http
GET /api/v1/rh/employes/paginated?page=0&size=20
```

#### Par département
```http
GET /api/v1/rh/employes/departement/{departement}
```

#### Activer/Désactiver
```http
PATCH /api/v1/rh/employes/{uuid}/activate
PATCH /api/v1/rh/employes/{uuid}/deactivate
```

### Endpoints Congés

#### Approuver un congé
```http
POST /api/v1/rh/conges/{uuid}/approve
Content-Type: application/json

{
  "approbateurId": 123
}
```

#### Rejeter un congé
```http
POST /api/v1/rh/conges/{uuid}/reject
Content-Type: application/json

{
  "motif": "Solde insuffisant"
}
```

### Endpoints Temps de Travail

#### Valider les temps
```http
POST /api/v1/rh/temps-travail/{uuid}/validate
Content-Type: application/json

{
  "validateurId": 123
}
```

### Réponses API

Toutes les réponses suivent le format `ApiResponse<T>` :

```json
{
  "success": true,
  "message": "Ressource créée avec succès",
  "data": { ... },
  "timestamp": "2024-01-15T10:30:00"
}
```

---

## 🖥️ Frontend Thymeleaf

### Pages disponibles

1. **Accueil** : `/rh`
   - Vue d'ensemble du module
   - Accès rapide aux différentes sections

2. **Liste des employés** : `/rh/employes`
   - Tableau avec pagination
   - Recherche et filtres

3. **Détails employé** : `/rh/employes/{uuid}`
   - Informations complètes
   - Congés, formations, fiches de paie associées

4. **Liste des congés** : `/rh/conges`
   - Tous les congés avec statut

5. **Liste des formations** : `/rh/formations`
   - Toutes les formations

6. **Liste des fiches de paie** : `/rh/fiches-paie`
   - Toutes les fiches de paie

7. **Tableau de bord** : `/rh/dashboard`
   - Vue d'ensemble avec statistiques

### Templates

Les templates sont situés dans `src/main/resources/templates/rh/` :

- `index.html` : Page d'accueil
- `employes/list.html` : Liste des employés
- `employes/view.html` : Détails employé
- `conges/list.html` : Liste des congés
- `formations/list.html` : Liste des formations
- `fiches-paie/list.html` : Liste des fiches de paie
- `dashboard.html` : Tableau de bord

### Technologies utilisées

- **Bootstrap 5.3** : Framework CSS
- **Bootstrap Icons** : Icônes
- **Thymeleaf** : Moteur de templates

---

## 📖 Guide d'utilisation

### 1. Créer un employé

**Via API REST :**
```bash
curl -X POST http://localhost:8080/api/v1/rh/employes \
  -H "Content-Type: application/json" \
  -H "X-Organisation-Id: 1" \
  -H "X-Username: admin" \
  -d '{
    "matricule": "EMP001",
    "nom": "Dupont",
    "prenom": "Jean",
    "email": "jean.dupont@example.com",
    "telephone": "+33123456789",
    "poste": "Réceptionniste",
    "departement": "Réception",
    "salaireBase": 2500.00,
    "dateEmbauche": "2024-01-01"
  }'
```

**Via Interface Web :**
1. Accéder à `/rh/employes`
2. Cliquer sur "Ajouter un employé"
3. Remplir le formulaire
4. Soumettre

### 2. Gérer les congés

**Créer une demande :**
```bash
curl -X POST http://localhost:8080/api/v1/rh/conges \
  -H "Content-Type: application/json" \
  -H "X-Organisation-Id: 1" \
  -d '{
    "employeId": 1,
    "typeConge": "ANNUEL",
    "dateDebut": "2024-07-01",
    "dateFin": "2024-07-15",
    "motif": "Vacances"
  }'
```

**Approuver :**
```bash
curl -X POST http://localhost:8080/api/v1/rh/conges/{uuid}/approve \
  -H "Content-Type: application/json" \
  -H "X-Organisation-Id: 1" \
  -d '{"approbateurId": 2}'
```

### 3. Valider les temps de travail

```bash
curl -X POST http://localhost:8080/api/v1/rh/temps-travail/{uuid}/validate \
  -H "Content-Type: application/json" \
  -H "X-Organisation-Id: 1" \
  -d '{"validateurId": 2}'
```

---

## 🔒 Sécurité et Multi-tenant

### Isolation des données

Toutes les opérations sont isolées par :
- `organisationId` : Obligatoire pour toutes les requêtes
- `hotelId` : Optionnel, pour un filtrage plus fin

### Soft Delete

Toutes les suppressions sont des "soft delete" :
- `supprime = true`
- `actif = false`
- Les données restent en base pour audit

---

## 📊 Traçabilité

Chaque entité inclut :
- **Audit** : `dateCreation`, `dateModification`, `creePar`, `modifiePar`, `version`
- **Trace** : `traceId`, `spanId`, `correlationId`, `requestId`, `operationId`

---

## 🧪 Tests

### Tests unitaires recommandés

- Tests des services
- Tests des mappers
- Tests des validations
- Tests des repositories

### Tests d'intégration

- Tests des contrôleurs REST
- Tests des contrôleurs Thymeleaf
- Tests end-to-end

---

## 🚀 Déploiement

### Prérequis

- Java 17+
- Spring Boot 3.3.4+
- PostgreSQL
- Maven 3.8+

### Configuration

```properties
# application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/hotel
spring.datasource.username=hotel
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
```

---

## 📝 Notes importantes

1. **Multi-tenant** : Toutes les requêtes doivent inclure `X-Organisation-Id`
2. **Validation** : Utilise les annotations Jakarta Validation
3. **Pagination** : Par défaut 20 éléments par page
4. **Soft Delete** : Les suppressions ne suppriment pas réellement les données
5. **Audit** : Toutes les opérations sont tracées

---

## 🤝 Contribution

Pour contribuer au module RH :

1. Respecter l'architecture existante
2. Utiliser les design patterns appropriés
3. Ajouter des tests
4. Documenter le code
5. Suivre les conventions de nommage

---

## 📞 Support

Pour toute question ou problème :
- Consulter la documentation de l'API (Swagger)
- Vérifier les logs de l'application
- Contacter l'équipe de développement

---

**Version** : 1.0.0  
**Dernière mise à jour** : 2024  
**Auteur** : Équipe Développement
