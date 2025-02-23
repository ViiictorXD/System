package br.com.system.bukkit;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Getter
public class SystemConfiguration {

  private final Map<String, FileConfiguration> configurationMap = new HashMap<>();

  public FileConfiguration getConfiguration(String id) {
    return configurationMap.get(id);
  }

  public boolean containsConfiguration(String id) {
    return configurationMap.containsKey(id);
  }

  public void register(String id, FileConfiguration configuration) {
    configurationMap.put(id, configuration);
  }

  public void unregister(String id) {
    configurationMap.remove(id);
  }

  public void resolve(SystemPlugin system) {
    File[] files = new File(system.getDataFolder(), "langs").listFiles();

    if (files == null || files.length == 0) {
      system.getLogger().warning("Nenhuma mensagem foi carregada. Verifique se o arquivo 'System/langs' existe.");
      return;
    }

    for (File file : files) {
      if (!file.isFile()) return;

      String id = file.getName().split("\\.")[0];
      FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

      register(id, configuration);
    }
  }
}
