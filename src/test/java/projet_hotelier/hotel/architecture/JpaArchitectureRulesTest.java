package projet_hotelier.hotel.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideOutsideOfPackage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

/**
 * Règles d'architecture pour prévenir les erreurs JPA/Hibernate.
 * Ces règles sont exécutées à chaque build pour garantir la cohérence.
 */
@AnalyzeClasses(packages = "projet_hotelier.hotel")
public class JpaArchitectureRulesTest {

    /**
     * Règle 1: Toutes les entités doivent étendre BaseEntity
     */
    @ArchTest
    static final ArchRule entitiesMustExtendBaseEntity = classes()
            .that().areAnnotatedWith(Entity.class)
            .should().beAssignableTo(projet_hotelier.hotel.core.common.BaseEntity.class)
            .because("Toutes les entités doivent hériter de BaseEntity pour la cohérence multi-tenant");

    /**
     * Règle 2: Pas de relation JPA vers des DTOs
     */
    @ArchTest
    static final ArchRule noJpaRelationsToDtos = fields()
            .that().areAnnotatedWith(OneToOne.class)
            .or().areAnnotatedWith(OneToMany.class)
            .or().areAnnotatedWith(ManyToOne.class)
            .or().areAnnotatedWith(ManyToMany.class)
            .should().haveRawType(resideOutsideOfPackage("..dto.."))
            .because("Les relations JPA ne doivent jamais pointer vers des DTOs");

    /**
     * Règle 3: Pas de relation JPA vers des services
     */
    @ArchTest
    static final ArchRule noJpaRelationsToServices = fields()
            .that().areAnnotatedWith(OneToOne.class)
            .or().areAnnotatedWith(OneToMany.class)
            .or().areAnnotatedWith(ManyToOne.class)
            .or().areAnnotatedWith(ManyToMany.class)
            .should().haveRawType(resideOutsideOfPackage("..service.."))
            .because("Les relations JPA ne doivent jamais pointer vers des services");

    /**
     * Règle 4: Les relations JPA doivent être privées ou protégées
     */
    @ArchTest
    static final ArchRule relationsShouldBeProperlyAnnotated = fields()
            .that().areAnnotatedWith(OneToOne.class)
            .or().areAnnotatedWith(OneToMany.class)
            .or().areAnnotatedWith(ManyToOne.class)
            .or().areAnnotatedWith(ManyToMany.class)
            .should().bePrivate()
            .orShould().beProtected()
            .because("Les relations JPA doivent être privées ou protégées");
}
