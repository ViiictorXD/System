package br.com.system.bukkit.service;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.bukkit.database.DatabaseProvider;
import br.com.system.bukkit.database.DatabaseResolver;
import br.com.system.core.data.Service;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;

@RequiredArgsConstructor
public class DatabaseService implements Service {

  private final SystemPlugin plugin;

  @Getter
  private DatabaseProvider<Connection> databaseProvider;

  @Override
  public void start() {
    DatabaseResolver databaseResolver = new DatabaseResolver();

    databaseProvider = databaseResolver.createDatabaseProviderInstance(plugin);
    databaseProvider.openConnection();

    plugin.getLogger().info("Banco de dados conectado com sucesso. Usando: " + databaseProvider.getType().getName());
  }
}