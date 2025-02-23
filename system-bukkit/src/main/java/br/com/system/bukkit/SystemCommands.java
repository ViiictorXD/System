package br.com.system.bukkit;

import br.com.system.core.command.CommandInfo;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class SystemCommands {

  private final Map<String, CommandInfo> commandInfoMap = new HashMap<>();

  public CommandInfo getInfo(String key) {
    return commandInfoMap.get(key);
  }

  public void addInfo(String key, CommandInfo info) {
    commandInfoMap.put(key, info);
  }

  public void removeInfo(String key) {
    commandInfoMap.remove(key);
  }

  public List<CommandInfo> all() {
    return new ArrayList<>(commandInfoMap.values());
  }

  public void resolve(SystemPlugin plugin) {
    File file = new File(plugin.getDataFolder(), "commands.yml");

    FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

    ConfigurationSection section = configuration.getConfigurationSection("commands");
    if (section == null) {
      plugin.getLogger().warning("Nenhum comando foi carregado. Verifique se a seção 'commands' existe em 'System/commands.yml'");
      return;
    }

    for (String key : section.getKeys(false)) {
      addInfo(key, CommandInfo.builder()
       .key(section.getString(key + ".key"))
       .name(section.getString(key + ".key").toLowerCase())
       .description(section.getString(key + ".description", "No description."))
       .usageMessage(section.getString(key + ".usage", "No usage"))
       .alias(section.getStringList(key + ".alias"))
       .permission(section.getString(key + ".permission-setting.permission", "system.commands." + key))
       .permissionMessage(SystemUtil.withColor(section.getString(key + ".permission-setting.message", "&cSem permissão.")))
       .enabled(section.getBoolean(key + ".enabled", true))
       .build());
    }
  }
}
