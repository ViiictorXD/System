package br.com.system.core;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class PlayerUtil {

  public static int getPlayerInventorySpace(Player player) {
    return (int) Arrays
            .stream(player.getInventory().getContents())
            .filter(i -> i != null && i.getType() != Material.AIR)
            .count();
  }
}
