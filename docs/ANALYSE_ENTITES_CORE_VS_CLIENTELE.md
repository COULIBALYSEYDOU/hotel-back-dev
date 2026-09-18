# ANALYSE ENTITÉS GLOBALES CORE vs CLIENTELE
═══════════════════════════════════════════════════════════════════════

**Date** : 2026-02-06  
**Objectif** : Identifier les entités globales dans `core/` et éviter les duplications dans `clientele/`

---

## 📊 RÉSUMÉ EXÉCUTIF

### Principe architectural
- ✅ **`core/`** : Gère **TOUTES** les entités **globales** partagées entre tous les modules
- ✅ **`clientele/model/`** : Gère uniquement les entités **spécifiques** au module Clientèle

---

## 🔍 ANALYSE DÉTAILLÉE

### 1️⃣ INTÉGRATION (`core/integration/` vs `clientele/model/integration/`)

#### Entités dans `core/integration/` :
| Entité | Statut | Description |
|--------|--------|-------------|
| `CleApi.java` | ⚠️ **VIDE** | Classe vide, non implémentée |
| `WebhookConfig.java` | ⚠️ **VIDE** | Classe vide, non implémentée |
| `SourceDonneeExterneModel.java` | ✅ Implémentée | Source de données externes |

#### Entités créées dans `clientele/model/integration/` :
| Entité | Statut | Action recommandée |
|--------|--------|-------------------|
| `APIKey.java` | ✅ Créée | **DÉPLACER vers `core/integration/`** (remplace `CleApi.java`) |
| `WebhookConfiguration.java` | ✅ Créée | **DÉPLACER vers `core/integration/`** (remplace `WebhookConfig.java`) |
| `IntegrationTierce.java` | ✅ Créée | **GARDER dans `clientele/`** (spécifique clientèle) |
| `LogIntegration.java` | ✅ Créée | **GARDER dans `clientele/`** (logs spécifiques clientèle) |

**Recommandation** :
- ✅ `APIKey` et `WebhookConfiguration` sont **globales** → doivent être dans `core/integration/`
- ✅ `IntegrationTierce` et `LogIntegration` sont **spécifiques clientèle** → restent dans `clientele/model/integration/`

---

### 2️⃣ GÉOGRAPHIE (`core/geo/`)

#### Entités globales existantes :
| Entité | Description | Utilisation |
|--------|-------------|-------------|
| `Devise.java` | ✅ **EXISTE** | Devises monétaires |
| `Langue.java` | ✅ **EXISTE** | Langues supportées |
| `FuseauHoraire.java` | ✅ **EXISTE** | Timezones |
| `Pays.java` | ✅ **EXISTE** | Pays |
| `Region.java` | ✅ **EXISTE** | Régions |
| `Ville.java` | ✅ **EXISTE** | Villes |

**Action** : ✅ **AUCUNE ACTION** - Ces entités sont déjà dans `core/geo/` et doivent être utilisées par tous les modules.

---

### 3️⃣ ORGANISATION (`core/organisation/`)

#### Entités globales existantes :
| Entité | Description | Utilisation |
|--------|-------------|-------------|
| `OrganisationSaaS.java` | ✅ **EXISTE** | Organisation SaaS principale |
| `Societe.java` | ✅ **EXISTE** | Sociétés |
| `PlanSaaS.java` | ✅ **EXISTE** | Plans d'abonnement |
| `Abonnement.java` | ✅ **EXISTE** | Abonnements |

#### Entité créée dans `clientele/model/organisation/` :
| Entité | Statut | Action recommandée |
|--------|--------|-------------------|
| `ConfigurationHotel.java` | ✅ Créée | **GARDER dans `clientele/`** (config spécifique module clientèle) |

**Recommandation** : ✅ `ConfigurationHotel` est **spécifique au module clientèle** (check-in/check-out, politiques d'annulation) → reste dans `clientele/model/organisation/`

---

### 4️⃣ SÉCURITÉ (`core/securite/`)

#### Entités globales existantes :
| Entité | Description | Utilisation |
|--------|-------------|-------------|
| `Utilisateur.java` | ✅ **EXISTE** | Utilisateurs système |
| `Role.java` | ✅ **EXISTE** | Rôles |
| `SessionUtilisateur.java` | ✅ **EXISTE** | Sessions utilisateurs |
| `ProfilUtilisateur.java` | ✅ **EXISTE** | Profils utilisateurs |
| `PolitiqueMotDePasse.java` | ✅ **EXISTE** | Politiques de mots de passe |

**Action** : ✅ **AUCUNE ACTION** - Ces entités sont déjà dans `core/securite/` et doivent être utilisées par tous les modules.

---

### 5️⃣ AUDIT (`core/audit/`)

#### Entités globales existantes :
| Entité | Description | Statut |
|--------|-------------|--------|
| `JournalAudit.java` | Journal d'audit | ⚠️ **INCOMPLET** (pas d'annotations JPA) |
| `LogErreur.java` | Logs d'erreurs | ✅ Implémentée |
| `TraceRequest.java` | Traces de requêtes | ✅ Implémentée |
| `EvenementSysteme.java` | Événements système | ✅ Implémentée |

