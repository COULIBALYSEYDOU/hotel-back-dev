# STRUCTURE ACTUELLE MODULE CLIENTÈLE
═══════════════════════════════════

**Date de génération** : 2026-02-06  
**Chemin racine** : `src/main/java/projet_hotelier/hotel/module/clientele/`

---

## 📁 STRUCTURE COMPLÈTE DES DOSSIERS

```
clientele/
├── automation/
├── config/
├── controller/
│   ├── api/
│   └── web/
├── dto/
│   ├── request/
│   │   ├── avis/
│   │   ├── campagne/
│   │   ├── client/
│   │   ├── fidelite/
│   │   ├── interaction/
│   │   └── notification/
│   └── response/
│       ├── avis/
│       ├── campagne/
│       ├── client/
│       ├── fidelite/
│       ├── interaction/
│       └── notification/
├── enumeration/
├── event/
├── exception/
├── i18n/
├── listener/
├── mapper/
│   └── notification/
├── model/
│   ├── analytics/
│   │   ├── alerte/
│   │   ├── churn/
│   │   ├── comportement/
│   │   ├── kpi/
│   │   ├── prediction/
│   │   ├── profil/
│   │   ├── rapport/
│   │   ├── tendances/
│   ├── avis/
│   ├── campagne/
│   ├── chambre/
│   │   ├── amenite/
│   │   ├── disponibilite/
│   │   ├── etat/
│   │   ├── historique/
│   │   ├── inspection/
│   │   ├── maintenance/
│   │   ├── package/
│   │   ├── pricing/
│   │   ├── promotion/
│   │   ├── regle/
│   │   ├── restriction/
│   │   ├── saison/
│   │   ├── tarif/
│   │   ├── type/
│   │   └── yield/
│   ├── channel/
│   │   ├── commission/
│   │   ├── disponibilite/
│   │   ├── gds/
│   │   ├── ota/
│   │   ├── reservation/
│   │   ├── sync/
│   │   └── tarif/
│   ├── client/
│   │   ├── adresse/
│   │   ├── contact/
│   │   ├── document/
│   │   ├── preference/
│   │   ├── relation/
│   │   ├── tag/
│   │   └── voyageur/
│   ├── communication/
│   │   ├── appel/
│   │   ├── canal/
│   │   ├── chat/
│   │   ├── conversation/
│   │   ├── email/
│   │   ├── followup/
│   │   ├── historique/
│   │   ├── message/
│   │   ├── preference/
│   │   ├── rappel/
│   │   ├── rendezvous/
│   │   ├── sms/
│   │   └── visite/
│   ├── contrat/
│   │   ├── banquet/
│   │   ├── budget/
│   │   ├── conference/
│   │   ├── corporate/
│   │   ├── devis/
│   │   ├── equipement/
│   │   ├── evenement/
│   │   ├── facture/
│   │   ├── groupe/
│   │   ├── historique/
│   │   ├── menu/
│   │   ├── mice/
│   │   ├── participant/
│   │   ├── planning/
│   │   ├── salle/
│   │   └── seminaire/
│   ├── facturation/
│   │   ├── acompte/
│   │   ├── caution/
│   │   ├── facture/
│   │   ├── frais/
│   │   ├── historique/
│   │   ├── ligne/
│   │   ├── moyen/
│   │   ├── note/
│   │   ├── paiement/
│   │   ├── reglement/
│   │   ├── remboursement/
│   │   ├── remise/
│   │   ├── split/
│   │   ├── taxe/
│   │   └── transaction/
│   ├── fidelite/
│   ├── integration/
│   │   ├── config/
│   │   ├── crs/
│   │   ├── gds/
│   │   ├── log/
│   │   ├── ota/
│   │   ├── payment/
│   │   ├── pms/
│   │   └── sync/
│   ├── interaction/
│   ├── marketing/
│   │   ├── abtest/
│   │   ├── avantage/
│   │   ├── carte/
│   │   ├── cible/
│   │   ├── coupon/
│   │   ├── email/
│   │   ├── historique/
│   │   ├── niveau/
│   │   ├── offre/
│   │   ├── performance/
│   │   ├── push/
│   │   ├── segment/
│   │   ├── sms/
│   │   └── transaction/
│   ├── multipropriete/
│   │   ├── chaine/
│   │   ├── groupe/
│   │   ├── loyalty/
│   │   ├── marque/
│   │   ├── partage/
│   │   ├── relation/
│   │   ├── reporting/
│   │   └── tarification/
│   ├── notification/
│   ├── reservation/
│   │   ├── annulation/
│   │   ├── attente/
│   │   ├── blocage/
│   │   ├── checkin/
│   │   ├── checkout/
│   │   ├── demande/
│   │   ├── historique/
│   │   ├── invite/
│   │   ├── liste/
│   │   ├── modification/
│   │   ├── package/
│   │   ├── prereservation/
│   │   ├── promotion/
│   │   ├── sejour/
│   │   ├── service/
│   ├── restauration/
│   │   ├── allergie/
│   │   ├── commande/
│   │   ├── menu/
│   │   ├── plat/
│   │   ├── preference/
│   │   ├── regime/
│   │   ├── reservation/
│   │   └── roomservice/
│   ├── revenue/
│   │   ├── alerte/
│   │   ├── analyse/
│   │   ├── forecast/
│   │   ├── historique/
│   │   ├── kpi/
│   │   ├── optimisation/
│   │   ├── reporting/
│   │   └── strategie/
│   ├── rgpd/
│   │   ├── acces/
│   │   ├── audit/
│   │   ├── consentement/
│   │   ├── historique/
│   │   ├── portabilite/
│   │   ├── suppression/
│   │   ├── traitement/
│   │   └── violation/
│   ├── satisfaction/
│   │   ├── action/
│   │   ├── benchmark/
│   │   ├── enquete/
│   │   ├── escalade/
│   │   ├── feedback/
│   │   ├── historique/
│   │   ├── nps/
│   │   ├── question/
│   │   ├── reclamation/
│   │   ├── reponse/
│   │   ├── resolution/
│   │   ├── review/
│   │   ├── sentiment/
│   │   ├── suggestion/
│   │   └── theme/
│   ├── service/
│   │   ├── blanchisserie/
│   │   ├── conciergerie/
│   │   ├── demande/
│   │   ├── historique/
│   │   ├── reservation/
│   │   ├── spa/
│   │   ├── tarif/
│   │   └── type/
│   ├── notification/
│   └── (autres dossiers model/)
├── notification/
├── repository/
│   └── notification/
├── scheduler/
├── security/
├── service/
│   └── impl/
├── tenant/
└── validation/
```

