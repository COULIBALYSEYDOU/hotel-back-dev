package projet_hotelier.hotel.infrastructure.jpa;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ManagedType;
import jakarta.persistence.metamodel.PluralAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test de validation des relations JPA.
 * Vérifie que toutes les relations pointent vers des @Entity ou @Embeddable valides.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class JpaRelationValidationTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void allRelationsShouldTargetValidTypes() {
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        
        for (EntityType<?> entityType : entities) {
            Class<?> javaType = entityType.getJavaType();
            
            // Vérifier les attributs singuliers (OneToOne, ManyToOne)
            for (SingularAttribute<?, ?> attr : entityType.getSingularAttributes()) {
                Class<?> targetType = attr.getJavaType();
                
                if (isJpaRelation(attr.getJavaMember())) {
                    validateRelationTarget(javaType, targetType, attr.getName());
                }
            }
            
            // Vérifier les attributs pluriels (OneToMany, ManyToMany)
            for (PluralAttribute<?, ?, ?> attr : entityType.getPluralAttributes()) {
                Class<?> targetType = attr.getElementType().getJavaType();
                
                if (isJpaRelation(attr.getJavaMember())) {
                    validateRelationTarget(javaType, targetType, attr.getName());
                }
            }
        }
    }

    private boolean isJpaRelation(java.lang.reflect.Member member) {
        if (!(member instanceof Field)) {
            return false;
        }
        
        Field field = (Field) member;
        return field.isAnnotationPresent(OneToOne.class) ||
               field.isAnnotationPresent(OneToMany.class) ||
               field.isAnnotationPresent(ManyToOne.class) ||
               field.isAnnotationPresent(ManyToMany.class);
    }

    private void validateRelationTarget(Class<?> sourceClass, Class<?> targetClass, String fieldName) {
        // Ignorer les types primitifs et wrappers
        if (targetClass.isPrimitive() || 
            targetClass.getName().startsWith("java.lang.") ||
            targetClass.getName().startsWith("java.util.") ||
            targetClass.isEnum()) {
            return;
        }
        
        boolean isEntity = targetClass.isAnnotationPresent(Entity.class);
        boolean isEmbeddable = targetClass.isAnnotationPresent(Embeddable.class);
        
        assertThat(isEntity || isEmbeddable)
                .as("La relation %s.%s vers %s doit pointer vers une @Entity ou @Embeddable",
                    sourceClass.getSimpleName(), fieldName, targetClass.getSimpleName())
                .isTrue();
    }
}
