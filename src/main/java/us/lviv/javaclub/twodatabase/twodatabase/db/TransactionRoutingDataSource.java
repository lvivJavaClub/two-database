package us.lviv.javaclub.twodatabase.twodatabase.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

public class TransactionRoutingDataSource extends AbstractRoutingDataSource {
  private static final ThreadLocal<DataSourceType> CURRENT_DATA_SOURCE = new ThreadLocal<>();

  public TransactionRoutingDataSource(@NonNull final DataSource master, @NonNull final DataSource slave) {
    final Map<Object, Object> dataSources = new HashMap<>();
    dataSources.put(DataSourceType.READ_WRITE, master);
    dataSources.put(DataSourceType.READ_ONLY, slave);

    super.setTargetDataSources(dataSources);
    super.setDefaultTargetDataSource(master);
  }

  static boolean isCurrentlyReadonly() {
    return CURRENT_DATA_SOURCE.get() == DataSourceType.READ_ONLY;
  }

  static void setReadonlyDataSource(@NonNull boolean isReadonly) {
    CURRENT_DATA_SOURCE.set(isReadonly ? DataSourceType.READ_ONLY : DataSourceType.READ_WRITE);
  }

  public static void unload() {
    CURRENT_DATA_SOURCE.remove();
  }
  
  @Override
  protected Object determineCurrentLookupKey() {
    return CURRENT_DATA_SOURCE.get();
  }

  private enum DataSourceType {
    READ_ONLY,
    READ_WRITE
  }
}
