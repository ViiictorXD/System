package br.com.system.bukkit.database.sql.local;

import br.com.system.bukkit.database.DatabaseType;
import br.com.system.bukkit.database.sql.SqlDatabaseProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

@RequiredArgsConstructor
public class SqliteDatabaseProvider implements SqlDatabaseProvider {

  private final File file;
  private final String name;

  private Connection connection;

  @SneakyThrows
  @Override
  public Connection getConnection() {
    if (connection == null || connection.isClosed())
      openConnection();

    return connection;
  }

  @SneakyThrows
  @Override
  public void openConnection() {
    Class.forName("org.sqlite.JDBC");

    connection = DriverManager.getConnection(String.format(
     "jdbc:sqlite:%s/%s", file, name
    ));
  }

  @SneakyThrows
  @Override
  public void closeConnection() {
    if (connection == null || connection.isClosed())
      return;

    connection.close();
  }

  @Override
  public DatabaseType getType() {
    return DatabaseType.SQLITE;
  }
}
