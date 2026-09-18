# DOSSIERS À SUPPRIMER - MODULE CLIENTÈLE
═══════════════════════════════════════════════════════

**Date** : 2026-02-06  
**Module** : `src/main/java/projet_hotelier/hotel/module/clientele/`

---

## 📋 RÉSUMÉ

**Total de dossiers à supprimer** : 11 dossiers  
**Raison** : Ces dossiers contiennent uniquement des fichiers `package-info.java` et leurs fonctionnalités sont gérées au niveau global du projet ou ne sont pas nécessaires pour l'instant.

---

## ❌ LISTE EXACTE DES DOSSIERS À SUPPRIMER

### 1. Configuration
**Chemin complet** : `src/main/java/projet_hotelier/hotel/module/clientele/config/`  
**Raison** : Configuration déjà gérée au niveau principal du projet  
**Contenu** : Uniquement `package-info.java`

### 2. Controller Web
**Chemin complet** : `src/main/java/projet_hotelier/hotel/module/clientele/controller/web/`  
**Raison** : Pas d'UI web pour l'instant  
**Contenu** : Uniquement `package-info.java`

### 3. Événements
**Chemin complet** : `src/main/java/projet_hotelier/hotel/module/clientele/event/`  
**Raison** : Pas d'événements pour l'instant  
**Contenu** : Uniquement `package-info.java`

### 4. Internationalisation
**Chemin complet** : `src/main/java/projet_hotelier/hotel/module/clientele/i18n/`  
**Raison** : i18n géré au niveau global  
**Contenu** : Uniquement `package-info.java`

### 5. Listeners
**Chemin complet** : `src/main/java/projet_hotelier/hotel/module/clientele/listener/`  
**Raison** : Pas de listeners pour l'instant  
**Contenu** : Uniquement `package-info.java`

### 6. Sécurité
**Chemin complet** : `src/main/java/projet_hotelier/hotel/module/clientele/security/`  
**Raison** : Sécurité gérée au niveau global  
**Contenu** : Uniquement `package-info.java`

### 7. Tenant
**Chemin complet** : `src/main/java/projet_hotelier/hotel/module/clientele/tenant/`  
**Raison** : Tenant géré au niveau global  
**Contenu** : Uniquement `package-info.java`

### 8. Validation
**Chemin complet** : `src/main/java/projet_hotelier/hotel/module/clientele/validation/`  
**Raison** : Validations dans les DTOs  
**Contenu** : Uniquement `package-info.java`

### 9. Automation
**Chemin complet** : `src/main/java/projet_hotelier/hotel/module/clientele/automation/`  
**Raison** : Services d'automatisation dans `service/`  
**Contenu** : Uniquement `package-info.java`

### 10. Scheduler
**Chemin complet** : `src/main/java/projet_hotelier/hotel/module/clientele/scheduler/`  
**Raison** : Schedulers gérés au niveau global  
**Contenu** : Uniquement `package-info.java`

### 11. Notification (dossier principal)
**Chemin complet** : `src/main/java/projet_hotelier/hotel/module/clientele/notification/`  
**Raison** : Doublon - les entités sont dans `model/notification/`, les DTOs dans `dto/`, etc.  
**Contenu** : Uniquement `package-info.java`  
**Note** : ⚠️ **CONSERVER** les sous-dossiers suivants qui contiennent des fichiers réels :
- ✅ `model/notification/` (contient NotificationModel.java, NotificationTemplateModel.java)
- ✅ `dto/request/notification/`
- ✅ `dto/response/notification/`
- ✅ `mapper/notification/`
- ✅ `repository/notification/`

---

## ✅ DOSSIERS À CONSERVER

### Dossiers essentiels (niveau 1)
- ✅ `model/` - Contient toutes les entités
- ✅ `repository/` - Contient les repositories
- ✅ `service/` - Contient les services
- ✅ `service/impl/` - Contient les implémentations
- ✅ `controller/` - Contient les controllers
- ✅ `controller/api/` - Contient les API REST
- ✅ `dto/` - Contient les DTOs
- ✅ `mapper/` - Contient les mappers
- ✅ `exception/` - Contient les exceptions custom
- ✅ `enumeration/` - Contient les énumérations

