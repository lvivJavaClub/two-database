package us.lviv.javaclub.twodatabase.user.db;

import jakarta.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class PlatformTransactionManagerConfig {
  @Bean
  @Primary
  public PlatformTransactionManager transactionManager(@NonNull @Qualifier("jpaTxManager") final PlatformTransactionManager wrapped) {
    return new ReplicaAwareTransactionManager(wrapped);
  }

  @Bean(name = "jpaTxManager")
  public PlatformTransactionManager jpaTransactionManager(@NonNull final EntityManagerFactory emf) {
    return new JpaTransactionManager(emf);
  }
}