---

## 📋 LISTE DÉTAILLÉE DES CHEMINS COMPLETS

### Niveau 1 - Dossiers principaux

1. `src/main/java/projet_hotelier/hotel/module/clientele/automation/`
2. `src/main/java/projet_hotelier/hotel/module/clientele/config/`
3. `src/main/java/projet_hotelier/hotel/module/clientele/controller/`
4. `src/main/java/projet_hotelier/hotel/module/clientele/dto/`
5. `src/main/java/projet_hotelier/hotel/module/clientele/enumeration/`
6. `src/main/java/projet_hotelier/hotel/module/clientele/event/`
7. `src/main/java/projet_hotelier/hotel/module/clientele/exception/`
8. `src/main/java/projet_hotelier/hotel/module/clientele/i18n/`
9. `src/main/java/projet_hotelier/hotel/module/clientele/listener/`
10. `src/main/java/projet_hotelier/hotel/module/clientele/mapper/`
11. `src/main/java/projet_hotelier/hotel/module/clientele/model/`
12. `src/main/java/projet_hotelier/hotel/module/clientele/notification/`
13. `src/main/java/projet_hotelier/hotel/module/clientele/repository/`
14. `src/main/java/projet_hotelier/hotel/module/clientele/scheduler/`
15. `src/main/java/projet_hotelier/hotel/module/clientele/security/`
16. `src/main/java/projet_hotelier/hotel/module/clientele/service/`
17. `src/main/java/projet_hotelier/hotel/module/clientele/tenant/`
18. `src/main/java/projet_hotelier/hotel/module/clientele/validation/`

