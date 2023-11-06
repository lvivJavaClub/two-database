package us.lviv.javaclub.twodatabase.twodatabase.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseDatabaseProperties {
  private String url;

  private String username;

  private String password;
}
