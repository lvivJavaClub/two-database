package us.lviv.javaclub.twodatabase.todo.db;

import us.lviv.javaclub.twodatabase.todo.Todo;

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
    basePackageClasses = Todo.class,
    entityManagerFactoryRef = "todosEntityManagerFactory",
    transactionManagerRef = "todosTransactionManager"
)
public class TodoJpaConfiguration {

  @Bean
  public PlatformTransactionManager todosTransactionManager(
      @NonNull @Qualifier("todosEntityManagerFactory") final LocalContainerEntityManagerFactoryBean todosEntityManagerFactory) {
    return new JpaTransactionManager(Objects.requireNonNull(todosEntityManagerFactory.getObject()));
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean todosEntityManagerFactory(
      @NonNull @Qualifier("todosDataSource") final DataSource dataSource,
      @NonNull final EntityManagerFactoryBuilder builder) {
    return builder
        .dataSource(dataSource)
        .packages(Todo.class)
        .build();
  }
}
