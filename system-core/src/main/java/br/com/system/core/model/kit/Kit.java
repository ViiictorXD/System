package br.com.system.core.model.kit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
@Builder
@AllArgsConstructor
public class Kit {

  private String name;
  private String permission;

  private ItemStack[] itemStacks;

  private long delay;

  private int maxPickups;

  public int getKitCount() {
    return itemStacks.length;
  }
}