### Niveau 2 - Sous-dossiers

#### Controller
- `src/main/java/projet_hotelier/hotel/module/clientele/controller/api/`
- `src/main/java/projet_hotelier/hotel/module/clientele/controller/web/`

#### DTO
- `src/main/java/projet_hotelier/hotel/module/clientele/dto/request/`
- `src/main/java/projet_hotelier/hotel/module/clientele/dto/response/`

#### DTO Request
- `src/main/java/projet_hotelier/hotel/module/clientele/dto/request/avis/`
- `src/main/java/projet_hotelier/hotel/module/clientele/dto/request/campagne/`
- `src/main/java/projet_hotelier/hotel/module/clientele/dto/request/client/`
- `src/main/java/projet_hotelier/hotel/module/clientele/dto/request/fidelite/`
- `src/main/java/projet_hotelier/hotel/module/clientele/dto/request/interaction/`
- `src/main/java/projet_hotelier/hotel/module/clientele/dto/request/notification/`

#### DTO Response
- `src/main/java/projet_hotelier/hotel/module/clientele/dto/response/avis/`
- `src/main/java/projet_hotelier/hotel/module/clientele/dto/response/campagne/`
- `src/main/java/projet_hotelier/hotel/module/clientele/dto/response/client/`
- `src/main/java/projet_hotelier/hotel/module/clientele/dto/response/fidelite/`
- `src/main/java/projet_hotelier/hotel/module/clientele/dto/response/interaction/`
- `src/main/java/projet_hotelier/hotel/module/clientele/dto/response/notification/`

#### Mapper
- `src/main/java/projet_hotelier/hotel/module/clientele/mapper/notification/`

#### Repository
- `src/main/java/projet_hotelier/hotel/module/clientele/repository/notification/`

#### Service
- `src/main/java/projet_hotelier/hotel/module/clientele/service/impl/`

### Niveau 2 - Model (Dossiers principaux)

1. `src/main/java/projet_hotelier/hotel/module/clientele/model/analytics/`
2. `src/main/java/projet_hotelier/hotel/module/clientele/model/avis/`
3. `src/main/java/projet_hotelier/hotel/module/clientele/model/campagne/`
4. `src/main/java/projet_hotelier/hotel/module/clientele/model/chambre/`
5. `src/main/java/projet_hotelier/hotel/module/clientele/model/channel/`
6. `src/main/java/projet_hotelier/hotel/module/clientele/model/client/`
7. `src/main/java/projet_hotelier/hotel/module/clientele/model/communication/`
8. `src/main/java/projet_hotelier/hotel/module/clientele/model/contrat/`
9. `src/main/java/projet_hotelier/hotel/module/clientele/model/facturation/`
10. `src/main/java/projet_hotelier/hotel/module/clientele/model/fidelite/`
11. `src/main/java/projet_hotelier/hotel/module/clientele/model/integration/`
12. `src/main/java/projet_hotelier/hotel/module/clientele/model/interaction/`
13. `src/main/java/projet_hotelier/hotel/module/clientele/model/marketing/`
14. `src/main/java/projet_hotelier/hotel/module/clientele/model/multipropriete/`
15. `src/main/java/projet_hotelier/hotel/module/clientele/model/notification/`
16. `src/main/java/projet_hotelier/hotel/module/clientele/model/reservation/`
17. `src/main/java/projet_hotelier/hotel/module/clientele/model/restauration/`
18. `src/main/java/projet_hotelier/hotel/module/clientele/model/revenue/`
19. `src/main/java/projet_hotelier/hotel/module/clientele/model/rgpd/`
20. `src/main/java/projet_hotelier/hotel/module/clientele/model/satisfaction/`
21. `src/main/java/projet_hotelier/hotel/module/clientele/model/service/`

