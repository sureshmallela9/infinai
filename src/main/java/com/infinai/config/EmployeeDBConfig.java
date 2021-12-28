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
    basePackages = {"com.infinai.employee"},
    entityManagerFactoryRef = "productsEntityManager",
    transactionManagerRef = "productsTransactionManager")
public class EmployeeDBConfig extends BaseConfig {

  @Bean
  public JpaProperties employeeJpaProperties() {
    return getJpaProperties();
  }

  @Bean
  @ConfigurationProperties("datasources.employee")
  public DataSourceProperties employeeDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean employeeEntityManager(
      final JpaProperties employeeJpaProperties) {
    EntityManagerFactoryBuilder builder = createEntityManagerFactory(employeeJpaProperties);
    return builder.dataSource(employeeDataSource()).packages("com.infinai.employee")
        .persistenceUnit("employeeDataSource").build();
  }

  @Bean
  @ConfigurationProperties(prefix = "datasources.employee.configurations")
  public DataSource employeeDataSource() {
    DataSource dataSource = employeeDataSourceProperties()
        .initializeDataSourceBuilder()
        .type(BasicDataSource.class).build();
    return dataSource;
  }

  @Bean
  public JpaTransactionManager employeeTransactionManager(final JpaProperties employeeJpaProperties) {
    return new JpaTransactionManager(employeeEntityManager(employeeJpaProperties).getObject());
  }
}
