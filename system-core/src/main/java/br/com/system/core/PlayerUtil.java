package br.com.system.core;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerUtil {

  public static int getPlayerInventorySpace(Player player) {
    PlayerInventory inventory = player.getInventory();

    int inventorySpace = 0;

    for (ItemStack itemStack : inventory.getContents()) {
      if (itemStack == null) {
        inventorySpace++;
        continue;
      }

      XMaterial material = XMaterial.matchXMaterial(itemStack);

      if (material.name().contains("AIR")) {
        inventorySpace++;
        continue;
      }
    }
    return inventorySpace;
  }
}
