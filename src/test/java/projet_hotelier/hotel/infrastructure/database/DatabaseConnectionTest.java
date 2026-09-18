package projet_hotelier.hotel.infrastructure.database;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test de connexion à la base de données PostgreSQL.
 */
@SpringBootTest
@ActiveProfiles("test")
public class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void shouldConnectToPostgreSQL() throws SQLException {
        assertThat(dataSource)
                .as("DataSource doit être configuré")
                .isNotNull();
        
        try (Connection connection = dataSource.getConnection()) {
            assertThat(connection)
                    .as("Connexion à PostgreSQL doit être établie")
                    .isNotNull();
            
            assertThat(connection.isValid(5))
                    .as("Connexion doit être valide")
                    .isTrue();
            
            DatabaseMetaData metaData = connection.getMetaData();
            assertThat(metaData.getDatabaseProductName())
                    .as("Base de données doit être PostgreSQL")
                    .containsIgnoringCase("PostgreSQL");
        }
    }

    @Test
    void shouldHaveCorrectDatabaseName() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String catalog = connection.getCatalog();
            assertThat(catalog)
                    .as("Nom de la base de données doit être défini")
                    .isNotNull();
        }
    }
}
