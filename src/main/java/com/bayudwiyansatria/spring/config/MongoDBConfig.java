package com.bayudwiyansatria.spring.config;

import com.bayudwiyansatria.spring.exception.config.MongoConfigurationException;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * MongoDBConfig
 *
 * <p>
 * Configuration class responsible for setting up MongoDB integration with Spring Boot. It enables
 * MongoDB repositories and provides a configured {@link MongoTemplate} bean for interacting with
 * the MongoDB database. This class also handles MongoDB client configuration and authentication
 * settings, which are injected from the application properties.
 * </p>
 *
 * <p>
 * The configuration class uses the MongoDB URI format to configure MongoDB client settings and
 * handles errors that might occur during the connection or template creation by throwing
 * {@link MongoConfigurationException}.
 * </p>
 *
 * <h2>Key Components</h2>
 * <ul>
 *   <li>{@link MongoClient}: The MongoDB client used to establish a connection to the MongoDB server.</li>
 *   <li>{@link MongoTemplate}: A Spring Data MongoDB template used to interact with the MongoDB database.</li>
 *   <li>{@link ConnectionString}: A class that encapsulates the MongoDB connection details in URI format.</li>
 * </ul>
 *
 * <h2>Exceptions</h2>
 * <p>
 * This class may throw a {@link MongoConfigurationException} if there are issues while creating the MongoDB client,
 * MongoTemplate, or while constructing the MongoDB connection string.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 1.0.0
 */
@Configuration()
@EnableMongoRepositories(
    basePackages = {
        "com.bayudwiyansatria.spring.repository.mongodb"
    }
)
public class MongoDBConfig extends AbstractMongoClientConfiguration {

    /**
     * Logger for logging messages and errors related to MongoDB configuration.
     */
    private static final Logger logger = LoggerFactory.getLogger(MongoDBConfig.class);

    /**
     * Host name of the MongoDB server, injected from the application properties. This property
     * defines the host to which the MongoDB client will connect.
     */
    @Value("${spring.data.mongodb.host}")
    private String host;

    /**
     * Port number of the MongoDB server, injected from the application properties. This property
     * defines the port on which MongoDB is running.
     */
    @Value("${spring.data.mongodb.port}")
    private String port;

    /**
     * Username for MongoDB authentication, injected from the application properties. This property
     * provides the username for connecting to the MongoDB instance.
     */
    @Value("${spring.data.mongodb.username}")
    private String username;

    /**
     * Password for MongoDB authentication, injected from the application properties. This property
     * provides the password for connecting to the MongoDB instance.
     */
    @Value("${spring.data.mongodb.password}")
    private String password;

    /**
     * Name of the MongoDB database, injected from the application properties. This property
     * specifies which database to connect to within MongoDB.
     */
    @Value("${spring.data.mongodb.database}")
    private String database;

    /**
     * Name of the authentication database, injected from the application properties. This property
     * specifies which MongoDB database to authenticate against.
     */
    @Value("${spring.data.mongodb.authentication-database}")
    private String authDatabase;

    /**
     * Configures the MongoDB client settings, including connection details like host, port, and
     * authentication credentials. The method builds and returns an instance of
     * {@link MongoClientSettings} using the provided connection string.
     *
     * @return {@link MongoClientSettings} with the appropriate configuration for MongoDB
     * connection.
     * @see MongoClientSettings
     * @since 0.0.1
     */
    @NotNull
    @ConditionalOnProperty(name = "spring.data.mongodb.enabled", havingValue = "true")
    private MongoClientSettings mongoClientSetting() {
        return MongoClientSettings.builder()
            .applyConnectionString(this.buildConnectionString())
            .build();

    }

    /**
     * Creates and configures a {@link MongoClient} instance using the settings defined in
     * {@link MongoClientSettings}. The MongoClient is responsible for establishing a connection to
     * the MongoDB server.
     *
     * @return {@link MongoClient} instance connected to the MongoDB server.
     * @see MongoClient
     * @since 0.0.1
     */
    @Bean
    @NotNull
    public MongoClient mongoClient() {
        try {
            return MongoClients.create(this.mongoClientSetting());
        } catch (Exception e) {
            throw new MongoConfigurationException(
                "An unexpected error occurred while connecting to MongoDB.", e);
        }
    }

    /**
     * Creates and configures a {@link MongoTemplate} instance, which is used to perform operations
     * on the MongoDB database. It relies on a {@link SimpleMongoClientDatabaseFactory} to connect
     * to the MongoDB database and execute CRUD operations.
     *
     * @return {@link MongoTemplate} instance configured for MongoDB operations.
     * @see MongoTemplate
     * @see SimpleMongoClientDatabaseFactory
     * @since 0.0.1
     */
    @Bean
    public MongoTemplate mongoTemplate() {
        try {
            return new MongoTemplate(
                new SimpleMongoClientDatabaseFactory(this.mongoClient(), database));
        } catch (Exception e) {
            throw new MongoConfigurationException(
                "An unexpected error occurred while creating MongoDB template.", e);
        }
    }

    /**
     * Returns the name of the MongoDB database to be used by the application. This method is
     * overridden from {@link AbstractMongoClientConfiguration}.
     *
     * @return the name of the MongoDB database.
     * @since 0.0.1
     */
    @Override
    @NotNull
    protected String getDatabaseName() {
        try {
            return database;
        } catch (Exception e) {
            throw new MongoConfigurationException(
                "An unexpected error occurred while getting database name.", e);
        }
    }

    /**
     * Constructs a {@link ConnectionString} object using the MongoDB connection details (username,
     * password, host, port, etc.). This connection string is used to configure the
     * {@link MongoClient} to establish a connection with the MongoDB server.
     *
     * @return a {@link ConnectionString} object representing the MongoDB connection.
     * @see ConnectionString
     * @since 0.0.1
     */
    @NotNull
    private ConnectionString buildConnectionString() {
        try {
            return new ConnectionString(
                String.format(
                    "mongodb://%s:%s@%s:%s/%s?authSource=%s&ssl=true&retrywrites=false",
                    username, password, host, port, database, authDatabase
                )
            );
        } catch (Exception e) {
            throw new MongoConfigurationException(
                "An unexpected error occurred while building MongoDB connection string.", e);
        }
    }
}