### Niveau 3 - Model Analytics
- `src/main/java/projet_hotelier/hotel/module/clientele/model/analytics/alerte/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/analytics/churn/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/analytics/comportement/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/analytics/kpi/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/analytics/prediction/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/analytics/profil/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/analytics/rapport/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/analytics/tendances/`

### Niveau 3 - Model Chambre
- `src/main/java/projet_hotelier/hotel/module/clientele/model/chambre/amenite/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/chambre/disponibilite/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/chambre/etat/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/chambre/historique/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/chambre/inspection/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/chambre/maintenance/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/chambre/package/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/chambre/pricing/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/chambre/promotion/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/chambre/regle/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/chambre/restriction/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/chambre/saison/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/chambre/tarif/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/chambre/type/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/chambre/yield/`

### Niveau 3 - Model Channel
- `src/main/java/projet_hotelier/hotel/module/clientele/model/channel/commission/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/channel/disponibilite/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/channel/gds/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/channel/ota/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/channel/reservation/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/channel/sync/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/channel/tarif/`

### Niveau 3 - Model Client
- `src/main/java/projet_hotelier/hotel/module/clientele/model/client/adresse/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/client/contact/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/client/document/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/client/preference/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/client/relation/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/client/tag/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/client/voyageur/`

### Niveau 3 - Model Communication
- `src/main/java/projet_hotelier/hotel/module/clientele/model/communication/appel/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/communication/canal/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/communication/chat/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/communication/conversation/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/communication/email/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/communication/followup/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/communication/historique/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/communication/message/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/communication/preference/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/communication/rappel/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/communication/rendezvous/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/communication/sms/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/communication/visite/`

### Niveau 3 - Model Contrat
- `src/main/java/projet_hotelier/hotel/module/clientele/model/contrat/banquet/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/contrat/budget/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/contrat/conference/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/contrat/corporate/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/contrat/devis/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/contrat/equipement/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/contrat/evenement/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/contrat/facture/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/contrat/groupe/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/contrat/historique/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/contrat/menu/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/contrat/mice/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/contrat/participant/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/contrat/planning/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/contrat/salle/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/contrat/seminaire/`

### Niveau 3 - Model Facturation
- `src/main/java/projet_hotelier/hotel/module/clientele/model/facturation/acompte/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/facturation/caution/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/facturation/facture/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/facturation/frais/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/facturation/historique/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/facturation/ligne/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/facturation/moyen/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/facturation/note/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/facturation/paiement/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/facturation/reglement/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/facturation/remboursement/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/facturation/remise/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/facturation/split/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/facturation/taxe/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/facturation/transaction/`

### Niveau 3 - Model Integration
- `src/main/java/projet_hotelier/hotel/module/clientele/model/integration/config/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/integration/crs/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/integration/gds/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/integration/log/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/integration/ota/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/integration/payment/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/integration/pms/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/integration/sync/`

### Niveau 3 - Model Marketing
- `src/main/java/projet_hotelier/hotel/module/clientele/model/marketing/abtest/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/marketing/avantage/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/marketing/carte/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/marketing/cible/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/marketing/coupon/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/marketing/email/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/marketing/historique/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/marketing/niveau/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/marketing/offre/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/marketing/performance/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/marketing/push/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/marketing/segment/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/marketing/sms/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/marketing/transaction/`

### Niveau 3 - Model Multipropriete
- `src/main/java/projet_hotelier/hotel/module/clientele/model/multipropriete/chaine/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/multipropriete/groupe/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/multipropriete/loyalty/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/multipropriete/marque/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/multipropriete/partage/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/multipropriete/relation/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/multipropriete/reporting/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/multipropriete/tarification/`

