package us.lviv.javaclub.twodatabase.twodatabase.db;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;
import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel;
import net.ttddyy.dsproxy.listener.logging.SLF4JQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import us.lviv.javaclub.twodatabase.twodatabase.ReplicaAwareTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

  @Value("${db.master.url}")
  private String masterUrl;

  @Value("${db.master.username}")
  private String masterUsername;

  @Value("${db.master.password}")
  private String masterPassword;

  @Bean
  @FlywayDataSource
  public DataSource readWriteConfiguration() {
    return DataSourceBuilder.create()
        .url(masterUrl)
        .username(masterUsername)
        .password(masterPassword)
        .build();
  }

  @Value("${db.slave.url}")
  private String slaveUrl;

  @Value("${db.slave.username}")
  private String slaveUsername;

  @Value("${db.slave.password}")
  private String slavePassword;

  @Bean
  public DataSource readOnlyConfiguration() {
    return DataSourceBuilder.create()
        .url(slaveUrl)
        .username(slaveUsername)
        .password(slavePassword)
        .build();
  }

  @Bean
  @Primary
  public DataSource routingDataSource() {
    return new TransactionRoutingDataSource(
        loggingProxy("readWrite", readWriteConfiguration()),
        loggingProxy("readOnly", readOnlyConfiguration()));
  }

  @Bean
  @Primary
  public PlatformTransactionManager transactionManager(@Qualifier("jpaTxManager") PlatformTransactionManager wrapped) {
    return new ReplicaAwareTransactionManager(wrapped);
  }

  @Bean(name = "jpaTxManager")
  public PlatformTransactionManager jpaTransactionManager(EntityManagerFactory emf) {
    return new JpaTransactionManager(emf);
  }
  
  private DataSource loggingProxy(String name, DataSource dataSource) {
    final SLF4JQueryLoggingListener loggingListener = new SLF4JQueryLoggingListener();
    loggingListener.setLogLevel(SLF4JLogLevel.INFO);
    loggingListener.setLogger(name);
    loggingListener.setWriteConnectionId(false);
    return ProxyDataSourceBuilder
        .create(dataSource)
        .name(name)
        .listener(loggingListener)
        .build();
  }
}
