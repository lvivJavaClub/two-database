package us.lviv.javaclub.twodatabase.twodatabase.db;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "db.master")
public class DatabaseMasterProperties extends BaseDatabaseProperties {
}
