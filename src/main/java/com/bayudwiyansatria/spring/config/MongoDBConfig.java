package com.bayudwiyansatria.spring.config;

import com.bayudwiyansatria.spring.exception.MongoConfigurationException;
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
 * <p>
 * Configuration class for MongoDB. Enables MongoDB repositories and provides MongoTemplate bean.
 * Configures MongoDB client settings based on application properties.
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration()
@EnableMongoRepositories(
    basePackages = {
        "com.bayudwiyansatria.spring.repository.mongodb"
    }
)
public class MongoDBConfig extends AbstractMongoClientConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MongoDBConfig.class);

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private String port;

    @Value("${spring.data.mongodb.username}")
    private String username;

    @Value("${spring.data.mongodb.password}")
    private String password;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${spring.data.mongodb.authentication-database}")
    private String authDatabase;

    /**
     * Configures the settings for the MongoDB client.
     *
     * @return MongoClientSettings
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
     * Creates and configures a MongoClient instance.
     *
     * @return MongoClient
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
     * Creates and configures a MongoTemplate instance.
     *
     * @return MongoTemplate
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
     * Returns the MongoDB database name.
     *
     * @return String
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
     * Constructs a ConnectionString object that encapsulates the MongoDB connection details.
     *
     * @return ConnectionString
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
