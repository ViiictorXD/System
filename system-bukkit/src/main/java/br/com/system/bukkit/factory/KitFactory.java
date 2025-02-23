package br.com.system.bukkit.factory;

import br.com.system.core.model.kit.Kit;
import org.bukkit.inventory.ItemStack;

public class KitFactory {

  public Kit createKit(String name, String permission, ItemStack[] itemStacks, long delay, int maxPickups) {
    return new Kit(name, permission, itemStacks, delay, maxPickups);
  }
}
