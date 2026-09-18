package projet_hotelier.hotel.core.config;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.metamodel.EntityType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Configuration de validation JPA au démarrage.
 * Vérifie que toutes les entités sont correctement configurées.
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "app.jpa.validation.enabled", havingValue = "true", matchIfMissing = true)
public class JpaValidationConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void validateJpaEntities() {
        log.info("🔍 Validation des entités JPA...");
        
        try {
            Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
            log.info("✅ {} entités JPA chargées", entities.size());
            
            for (EntityType<?> entityType : entities) {
                Class<?> javaType = entityType.getJavaType();
                
                if (!javaType.isAnnotationPresent(Entity.class)) {
                    log.warn("⚠️  Classe {} dans le metamodel mais pas annotée @Entity", javaType.getName());
                } else {
                    log.debug("✅ Entité valide: {}", javaType.getSimpleName());
                }
            }
            
            log.info("✅ Validation JPA terminée avec succès");
        } catch (Exception e) {
            log.warn("⚠️  Validation JPA non disponible (connexion DB peut-être indisponible): {}", e.getMessage());
            log.debug("Détails de l'erreur:", e);
            // Ne pas bloquer le démarrage - la validation peut échouer si la DB n'est pas disponible
        }
    }
}
