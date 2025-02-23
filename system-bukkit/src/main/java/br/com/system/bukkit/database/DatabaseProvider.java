package br.com.system.bukkit.database;

public interface DatabaseProvider<C> {

  C getConnection();

  void openConnection();

  void closeConnection();

  DatabaseType getType();
}
