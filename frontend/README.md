# Frontend Angular - Plateforme de Gestion Hôtelière

## 🚀 Démarrage rapide

### Prérequis

- Node.js 18+ et npm
- Angular CLI 17+
- Backend Spring Boot démarré sur `http://localhost:8098`

### Installation

```bash
cd frontend
npm install
```

### Démarrage

```bash
npm start
```

L'application sera accessible sur `http://localhost:4200`

## 📁 Structure du projet

```
frontend/
├── src/
│   ├── app/
│   │   ├── core/              # Services, interceptors, guards
│   │   ├── shared/            # Composants réutilisables
│   │   ├── features/          # Modules fonctionnels (lazy loaded)
│   │   │   ├── rh/            # Module RH (Production-Ready)
│   │   │   ├── dashboard/     # Dashboard principal
│   │   │   ├── admin/         # Administration
│   │   │   └── ...
│   │   └── layout/            # Layout (sidebar, topbar)
│   ├── styles.css             # Styles globaux TailwindCSS
│   └── main.ts                # Point d'entrée
├── angular.json
├── package.json
└── tsconfig.json
```

## 🏗️ Architecture

### Standalone Components

Tous les composants sont standalone (pas de NgModules).

### Lazy Loading

Tous les modules fonctionnels sont chargés en lazy loading via le routing.

### Multi-tenant

Les headers `X-Organisation-Id`, `X-Hotel-Id` et `X-Username` sont injectés automatiquement via l'intercepteur `apiHeadersInterceptor`.

### Services

- `TenantService` : Gestion du tenant (organisation, hôtel)
- `AuthService` : Authentification
- `EmployeService` : Gestion des employés (module RH)

## 🎨 Composants partagés

- `app-status-badge` : Badge de statut coloré
- `app-kpi-card` : Carte KPI avec tendance
- `app-data-table` : Table avec pagination, tri, recherche
- `app-confirm-dialog` : Modal de confirmation
- `app-page-header` : En-tête de page avec breadcrumb

## 📦 Module RH (Production-Ready)

### Routes

- `/rh/employes` : Liste des employés
- `/rh/employes/new` : Créer un employé
- `/rh/employes/:uuid` : Détails employé
- `/rh/employes/:uuid/edit` : Modifier un employé
- `/rh/conges` : Liste des congés
- `/rh/formations` : Liste des formations
- `/rh/fiches-paie` : Liste des fiches de paie
- `/rh/evaluations` : Liste des évaluations
- `/rh/onboarding` : Onboarding
- `/rh/offboarding` : Offboarding
- `/rh/dashboard` : Dashboard RH

### Service EmployeService

```typescript
getAll(params, departement?)     // Liste paginée
getByUuid(uuid)                  // Détails par UUID
getById(id)                      // Détails par ID
getByDepartement(departement)    // Par département
create(dto)                      // Créer
update(uuid, dto)                // Modifier
activate(uuid)                   // Activer
deactivate(uuid)                 // Désactiver
delete(uuid)                     // Supprimer (soft delete)
```

## 🔧 Configuration

### API Base URL

Modifier dans `src/app/features/rh/services/employe.service.ts` :

```typescript
private readonly apiUrl = 'http://localhost:8098/api/v1/rh/employes';
```

### Tenant par défaut

Modifier dans `src/app/core/services/tenant.service.ts` :

```typescript
// Valeurs par défaut pour le développement
this._organisationId.set(1);
this._username.set('admin');
```

## 🧪 Tests

```bash
npm test
```

## 📝 Notes

- Tous les composants utilisent **Signals** (Angular 17+)
- **TailwindCSS** pour le styling
- **Lucide Angular** pour les icônes
- **ngx-toastr** pour les notifications
- **Reactive Forms** pour les formulaires

## 🚧 À implémenter

- [ ] Dashboard avec graphiques (Chart.js)
- [ ] Module Admin complet
- [ ] Module Réservations
- [ ] Module Finance
- [ ] Module Clientèle
- [ ] Authentification JWT complète
- [ ] Tests unitaires et d'intégration
- [ ] Internationalisation (i18n)
