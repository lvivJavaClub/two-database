package us.lviv.javaclub.twodatabase.todo.db;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties({DatabaseTodoProperties.class})
public class TodoDatasourceConfiguration {

  @Bean
  public DataSource todosDataSource(@NonNull final DatabaseTodoProperties databaseTodoProperties) {
    return DataSourceBuilder.create()
        .url(databaseTodoProperties.getUrl())
        .username(databaseTodoProperties.getUsername())
        .password(databaseTodoProperties.getPassword())
        .build();
  }
}