### Niveau 3 - Model Reservation
- `src/main/java/projet_hotelier/hotel/module/clientele/model/reservation/annulation/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/reservation/attente/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/reservation/blocage/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/reservation/checkin/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/reservation/checkout/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/reservation/demande/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/reservation/historique/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/reservation/invite/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/reservation/liste/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/reservation/modification/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/reservation/package/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/reservation/prereservation/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/reservation/promotion/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/reservation/sejour/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/reservation/service/`

### Niveau 3 - Model Restauration
- `src/main/java/projet_hotelier/hotel/module/clientele/model/restauration/allergie/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/restauration/commande/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/restauration/menu/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/restauration/plat/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/restauration/preference/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/restauration/regime/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/restauration/reservation/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/restauration/roomservice/`

### Niveau 3 - Model Revenue
- `src/main/java/projet_hotelier/hotel/module/clientele/model/revenue/alerte/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/revenue/analyse/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/revenue/forecast/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/revenue/historique/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/revenue/kpi/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/revenue/optimisation/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/revenue/reporting/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/revenue/strategie/`

### Niveau 3 - Model RGPD
- `src/main/java/projet_hotelier/hotel/module/clientele/model/rgpd/acces/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/rgpd/audit/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/rgpd/consentement/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/rgpd/historique/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/rgpd/portabilite/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/rgpd/suppression/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/rgpd/traitement/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/rgpd/violation/`

### Niveau 3 - Model Satisfaction
- `src/main/java/projet_hotelier/hotel/module/clientele/model/satisfaction/action/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/satisfaction/benchmark/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/satisfaction/enquete/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/satisfaction/escalade/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/satisfaction/feedback/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/satisfaction/historique/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/satisfaction/nps/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/satisfaction/question/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/satisfaction/reclamation/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/satisfaction/reponse/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/satisfaction/resolution/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/satisfaction/review/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/satisfaction/sentiment/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/satisfaction/suggestion/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/satisfaction/theme/`

### Niveau 3 - Model Service
- `src/main/java/projet_hotelier/hotel/module/clientele/model/service/blanchisserie/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/service/conciergerie/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/service/demande/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/service/historique/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/service/reservation/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/service/spa/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/service/tarif/`
- `src/main/java/projet_hotelier/hotel/module/clientele/model/service/type/`

---

## 📊 STATISTIQUES

- **Total de dossiers** : ~250+ dossiers
- **Niveau de profondeur maximum** : 4 niveaux
- **Dossiers principaux (niveau 1)** : 18
- **Dossiers model (niveau 2)** : 21
- **Sous-dossiers model (niveau 3)** : ~200+

---

## 🎯 ORGANISATION PAR DOMAINE

### Gestion Client
- `model/client/` (7 sous-dossiers)
- `model/interaction/`
- `model/communication/` (13 sous-dossiers)

### Réservations & Séjours
- `model/reservation/` (15 sous-dossiers)
- `model/chambre/` (16 sous-dossiers)

### Facturation & Paiement
- `model/facturation/` (15 sous-dossiers)

### Marketing & Fidélité
- `model/marketing/` (14 sous-dossiers)
- `model/fidelite/`
- `model/campagne/`

### Satisfaction & Avis
- `model/satisfaction/` (15 sous-dossiers)
- `model/avis/`

### Contrats & Événements
- `model/contrat/` (16 sous-dossiers)

### Services Additionnels
- `model/service/` (8 sous-dossiers)
- `model/restauration/` (8 sous-dossiers)

### Analytics & Revenue
- `model/analytics/` (8 sous-dossiers)
- `model/revenue/` (8 sous-dossiers)

### Intégrations
- `model/integration/` (8 sous-dossiers)
- `model/channel/` (7 sous-dossiers)

### Conformité
- `model/rgpd/` (8 sous-dossiers)

### Multi-propriété
- `model/multipropriete/` (8 sous-dossiers)

---

**Rapport généré automatiquement le 2026-02-06**
