package br.com.system.bukkit.resource;

import br.com.system.bukkit.SystemPlugin;
import org.bukkit.Bukkit;

public class Tasks {

  private static final SystemPlugin SYSTEM_PLUGIN;

  public static void async(Runnable runnable) {
    Bukkit.getScheduler().runTaskAsynchronously(SYSTEM_PLUGIN, runnable);
  }

  static {
    SYSTEM_PLUGIN = SystemPlugin.getInstance();
  }
}
