package projet_hotelier.hotel.infrastructure.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.metamodel.EntityType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test de validation des entités JPA.
 * Vérifie que toutes les entités sont correctement configurées.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class JpaEntityValidationTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void allEntitiesShouldBeAnnotated() {
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        
        assertThat(entities)
                .as("Toutes les entités doivent être chargées par Hibernate")
                .isNotEmpty();
        
        for (EntityType<?> entityType : entities) {
            Class<?> javaType = entityType.getJavaType();
            
            assertThat(javaType.isAnnotationPresent(Entity.class))
                    .as("La classe %s doit être annotée avec @Entity", javaType.getName())
                    .isTrue();
        }
    }

    @Test
    void entityManagerFactoryShouldBeInitialized() {
        assertThat(entityManager)
                .as("EntityManager doit être initialisé")
                .isNotNull();
        
        assertThat(entityManager.getMetamodel())
                .as("Metamodel doit être disponible")
                .isNotNull();
    }
}
