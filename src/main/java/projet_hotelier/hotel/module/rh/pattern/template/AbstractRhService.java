package projet_hotelier.hotel.module.rh.pattern.template;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.core.common.BaseEntity;

import java.time.LocalDateTime;

/**
 * Template Method Pattern pour les services RH.
 * Definit le squelette d'un algorithme pour les operations CRUD communes.
 */
@Slf4j
public abstract class AbstractRhService<T extends BaseEntity, ID> {

    /**
     * Template method pour la creation d'une entite.
     */
    @Transactional
    public T create(T entity, Long organisationId, Long hotelId, String username) {
        log.debug("Creation d'une entite via template method");
        
        // Etape 1: Validation pre-creation
        validateBeforeCreate(entity);
        
        // Etape 2: Initialisation
        initializeEntity(entity, organisationId, hotelId, username);
        
        // Etape 3: Persistence
        T saved = saveEntity(entity);
        
        // Etape 4: Post-creation (events, notifications, etc.)
        afterCreate(saved);
        
        log.debug("Entite creee avec succes: id={}, uuid={}", saved.getId(), saved.getUuid());
        return saved;
    }

    /**
     * Template method pour la mise a jour d'une entite.
     */
    @Transactional
    public T update(ID id, T updatedEntity, Long organisationId, String username) {
        log.debug("Mise a jour d'une entite via template method");
        
        // Etape 1: Recuperation de l'entite existante
        T existing = findEntityById(id, organisationId);
        
        // Etape 2: Validation pre-update
        validateBeforeUpdate(existing, updatedEntity);
        
        // Etape 3: Mise a jour
        updateEntityFields(existing, updatedEntity);
        existing.setModifiePar(username);
        existing.setDateModification(LocalDateTime.now());
        
        // Etape 4: Persistence
        T saved = saveEntity(existing);
        
        // Etape 5: Post-update
        afterUpdate(saved);
        
        log.debug("Entite mise a jour avec succes: id={}, uuid={}", saved.getId(), saved.getUuid());
        return saved;
    }

    /**
     * Template method pour la suppression d'une entite.
     */
    @Transactional
    public void delete(ID id, Long organisationId, String username) {
        log.debug("Suppression d'une entite via template method");
        
        // Etape 1: Recuperation
        T entity = findEntityById(id, organisationId);
        
        // Etape 2: Validation pre-delete
        validateBeforeDelete(entity);
        
        // Etape 3: Soft delete
        entity.setSupprime(true);
        entity.setActif(false);
        entity.setModifiePar(username);
        entity.setDateModification(LocalDateTime.now());
        
        // Etape 4: Persistence
        saveEntity(entity);
        
        // Etape 5: Post-delete
        afterDelete(entity);
        
        log.debug("Entite supprimee avec succes: id={}", entity.getId());
    }

    // Methodes abstraites a implementer par les sous-classes
    protected abstract void validateBeforeCreate(T entity);
    protected abstract void validateBeforeUpdate(T existing, T updated);
    protected abstract void validateBeforeDelete(T entity);
    protected abstract void initializeEntity(T entity, Long organisationId, Long hotelId, String username);
    protected abstract void updateEntityFields(T existing, T updated);
    protected abstract T findEntityById(ID id, Long organisationId);
    protected abstract T saveEntity(T entity);

    // Hooks optionnels (peuvent etre overrides)
    protected void afterCreate(T entity) {
        // Par defaut, rien a faire
    }

    protected void afterUpdate(T entity) {
        // Par defaut, rien a faire
    }

    protected void afterDelete(T entity) {
        // Par defaut, rien a faire
    }
}
