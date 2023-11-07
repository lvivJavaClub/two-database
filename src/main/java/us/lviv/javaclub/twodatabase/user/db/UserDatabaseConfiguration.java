package us.lviv.javaclub.twodatabase.user.db;

import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel;
import net.ttddyy.dsproxy.listener.logging.SLF4JQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;

import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties({DatabaseMasterProperties.class, DatabaseSlaveProperties.class})
public class UserDatabaseConfiguration {

  @Bean
  @FlywayDataSource
  public DataSource readWriteConfiguration(@NonNull final DatabaseMasterProperties databaseMasterProperties) {
    return DataSourceBuilder.create()
        .url(databaseMasterProperties.getUrl())
        .username(databaseMasterProperties.getUsername())
        .password(databaseMasterProperties.getPassword())
        .build();
  }

  @Bean
  public DataSource readOnlyConfiguration(@NonNull final DatabaseSlaveProperties databaseSlaveProperties) {
    return DataSourceBuilder.create()
        .url(databaseSlaveProperties.getUrl())
        .username(databaseSlaveProperties.getUsername())
        .password(databaseSlaveProperties.getPassword())
        .build();
  }

  @Bean
  @Primary
  public DataSource routingDataSource(@NonNull final DatabaseMasterProperties databaseMasterProperties,
                                      @NonNull final DatabaseSlaveProperties databaseSlaveProperties) {
    return new TransactionRoutingDataSource(
        loggingProxy("ReadWrite", readWriteConfiguration(databaseMasterProperties)),
        loggingProxy("ReadOnly", readOnlyConfiguration(databaseSlaveProperties)));
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
