package br.com.system.bukkit.database.sql.remote;

import br.com.system.bukkit.database.DatabaseType;
import br.com.system.bukkit.database.sql.SqlDatabaseProperty;
import br.com.system.bukkit.database.sql.SqlDatabaseProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

@RequiredArgsConstructor
public class MysqlDatabaseProvider implements SqlDatabaseProvider {

  private final SqlDatabaseProperty property;
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
    Class.forName("com.mysql.jdbc.Driver");

    connection = DriverManager.getConnection(String.format(
     "jdbc:mysql://%s/%s", property.getProperty("host"), property.getProperty("database")
    ), property.getProperty("username"), property.getProperty("password"));
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
    return DatabaseType.MYSQL;
  }
}
