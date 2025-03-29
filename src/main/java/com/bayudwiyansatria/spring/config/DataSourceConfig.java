package com.bayudwiyansatria.spring.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * DataSourceConfig
 * <p>
 * Configuration class for DataSource. Enables JPA repositories and transaction management. Provides
 * a DataSource bean for the development profile.
 *
 * <p>This class is annotated with {@code @Configuration} to indicate that it is a Spring
 * configuration class. It is also annotated with {@code @EnableJpaRepositories} to enable JPA
 * repositories and {@code @EnableTransactionManagement} to enable transaction management.</p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
@EnableJpaRepositories(
    basePackages = {
        "com.bayudwiyansatria.spring.repository.jpa"
    }
)
@EnableTransactionManagement
public class DataSourceConfig {

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    /**
     * Creates and configures a DataSource bean. This bean is only active in the development
     * profile.
     *
     * @return configured DataSource
     * @since 0.0.1
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
