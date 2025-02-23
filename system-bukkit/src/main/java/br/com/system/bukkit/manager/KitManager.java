package br.com.system.bukkit.manager;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.core.manager.AbstractManager;
import br.com.system.core.serializer.ItemStackSerializer;
import br.com.system.core.model.kit.Kit;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class KitManager extends AbstractManager<Kit> {

  public Kit getKitByName(String name) {
    return optional(kit -> kit.getName().equalsIgnoreCase(name))
     .orElse(null);
  }

  public void init(SystemPlugin plugin) {
    File[] files = new File(plugin.getDataFolder(), "kits").listFiles();

    if (files == null || files.length == 0) {
      plugin.getLogger().warning("Nenhum kit foi carregado.");
      return;
    }

    for (File file : files) {
      if (!file.isFile() || !file.getName().endsWith(".yml")) continue;

      addEntity(load(file));
    }
    plugin.getLogger().info(size() + " kits carregados com sucesso.");
  }

  @SneakyThrows
  public void save(Kit kit, File file) {
    YamlConfiguration yamlConfiguration = new YamlConfiguration();

    yamlConfiguration.set("name", kit.getName());
    yamlConfiguration.set("permission", kit.getPermission());

    yamlConfiguration.set("itemStacks", ItemStackSerializer.serialize(kit.getItemStacks()));

    yamlConfiguration.set("delay", kit.getDelay());

    yamlConfiguration.set("max-pickups", kit.getMaxPickups());

    yamlConfiguration.save(file);
  }

  public Kit load(File file) {
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    String name = yamlConfiguration.getString("name");
    String permission = yamlConfiguration.getString("permission");

    ItemStack[] itemStacks = ItemStackSerializer.deserialize(yamlConfiguration.getString("itemStacks"));

    long delay = yamlConfiguration.getLong("delay");

    int maxPickups = yamlConfiguration.getInt("max-pickups");

    return new Kit(name, permission, itemStacks, delay, maxPickups);
  }
}
