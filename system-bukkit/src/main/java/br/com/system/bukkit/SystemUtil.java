package br.com.system.bukkit;

import br.com.system.core.BukkitNMSVersion;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.lang.reflect.Method;

public class SystemUtil {

  public static final String CLEAR_CHAT;
  public static final int INFINITE_POTION_DURATION;

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
    player.setItemOnCursor(null);

    try {
      Class<?> playerClass = player.getClass();
      // Procura por Player::setItemInOffHand (só existe na 1.9+)
      Method setItemInOffHand = playerClass.getMethod("setItemInOffHand", org.bukkit.inventory.ItemStack.class);
      setItemInOffHand.invoke(player, (Object) null);
    } catch (Exception ignored) {
      // Se der erro, significa que estamos na 1.8 e não fazemos nada
    }

    player.updateInventory();
  }

  static {
    CLEAR_CHAT = StringUtils.repeat("\n ", 100);
    // PotionEffect::isInfinite pode ser usado por outros desenvolvedores nas versões 1.19.4 para cima.
    // Nessas versões, aparece o símbolo ∞ (infinito) quando o efeito é visto no inventário.
    INFINITE_POTION_DURATION = (
            BukkitNMSVersion.getCurrentVersion().higherThan(BukkitNMSVersion.V1_19) || Bukkit.getVersion().contains("1.19.4")
    ) ? -1 : Integer.MAX_VALUE;
  }
}
