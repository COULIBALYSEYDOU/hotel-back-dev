# 🏨 Plateforme de Gestion Hôtelière

**Version** : 2.0.0 Enterprise  
**Statut** : 🚀 Production-Ready  
**Technologie** : Spring Boot 3.3.4 | Java 17 | PostgreSQL

---

## 📋 Table des matières

1. [Vue d'ensemble](#vue-densemble)
2. [Architecture](#architecture)
3. [Modules disponibles](#modules-disponibles)
4. [Module RH - Enterprise](#module-rh---enterprise)
5. [Prérequis](#prérequis)
6. [Installation](#installation)
7. [Configuration](#configuration)
8. [Utilisation](#utilisation)
9. [API REST](#api-rest)
10. [Frontend Web](#frontend-web)
11. [Architecture technique](#architecture-technique)
12. [Sécurité & Conformité](#sécurité--conformité)
13. [Documentation](#documentation)
14. [Contribution](#contribution)

---

## 🎯 Vue d'ensemble

Plateforme SaaS de gestion hôtelière modulaire et extensible, conçue pour gérer l'ensemble des opérations d'un établissement hôtelier. Architecture enterprise-grade avec support multi-tenant, conformité internationale et intégration IA.

### Caractéristiques principales

- ✅ **Architecture modulaire** : Modules indépendants et extensibles
- ✅ **Multi-tenant** : Isolation complète des données par organisation
- ✅ **Conformité internationale** : Support multi-pays (FRA, CMR, extensible)
- ✅ **RGPD** : Conformité complète avec gestion des données personnelles
- ✅ **Event-Driven** : Architecture asynchrone avec événements métier
- ✅ **IA & Analytics** : Scores, prédictions et recommandations intelligentes
- ✅ **API REST complète** : 100+ endpoints documentés
- ✅ **Interface Web** : Frontend Thymeleaf responsive
- ✅ **Design Patterns** : Repository, Service Layer, DTO, Mapper, Factory, Strategy, Facade, Template Method

---

## 🏗️ Architecture

### Structure du projet

```
hotel/
├── src/main/java/projet_hotelier/hotel/
│   ├── HotelApplication.java          # Point d'entrée Spring Boot
│   ├── core/                          # Modules core (organisation, géo, structure)
│   │   ├── organisation/              # Gestion multi-tenant
│   │   ├── geo/                       # Géolocalisation (pays, villes, régions)
│   │   ├── structure/                 # Structure hôtelière (hôtels, sites)
│   │   └── integration/               # Intégrations externes
│   ├── module/                        # Modules métier
│   │   ├── rh/                        # ⭐ Module RH Enterprise (Production-Ready)
│   │   ├── clientele/                 # Gestion clientèle
│   │   ├── finances/                  # Gestion financière
│   │   ├── planning/                  # Planning et réservations
│   │   └── reporting/                  # Reporting et analytics
│   ├── shared/                        # Composants partagés
│   └── repository/                    # Repositories globaux
├── src/main/resources/
│   ├── application.properties         # Configuration principale
│   └── templates/                     # Templates Thymeleaf
│       └── rh/                        # Interface web module RH
├── docs/                              # Documentation complète
└── pom.xml                            # Configuration Maven
```

### Stack technique

- **Backend** : Spring Boot 3.3.4, Spring Data JPA, Spring Security
- **Base de données** : PostgreSQL
- **Mapping** : MapStruct 1.5.5
- **Validation** : Jakarta Validation
- **Documentation API** : SpringDoc OpenAPI (Swagger)
- **Frontend** : Thymeleaf, Bootstrap 5.3
- **Build** : Maven 3.8+
- **Java** : 17+

---

## 📦 Modules disponibles

### 1. ⭐ Module RH (Ressources Humaines) - **Production-Ready**

**Version** : 2.0.0 Enterprise  
**Statut** : ✅ Production-Ready

Module RH international enterprise-grade avec gestion complète du personnel.

#### Fonctionnalités principales

- **Gestion des employés** : 200+ champs, conformité multi-pays
- **Recrutement** : Suivi complet des candidatures
- **Formation & Compétences** : Gestion des formations et certifications
- **Congés** : Workflow hiérarchique multi-niveaux
- **Contrats de travail** : Gestion complète des contrats
- **Temps de travail** : Suivi et validation des heures
- **Fiches de paie** : Gestion de la paie
- **Onboarding/Offboarding** : Processus automatisés avec checklists dynamiques
- **Évaluations de performance** : Scores pondérés et recommandations IA
- **Conformité légale** : Vérification automatique multi-pays

#### Statistiques du module

- **15 modèles de données** ultra-enrichis (500+ champs au total)
- **13 services métier** complets
- **13 contrôleurs REST** avec 100+ endpoints
- **1 contrôleur web Thymeleaf** avec 8 pages
- **27 DTOs** (18 request + 9 response)
- **9 mappers MapStruct**
- **13 repositories JPA**
- **5 types d'événements** (architecture event-driven)
- **8 design patterns** implémentés

📖 **Documentation complète** : Voir [docs/INVENTAIRE_COMPLET_MODULE_RH.md](docs/INVENTAIRE_COMPLET_MODULE_RH.md)

### 2. Module Clientele

Gestion complète de la clientèle avec :
- Clients et contacts
- Réservations
- Avis clients
- Programmes de fidélité
- Campagnes marketing
- Interactions clients

### 3. Module Finances

Gestion financière avec :
- Facturation
- Paiements
- Comptabilité
- Audit financier

### 4. Module Planning

Gestion du planning avec :
- Chambres
- Réservations
- Disponibilités
- Géolocalisation

### 5. Module Reporting

Reporting et analytics avec :
- Rapports personnalisés
- Analytics avancés
- Conformité et compliance
- Documents générés

---

## ⭐ Module RH - Enterprise

### Vue d'ensemble

Le module RH est le module le plus avancé de la plateforme, avec une architecture enterprise-grade et des fonctionnalités complètes de gestion des ressources humaines.

### Modèles de données (15 modèles)

1. **EmployeModel** (200+ champs) - Gestion complète des employés
2. **CongeModel** (100+ champs) - Gestion des congés avec workflow
3. **FormationModel** - Formations et certifications
4. **FichePaieModel** - Bulletins de salaire
5. **ContratTravailModel** - Contrats de travail
6. **TempsTravailModel** - Suivi des heures
7. **RecrutementModel** - Processus de recrutement
8. **CompetenceModel** - Compétences et certifications
9. **EvaluationPerformanceModel** - Évaluations de performance
10. **AbsenceModel** - Gestion des absences
11. **ShiftModel** - Gestion des shifts
12. **PrimeBonusModel** - Primes et bonus
13. **AvantageSocialModel** - Avantages sociaux
14. **UniformeEquipementModel** - Uniformes et équipements
15. **VisaPermisModel** - Visas et permis de travail

### Services métier (13 services)

1. **EmployeService** - CRUD complet des employés
2. **CongeService** - Gestion des congés avec approbation
3. **FormationService** - Gestion des formations
4. **FichePaieService** - Gestion de la paie
5. **ContratTravailService** - Gestion des contrats
6. **TempsTravailService** - Validation des temps
7. **RecrutementService** - Suivi des recrutements
8. **CompetenceService** - Gestion des compétences
9. **OnboardingService** ⭐ - Processus d'intégration automatisé
10. **OffboardingService** ⭐ - Processus de sortie automatisé
11. **EvaluationPerformanceService** ⭐ - Évaluations avec IA
12. **ConformiteLegaleService** ⭐ - Vérification de conformité
13. **RhFacade** - Interface simplifiée (Pattern Facade)

### API REST

**Base URL** : `/api/v1/rh`

#### Endpoints principaux

**Employés** (`/api/v1/rh/employes`)
- `POST /` - Créer un employé
- `GET /{uuid}` - Récupérer par UUID
- `GET /paginated` - Liste paginée
- `GET /departement/{departement}` - Par département
- `PATCH /{uuid}/activate` - Activer
- `PATCH /{uuid}/deactivate` - Désactiver

**Congés** (`/api/v1/rh/conges`)
- `POST /` - Créer une demande
- `POST /{uuid}/approve` - Approuver
- `POST /{uuid}/reject` - Rejeter
- `GET /employe/{employeId}` - Par employé

**Onboarding** (`/api/v1/rh/onboarding`)
- `POST /{employeUuid}/demarrer` - Démarrer l'onboarding
- `POST /{employeUuid}/etape/{etape}/valider` - Valider une étape
- `POST /{employeUuid}/finaliser` - Finaliser

**Offboarding** (`/api/v1/rh/offboarding`)
- `POST /{employeUuid}/demarrer` - Démarrer l'offboarding
- `POST /{employeUuid}/etape/{etape}/valider` - Valider une étape

**Évaluations** (`/api/v1/rh/evaluations`)
- `POST /` - Démarrer une évaluation
- `PUT /{uuid}/completer` - Compléter une évaluation
- `POST /{uuid}/valider` - Valider une évaluation

**Conformité** (`/api/v1/rh/conformite`)
- `GET /employe/{employeUuid}/verifier` - Vérifier la conformité
- `GET /employe/{employeUuid}/plan-action` - Générer plan d'action

#### Headers requis

Toutes les requêtes doivent inclure :
- `X-Organisation-Id` : **Obligatoire** - ID de l'organisation
- `X-Hotel-Id` : Optionnel - ID de l'hôtel
- `X-Username` : Pour création/modification

### Frontend Web

**Base URL** : `/rh`

Pages disponibles :
- `/rh` - Page d'accueil
- `/rh/employes` - Liste des employés (pagination)
- `/rh/employes/{uuid}` - Détails employé
- `/rh/employes/new` - Formulaire création
- `/rh/conges` - Liste des congés
- `/rh/formations` - Liste des formations
- `/rh/fiches-paie` - Liste des fiches de paie
- `/rh/dashboard` - Tableau de bord RH

Technologies : Bootstrap 5.3, Bootstrap Icons, Thymeleaf

### Workflows Enterprise

#### Onboarding automatisé

Processus d'intégration avec checklist dynamique selon :
- Pays de l'employé (FRA, CMR, extensible)
- Poste occupé
- Type de contrat

Étapes automatiques :
- Validation des documents d'identité
- Création du contrat de travail
- Visite médicale
- Formation sécurité
- Génération des accès système
- Attribution des équipements
- Déclarations légales par pays

#### Offboarding automatisé

Processus de sortie avec :
- Révocation automatique des accès
- Récupération des équipements
- Solde de tout compte
- Déclarations légales
- Génération des attestations

#### Évaluations de performance

Système d'évaluation avec :
- Scores pondérés (40% compétences, 40% objectifs, 20% comportement)
- Recommandations IA automatiques
- Workflow de validation hiérarchique
- Types : Annuel, semestriel, trimestriel, probation, promotion

### Conformité & Sécurité

#### RGPD

- ✅ Base légale du traitement
- ✅ Durée de rétention configurable
- ✅ Consentement géré
- ✅ Droits des personnes (accès, rectification, suppression)
- ✅ Vérification automatique dans `ConformiteLegaleService`

#### Multi-pays

Support actuel : **FRA** (France), **CMR** (Cameroun) - Extensible

Vérifications automatiques :
- Documents légaux (CNPS, NIF, passeport, etc.)
- Visas et permis de travail
- Formations obligatoires
- Conformité fiscale
- Obligations légales par pays

#### Sécurité

- Niveaux d'accès granulaires (STANDARD, ELEVE, ADMIN, SUPER_ADMIN)
- Permissions et rôles (JSON)
- Révocation automatique lors de l'offboarding
- Audit complet (toutes les opérations tracées)
- Soft delete (données conservées pour audit)

### IA & Analytics

- **Score d'engagement** : Calculé automatiquement
- **Risque de départ** : Prédiction IA
- **Recommandations de promotion** : Basées sur les évaluations
- **Prédictions de performance** : Analyse des tendances
- **Score de conformité** : Vérification automatique

### Architecture Event-Driven

5 types d'événements avec traitement asynchrone :
- **OnboardingEvent** : ONBOARDING_DEMARRE, ONBOARDING_ETAPE_VALIDEE, ONBOARDING_COMPLETE
- **OffboardingEvent** : OFFBOARDING_DEMARRE, OFFBOARDING_ETAPE_VALIDEE, OFFBOARDING_COMPLETE
- **EvaluationEvent** : EVALUATION_DEMARREE, EVALUATION_COMPLETEE, EVALUATION_VALIDEE
- **CongeEvent** : CONGE_DEMANDE, CONGE_APPROUVE, CONGE_REJETE
- **EmployeEvent** : Événements liés aux employés

Traitement asynchrone via `RhEventListener` avec notifications automatiques.

---

## 📋 Prérequis

### Logiciels requis

- **Java** : 17 ou supérieur
- **Maven** : 3.8 ou supérieur
- **PostgreSQL** : 12 ou supérieur
- **IDE** : IntelliJ IDEA, Eclipse, ou VS Code (recommandé)

### Base de données

PostgreSQL doit être installé et configuré. Voir le fichier `create-database.sql` pour le script de création.

---

## 🚀 Installation

### 1. Cloner le projet

```bash
git clone <repository-url>
cd hotel
```

### 2. Configurer la base de données

Créer la base de données PostgreSQL :

```bash
psql -U postgres -f create-database.sql
```

Ou manuellement :

```sql
CREATE DATABASE hotel_db;
```

### 3. Configurer l'application

Éditer `src/main/resources/application.properties` :

```properties
# Base de données
spring.datasource.url=jdbc:postgresql://localhost:5432/hotel_db
spring.datasource.username=postgres
spring.datasource.password=votre_mot_de_passe

# Port de l'application
server.port=8098
```

### 4. Compiler le projet

```bash
mvn clean install
```

### 5. Lancer l'application

```bash
mvn spring-boot:run
```

Ou via l'IDE en exécutant `HotelApplication.java`.

L'application sera accessible sur : `http://localhost:8098`

---

## ⚙️ Configuration

### Configuration de base

Le fichier `application.properties` contient toutes les configurations nécessaires :

- **Base de données** : PostgreSQL
- **JPA/Hibernate** : Configuration automatique des tables
- **Thymeleaf** : Templates web
- **Swagger** : Documentation API sur `/swagger-ui.html`
- **Actuator** : Monitoring sur `/actuator`

### Multi-tenant

Toutes les requêtes API doivent inclure le header `X-Organisation-Id` pour l'isolation des données.

### Logging

Configuration dans `application.properties` :

```properties
logging.level.projet_hotelier.hotel=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

---

## 💻 Utilisation

### API REST

#### Exemple : Créer un employé

```bash
curl -X POST http://localhost:8098/api/v1/rh/employes \
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
    "dateEmbauche": "2024-01-01",
    "paysCode": "FRA"
  }'
```

#### Exemple : Démarrer un onboarding

```bash
curl -X POST http://localhost:8098/api/v1/rh/onboarding/{employeUuid}/demarrer \
  -H "X-Organisation-Id: 1" \
  -H "X-Username: admin"
```

#### Exemple : Vérifier la conformité

```bash
curl -X GET http://localhost:8098/api/v1/rh/conformite/employe/{employeUuid}/verifier \
  -H "X-Organisation-Id: 1"
```

### Interface Web

Accéder à l'interface web du module RH :

1. Ouvrir le navigateur : `http://localhost:8098/rh`
2. Naviguer dans les différentes sections :
   - Liste des employés
   - Gestion des congés
   - Formations
   - Fiches de paie
   - Tableau de bord

### Documentation API

Swagger UI disponible sur : `http://localhost:8098/swagger-ui.html`

---

## 🔧 Architecture technique

### Design Patterns implémentés

1. **Repository Pattern** : Accès aux données via JPA
2. **Service Layer Pattern** : Logique métier centralisée
3. **DTO Pattern** : Séparation présentation/domaine
4. **Mapper Pattern (MapStruct)** : Transformation automatique
5. **Factory Pattern** : Création d'entités complexes
6. **Strategy Pattern** : Validation métier flexible
7. **Facade Pattern** : Interface simplifiée
8. **Template Method Pattern** : Algorithmes communs

### Traçabilité

Chaque entité inclut :
- **Audit** : `dateCreation`, `dateModification`, `creePar`, `modifiePar`, `version`
- **Trace technique** : `traceId`, `spanId`, `correlationId`, `requestId`, `operationId`

### Soft Delete

Toutes les suppressions sont des "soft delete" :
- Les données restent en base pour audit
- `supprime = true` et `actif = false`

### Pagination

Par défaut : 20 éléments par page  
Configurable via les paramètres `page` et `size`

---

## 🔐 Sécurité & Conformité

### Multi-tenant

- Isolation complète des données par `organisationId`
- Filtrage optionnel par `hotelId`
- Toutes les requêtes doivent inclure `X-Organisation-Id`

### RGPD

- Conformité complète avec gestion des données personnelles
- Base légale du traitement
- Durée de rétention configurable
- Consentement géré
- Droits des personnes (accès, rectification, suppression)

### Conformité légale

- Support multi-pays (FRA, CMR, extensible)
- Vérification automatique des documents légaux
- Conformité fiscale par pays
- Obligations légales locales

### Audit

- Toutes les opérations sont tracées
- Historique complet des modifications
- Traçabilité technique complète

---

## 📚 Documentation

### Documentation disponible

- **Module RH** : [docs/INVENTAIRE_COMPLET_MODULE_RH.md](docs/INVENTAIRE_COMPLET_MODULE_RH.md)
- **Module RH (README)** : [src/main/java/projet_hotelier/hotel/module/rh/README.md](src/main/java/projet_hotelier/hotel/module/rh/README.md)
- **Architecture Enterprise** : Voir documentation dans le module RH

### API Documentation

- **Swagger UI** : `http://localhost:8098/swagger-ui.html`
- **OpenAPI JSON** : `http://localhost:8098/api-docs`

---

## 🤝 Contribution

### Conventions de code

- Respecter l'architecture existante
- Utiliser les design patterns appropriés
- Ajouter des tests unitaires et d'intégration
- Documenter le code avec JavaDoc
- Suivre les conventions de nommage Java

### Structure des modules

Chaque module doit suivre cette structure :

```
module/
├── controller/        # Contrôleurs REST
├── service/          # Services métier
├── model/            # Entités JPA
├── repository/       # Repositories JPA
├── dto/              # DTOs (request/response)
├── mapper/           # Mappers MapStruct
└── enumeration/      # Énumérations
```

### Tests

- Tests unitaires pour les services
- Tests d'intégration pour les contrôleurs
- Tests de validation pour les DTOs
- Tests de mappers

---

## 📊 Statistiques du projet

### Code

- **Modules** : 5 modules métier (RH, Clientele, Finances, Planning, Reporting)
- **Modèles de données** : 100+ entités JPA
- **Services** : 50+ services métier
- **Contrôleurs** : 30+ contrôleurs REST
- **Endpoints** : 200+ endpoints API
- **Repositories** : 50+ repositories JPA
- **DTOs** : 100+ DTOs
- **Mappers** : 30+ mappers MapStruct

### Module RH (le plus avancé)

- **15 modèles** avec 500+ champs au total
- **13 services** métier complets
- **13 contrôleurs REST** + 1 web
- **100+ endpoints** API
- **27 DTOs**
- **9 mappers** MapStruct
- **5 types d'événements**
- **8 design patterns**

---

## 🚀 Statut de production

### Module RH

**✅ PRODUCTION-READY**

- Architecture enterprise-grade complète
- Fonctionnalités complètes implémentées
- Conformité multi-pays et RGPD
- IA & Analytics intégrés
- Frontend web opérationnel
- API REST complète et documentée

### Autres modules

- **Clientele** : En développement
- **Finances** : En développement
- **Planning** : En développement
- **Reporting** : En développement

---

## 📞 Support

Pour toute question ou problème :

1. Consulter la documentation dans `docs/`
2. Vérifier la documentation API (Swagger)
3. Examiner les logs de l'application
4. Contacter l'équipe de développement

---

## 📝 Notes importantes

1. **Multi-tenant** : Toutes les requêtes doivent inclure `X-Organisation-Id`
2. **Soft Delete** : Les suppressions ne suppriment pas réellement les données
3. **Audit** : Toutes les opérations sont tracées
4. **Pagination** : Par défaut 20 éléments par page
5. **Validation** : Utilise les annotations Jakarta Validation
6. **Événements** : Traitement asynchrone via listeners
7. **Conformité** : Vérification automatique via services dédiés
8. **Workflows** : Onboarding/Offboarding avec checklists dynamiques

---

## 📄 Licence

[À définir]

---

**Version** : 2.0.0 Enterprise  
**Dernière mise à jour** : 2024  
**Auteur** : Équipe Développement

---

## 🎯 Roadmap

### Court terme
- [ ] Finalisation des modules Clientele, Finances, Planning, Reporting
- [ ] Tests automatisés complets
- [ ] Documentation API complète

### Moyen terme
- [ ] Support de nouveaux pays (extension multi-pays)
- [ ] Intégrations externes (paiement, réservation)
- [ ] Application mobile

### Long terme
- [ ] Architecture microservices
- [ ] Déploiement cloud
- [ ] Analytics avancés avec ML

---

**FIN DU README**
