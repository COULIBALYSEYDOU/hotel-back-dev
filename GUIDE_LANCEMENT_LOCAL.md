# Guide de lancement local — `hotel-back-dev`

> Spring Boot 3.3.4 · Java 17 · PostgreSQL · Port **8098**

## Pré-requis

| Outil | Version | Vérifier |
|---|---|---|
| JDK | 17+ | `java -version` |
| Maven | 3.8+ (3.6 fonctionne aussi) | `mvn -v` |
| PostgreSQL | 13+ | `psql --version` |
| (Optionnel) Node | 18+ | pour le frontend Angular |

## 1. Créer la base de données

PostgreSQL doit être démarré. Identifiants attendus par défaut : **`postgres` / `postgres`** (modifiables dans `src/main/resources/application.properties`).

```bash
cd /home/coulibaly/Téléchargements/hotel-back-dev
psql -U postgres -f create-database.sql
```

Ce script crée la base **`hotel_db`**. Hibernate générera ensuite le schéma automatiquement au premier démarrage (`spring.jpa.hibernate.ddl-auto=update`).

## 2. Lancer le backend

```bash
cd /home/coulibaly/Téléchargements/hotel-back-dev
mvn clean spring-boot:run
```

Au premier lancement Maven télécharge les dépendances (≈ 2-5 min). L'application est prête quand vous voyez :

```
Tomcat started on port 8098
Started HotelApplication in X.XXX seconds
```

## 3. Accéder à la documentation API

Une fois l'application démarrée :

| Ressource | URL |
|---|---|
| **Swagger UI** (interactif) | http://localhost:8098/swagger-ui.html |
| **OpenAPI JSON** (brut) | http://localhost:8098/api-docs |
| Actuator (santé) | http://localhost:8098/actuator/health |
| IHM RH (Thymeleaf) | http://localhost:8098/rh |

### Exporter la doc OpenAPI au format JSON

```bash
curl -s http://localhost:8098/api-docs > openapi.json
```

Pour générer une doc HTML statique à partir de l'OpenAPI :

```bash
npx @redocly/cli build-docs openapi.json -o api-doc.html
```

## 4. Endpoints exposés (inventaire statique)

202 endpoints REST répartis sur 26 controllers, dont :

- `/api/v1/rh/...` — module RH (94 endpoints sur 13 ressources)
- `/api/clientele/...` — module Clientèle (108 endpoints sur 6 sous-modules)
- `/rh/...` — IHM Thymeleaf du module RH

Voir `Documentation_API.docx` pour le détail complet.

## 5. (Optionnel) Lancer le frontend Angular

```bash
cd frontend
npm install
npm start          # http://localhost:4200
```

## Erreurs courantes

| Erreur | Cause | Solution |
|---|---|---|
| `Connection refused: localhost:5432` | PostgreSQL non démarré | `sudo systemctl start postgresql` |
| `database "hotel_db" does not exist` | Étape 1 non exécutée | Rejouer `psql -U postgres -f create-database.sql` |
| `Port 8098 already in use` | Une autre instance tourne | `lsof -i:8098` puis `kill <pid>`, ou changer `server.port` |
| `Access denied for user postgres` | Mot de passe différent | Adapter `spring.datasource.password` dans `application.properties` |

## Aller plus loin (recommandé avant prod)

- **Sécurité** : `SecurityConfig` est en `permitAll()` — implémenter JWT/OAuth2 avant tout déploiement externe.
- **Schéma** : remplacer `ddl-auto=update` par `validate` + Flyway/Liquibase.
- **Credentials** : sortir le mot de passe Postgres du fichier `.properties` (variables d'environnement ou Spring Cloud Config).

Voir `Rapport_Analyse_Projet.docx` pour l'analyse complète.
