package us.lviv.javaclub.twodatabase.user.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "db.slave")
public class DatabaseSlaveProperties {
  private String url;
  private String username;
  private String password;
}
