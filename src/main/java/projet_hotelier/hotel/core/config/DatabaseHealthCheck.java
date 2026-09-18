package projet_hotelier.hotel.core.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

/**
 * Health check de la base de données au démarrage.
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "app.database.healthcheck.enabled", havingValue = "true", matchIfMissing = true)
public class DatabaseHealthCheck {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void checkDatabaseConnection() {
        log.info("🔍 Vérification de la connexion PostgreSQL...");
        
        try (Connection connection = dataSource.getConnection()) {
            if (!connection.isValid(5)) {
                log.warn("⚠️  Connexion PostgreSQL invalide - l'application peut continuer mais certaines fonctionnalités peuvent ne pas fonctionner");
                return;
            }
            
            DatabaseMetaData metaData = connection.getMetaData();
            String dbName = connection.getCatalog();
            String dbProduct = metaData.getDatabaseProductName();
            String dbVersion = metaData.getDatabaseProductVersion();
            
            log.info("✅ Connexion PostgreSQL établie:");
            log.info("   - Base de données: {}", dbName);
            log.info("   - Produit: {}", dbProduct);
            log.info("   - Version: {}", dbVersion);
            
        } catch (Exception e) {
            log.error("❌ Échec de la connexion PostgreSQL - l'application peut continuer mais la base de données ne sera pas accessible", e);
            log.error("   Vérifiez que PostgreSQL est démarré: sudo systemctl start postgresql");
            log.error("   Vérifiez que la base de données existe: CREATE DATABASE hotel_db;");
            // Ne pas bloquer le démarrage - permettre à Hibernate de s'initialiser avec le dialecte explicite
        }
    }
}
