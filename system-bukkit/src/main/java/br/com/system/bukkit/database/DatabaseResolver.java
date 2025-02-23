package br.com.system.bukkit.database;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.bukkit.database.sql.SqlDatabaseProperty;
import br.com.system.bukkit.database.sql.local.SqliteDatabaseProvider;
import br.com.system.bukkit.database.sql.remote.MysqlDatabaseProvider;

import java.sql.Connection;

public class DatabaseResolver {

  public DatabaseProvider<Connection> createDatabaseProviderInstance(SystemPlugin plugin) {
    DatabaseType type = DatabaseType.getType(plugin.getConfig().getString("database.type", "sqlite"));

    switch (type) {
      case SQLITE: return new SqliteDatabaseProvider(plugin.getDataFolder(), "system.db");
      case MYSQL: return new MysqlDatabaseProvider(SqlDatabaseProperty.of(plugin.getConfig()));
    }
    return null;
  }
}
