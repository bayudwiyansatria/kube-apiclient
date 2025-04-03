package com.bayudwiyansatria.spring.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration class for setting up the DataSource, enabling JPA repositories, and transaction
 * management.
 *
 * <p>
 * This class is responsible for configuring the DataSource and making it available as a bean within
 * the Spring application context. It also enables the use of JPA repositories and transaction
 * management for the application. The configuration values for the DataSource are injected from the
 * application's property files (typically {@code application.properties} or
 * {@code application.yml}).
 * </p>
 *
 * <p>
 * The class is annotated with:
 * </p>
 * <ul>
 *   <li>{@link Configuration} to denote that this is a Spring configuration class.</li>
 *   <li>{@link EnableJpaRepositories} to enable JPA repositories in the application, pointing to the
 *       {@code com.bayudwiyansatria.spring.repository.jpa} package.</li>
 *   <li>{@link EnableTransactionManagement} to enable Spring's transaction management capabilities.</li>
 * </ul>
 *
 * <h2>Key Configuration Details:</h2>
 * <ul>
 *   <li>Database connection details such as {@code driverClassName}, {@code url}, {@code username}, and
 *       {@code password} are injected via Spring's {@code @Value} annotation from the application properties.</li>
 *   <li>JPA repositories are enabled within the specified package {@code com.bayudwiyansatria.spring.repository.jpa}.</li>
 *   <li>Transaction management is enabled to ensure atomic operations in the application's persistence layer.</li>
 * </ul>
 *
 * <p>
 * This class is designed to work in a development environment but can be extended for other profiles.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 1.0.0
 */
@Configuration
@EnableJpaRepositories(
    basePackages = {
        "com.bayudwiyansatria.spring.repository.jpa"
    }
)
@EnableTransactionManagement
public class DataSourceConfig {

    /**
     * The driver class name for the DataSource, injected from the application properties. This
     * specifies the fully qualified class name of the database JDBC driver.
     */
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    /**
     * The URL for the DataSource, injected from the application properties. This URL defines the
     * database server's location and the database to which the application connects.
     */
    @Value("${spring.datasource.url}")
    private String url;

    /**
     * The username for the DataSource, injected from the application properties. This is the
     * username that will be used for authentication when connecting to the database.
     */
    @Value("${spring.datasource.username}")
    private String username;

    /**
     * The password for the DataSource, injected from the application properties. This password
     * corresponds to the username for authentication to the database.
     */
    @Value("${spring.datasource.password}")
    private String password;

    /**
     * Creates and configures the {@link DataSource} bean for database connectivity.
     *
     * <p>
     * This method uses the {@link DriverManagerDataSource} to create a connection to the database
     * using the configured {@code driverClassName}, {@code url}, {@code username}, and
     * {@code password}.
     * </p>
     *
     * <p>
     * The {@code DataSource} bean is crucial for enabling Spring to interact with the database. It
     * provides the connection pooling and database interactions needed by the JPA repositories.
     * </p>
     *
     * @return The configured {@link DataSource} bean.
     * @see DriverManagerDataSource
     * @since 1.0.0
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(this.driverClassName);
        dataSource.setUrl(this.url);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);

        return dataSource;
    }
}
