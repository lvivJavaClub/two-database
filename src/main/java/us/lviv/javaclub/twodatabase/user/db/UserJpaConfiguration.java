package us.lviv.javaclub.twodatabase.user.db;

import us.lviv.javaclub.twodatabase.user.User;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.lang.NonNull;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Objects;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackageClasses = User.class,
    entityManagerFactoryRef = "usersEntityManagerFactory",
    transactionManagerRef = "usersTransactionManager"
)
public class UserJpaConfiguration {
  @Bean
  public PlatformTransactionManager usersTransactionManager(
      @NonNull @Qualifier("usersEntityManagerFactory") final LocalContainerEntityManagerFactoryBean usersEntityManagerFactory) {
    return new JpaTransactionManager(Objects.requireNonNull(usersEntityManagerFactory.getObject()));
  }

  @Bean
  @Primary
  public LocalContainerEntityManagerFactoryBean usersEntityManagerFactory(
      @NonNull @Qualifier("routingDataSource") final DataSource dataSource,
      @NonNull final EntityManagerFactoryBuilder builder) {
    return builder
        .dataSource(dataSource)
        .packages(User.class)
        .build();
  }
}
