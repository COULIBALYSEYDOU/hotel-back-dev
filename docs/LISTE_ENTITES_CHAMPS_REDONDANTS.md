# LISTE DES ENTITÉS AVEC CHAMPS REDONDANTS
═══════════════════════════════════════════════════════════════════════

**Date** : 2026-02-06  
**Objectif** : Identifier toutes les entités qui redéfinissent des champs déjà présents dans BaseEntity

---

## 🔍 CRITÈRES DE DÉTECTION

Une entité a des champs redondants si elle :
- ✅ Étend `BaseEntity`
- ❌ Redéfinit un ou plusieurs de ces champs :
  - `createdAt`, `createdBy`, `modifiedAt`, `modifiedBy` (audit)
  - `version` (optimistic locking)
  - `deleted`, `deletedAt`, `deletedBy` (soft delete)
  - `status` (enum Status)

---

## 📋 LISTE DES ENTITÉS À CORRIGER

### Module Clientele

#### ✅ CORRIGÉ
- `AvisClientModel` - ✅ CORRIGÉ

#### ❌ À CORRIGER
- À identifier...

### Module Finances

#### ❌ À CORRIGER
- À identifier...

### Module RH

#### ❌ À CORRIGER
- À identifier...

### Module Planning

#### ❌ À CORRIGER
- À identifier...

### Module Reporting

#### ❌ À CORRIGER
- À identifier...

---

## 📊 STATISTIQUES

- **Total entités analysées** : En cours...
- **Entités avec champs redondants** : En cours...
- **Entités corrigées** : 1 (AvisClientModel)
- **Entités restantes** : En cours...

---

**Note** : Cette liste sera mise à jour au fur et à mesure de l'analyse.
