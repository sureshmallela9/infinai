package com.infinai.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
    basePackages = {"com.infinai.hospital"},
    entityManagerFactoryRef = "hospitalEntityManager",
    transactionManagerRef = "hospitalTransactionManager")
public class HospitalDBConfig extends BaseConfig{
  @Bean
  public JpaProperties hospitalJpaProperties() {
    return getJpaProperties();
  }

  @Bean
  @ConfigurationProperties("datasources.hospital")
  public DataSourceProperties hospitalDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean hospitalEntityManager(
      final JpaProperties hospitalJpaProperties) {
    EntityManagerFactoryBuilder builder = createEntityManagerFactory(hospitalJpaProperties);
    return builder.dataSource(hospitalDataSource()).packages("com.infinai.hospital")
        .persistenceUnit("hospitalDataSource").build();
  }

  @Bean
  @ConfigurationProperties(prefix = "datasources.hospital.configurations")
  public DataSource hospitalDataSource() {
    DataSource dataSource = hospitalDataSourceProperties()
        .initializeDataSourceBuilder()
        .type(BasicDataSource.class).build();
    return dataSource;
  }

  @Bean
  public JpaTransactionManager hospitalTransactionManager(final JpaProperties hospitalJpaProperties) {
    return new JpaTransactionManager(hospitalEntityManager(hospitalJpaProperties).getObject());
  }
}
