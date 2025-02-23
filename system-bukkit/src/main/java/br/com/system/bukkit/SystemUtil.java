package br.com.system.bukkit;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class SystemUtil {

  public static final String CLEAR_CHAT;

  public static String withColor(String message) {
    return ChatColor.translateAlternateColorCodes('&', message);
  }

  public static Integer asInt(String str) {
    try {
      return Integer.parseInt(str);
    } catch (NumberFormatException ignored) {
      return null;
    }
  }

  public static void clearPlayerInventory(Player player) {
    PlayerInventory inventory = player.getInventory();
    inventory.setContents(new ItemStack[0]);
    inventory.setArmorContents(new ItemStack[0]);

    player.setItemInHand(null);

    player.updateInventory();
  }

  static {
    CLEAR_CHAT = StringUtils.repeat("\n ", 100);
  }
}
