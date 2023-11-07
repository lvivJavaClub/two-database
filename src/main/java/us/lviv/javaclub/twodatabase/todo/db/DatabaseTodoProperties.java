package us.lviv.javaclub.twodatabase.todo.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "db.todo")
public class DatabaseTodoProperties {
  private String url;
  private String username;
  private String password;
}