#### Entités créées dans `clientele/model/compliance/` :
| Entité | Statut | Action recommandée |
|--------|--------|-------------------|
| `ConformiteGDPR.java` | ✅ Créée | **GARDER dans `clientele/`** (spécifique GDPR clientèle) |
| `PolitiqueConfidentialite.java` | ✅ Créée | **GARDER dans `clientele/`** (CGU/CGV clientèle) |
| `ConsentementClient.java` | ✅ Créée | **GARDER dans `clientele/`** (consentements clients) |

**Recommandation** : ✅ Les entités de compliance sont **spécifiques au module clientèle** (consentements clients, politiques clientèle) → restent dans `clientele/model/compliance/`

---

### 6️⃣ I18N / TRADUCTIONS

#### Entités dans `core/` :
| Entité | Description | Statut |
|--------|-------------|--------|
| `Langue.java` (dans `core/geo/`) | ✅ **EXISTE** | Langues supportées |

#### Entité créée dans `clientele/model/i18n/` :
| Entité | Statut | Action recommandée |
|--------|--------|-------------------|
| `Traduction.java` | ✅ Créée | **GARDER dans `clientele/`** (traductions spécifiques clientèle) |

**Recommandation** : ✅ `Traduction` est **spécifique au module clientèle** (traductions des libellés UI, emails, SMS clientèle) → reste dans `clientele/model/i18n/`

**Note** : `Langue` dans `core/geo/` gère les langues disponibles, tandis que `Traduction` dans `clientele/i18n/` gère les traductions des textes métier.

---

### 7️⃣ REPORTING (`clientele/model/reporting/`)

#### Entités créées dans `clientele/model/reporting/` :
| Entité | Statut | Action recommandée |
|--------|--------|-------------------|
| `RapportPersonnalise.java` | ✅ Créée | **GARDER dans `clientele/`** (rapports spécifiques clientèle) |
| `MetriquePerformance.java` | ✅ Créée | **GARDER dans `clientele/`** (métriques hôtelières clientèle) |
| `TableauBord.java` | ✅ Créée | **GARDER dans `clientele/`** (dashboards clientèle) |

**Recommandation** : ✅ Toutes les entités de reporting sont **spécifiques au module clientèle** → restent dans `clientele/model/reporting/`

---

## 📋 PLAN D'ACTION RECOMMANDÉ

### ✅ Actions immédiates

1. **DÉPLACER** `APIKey.java` de `clientele/model/integration/` vers `core/integration/`
   - Supprimer `CleApi.java` (vide)
   - Renommer si nécessaire pour cohérence

2. **DÉPLACER** `WebhookConfiguration.java` de `clientele/model/integration/` vers `core/integration/`
   - Supprimer `WebhookConfig.java` (vide)
   - Renommer si nécessaire pour cohérence

3. **GARDER** toutes les autres entités dans `clientele/model/` car elles sont spécifiques au module

---

## 🎯 RÈGLES D'ARBITRAGE

### Quand créer dans `core/` ?
- ✅ Entité utilisée par **plusieurs modules** (RH, Réservation, Facturation, etc.)
- ✅ Entité **générique** (Utilisateur, Organisation, Devise, Langue)
- ✅ Entité **infrastructure** (API Keys, Webhooks, Configurations système)

### Quand créer dans `clientele/model/` ?
- ✅ Entité **spécifique** au module Clientèle
- ✅ Entité liée aux **clients** (Client, ConsentementClient, ClientProfil)
- ✅ Entité liée aux **métriques clientèle** (MétriquesPerformance, Rapports clientèle)
- ✅ Entité liée aux **configurations clientèle** (ConfigurationHotel pour clientèle)

---

## 📊 STATISTIQUES FINALES

### Entités dans `core/` (globales) :
- **Géographie** : 8 entités (Devise, Langue, Pays, Region, Ville, etc.)
- **Organisation** : 6 entités (OrganisationSaaS, Societe, PlanSaaS, etc.)
- **Sécurité** : 5 entités (Utilisateur, Role, SessionUtilisateur, etc.)
- **Audit** : 4 entités (JournalAudit, LogErreur, TraceRequest, etc.)
- **Intégration** : 3 entités (CleApi vide, WebhookConfig vide, SourceDonneeExterneModel)
- **Structure** : 9 entités (Hotel, Departement, GroupeHotelier, etc.)
- **Config** : 7 entités (HotelConfigModel, ParametreGlobalModel, etc.)

**Total `core/`** : ~42 entités globales

### Entités dans `clientele/model/` (spécifiques) :
- **Client** : 3 entités (Client, ClientProfil, ClientPreference)
- **Compliance** : 3 entités (ConformiteGDPR, PolitiqueConfidentialite, ConsentementClient)
- **Integration** : 2 entités (IntegrationTierce, LogIntegration) - après déplacement
- **Reporting** : 3 entités (RapportPersonnalise, MetriquePerformance, TableauBord)
- **I18N** : 1 entité (Traduction)
- **Organisation** : 1 entité (ConfigurationHotel)

**Total `clientele/model/`** : 13 entités spécifiques (après déplacement)

---

## ✅ CONCLUSION

**Architecture respectée** : 
- ✅ `core/` gère les entités **globales**
- ✅ `clientele/model/` gère les entités **spécifiques**

**Actions requises** :
1. Déplacer `APIKey` et `WebhookConfiguration` vers `core/integration/`
2. Supprimer les classes vides `CleApi` et `WebhookConfig` dans `core/integration/`
3. Garder toutes les autres entités dans `clientele/model/`

---

**Rapport généré le 2026-02-06**