### Sous-dossiers à conserver (notifications)
- ✅ `model/notification/` - Entités de notification
- ✅ `dto/request/notification/` - DTOs de requête
- ✅ `dto/response/notification/` - DTOs de réponse
- ✅ `mapper/notification/` - Mappers de notification
- ✅ `repository/notification/` - Repositories de notification

---

## 📝 COMMANDES DE SUPPRESSION

### Commande unique pour supprimer tous les dossiers
```bash
cd /home/camara/Videos/hotel

# Supprimer les dossiers inutiles
rm -rf src/main/java/projet_hotelier/hotel/module/clientele/config
rm -rf src/main/java/projet_hotelier/hotel/module/clientele/controller/web
rm -rf src/main/java/projet_hotelier/hotel/module/clientele/event
rm -rf src/main/java/projet_hotelier/hotel/module/clientele/i18n
rm -rf src/main/java/projet_hotelier/hotel/module/clientele/listener
rm -rf src/main/java/projet_hotelier/hotel/module/clientele/security
rm -rf src/main/java/projet_hotelier/hotel/module/clientele/tenant
rm -rf src/main/java/projet_hotelier/hotel/module/clientele/validation
rm -rf src/main/java/projet_hotelier/hotel/module/clientele/automation
rm -rf src/main/java/projet_hotelier/hotel/module/clientele/scheduler
rm -rf src/main/java/projet_hotelier/hotel/module/clientele/notification
```

### Commande avec vérification
```bash
cd /home/camara/Videos/hotel

# Vérifier avant suppression
for dir in config controller/web event i18n listener security tenant validation automation scheduler notification; do
  echo "=== Vérification: $dir ==="
  find src/main/java/projet_hotelier/hotel/module/clientele/$dir -type f ! -name "package-info.java" 2>/dev/null
done

# Si aucun fichier réel trouvé, supprimer
rm -rf src/main/java/projet_hotelier/hotel/module/clientele/{config,event,i18n,listener,security,tenant,validation,automation,scheduler,notification}
rm -rf src/main/java/projet_hotelier/hotel/module/clientele/controller/web
```

---

## 🔍 VÉRIFICATION POST-SUPPRESSION

Après suppression, vérifier que les dossiers suivants existent toujours :
```bash
# Dossiers essentiels
ls -d src/main/java/projet_hotelier/hotel/module/clientele/{model,repository,service,controller,dto,mapper,exception,enumeration}

# Sous-dossiers notification à conserver
ls -d src/main/java/projet_hotelier/hotel/module/clientele/model/notification
ls -d src/main/java/projet_hotelier/hotel/module/clientele/dto/*/notification
ls -d src/main/java/projet_hotelier/hotel/module/clientele/mapper/notification
ls -d src/main/java/projet_hotelier/hotel/module/clientele/repository/notification
```

---

## ⚠️ ATTENTION

1. **Ne pas supprimer** les sous-dossiers `notification` dans :
   - `model/notification/`
   - `dto/request/notification/`
   - `dto/response/notification/`
   - `mapper/notification/`
   - `repository/notification/`

2. **Vérifier les imports** après suppression pour s'assurer qu'aucun code ne référence ces packages supprimés.

3. **Backup recommandé** avant suppression :
   ```bash
   git add -A
   git commit -m "Backup avant suppression dossiers inutiles module clientele"
   ```

---

## 📊 IMPACT

- **Fichiers supprimés** : ~11 fichiers `package-info.java`
- **Dossiers supprimés** : 11 dossiers
- **Impact sur le code** : Aucun (seulement des fichiers package-info.java vides)
- **Gain** : Structure plus claire et alignée avec l'architecture du projet

---

**Rapport généré le 2026-02-06**
