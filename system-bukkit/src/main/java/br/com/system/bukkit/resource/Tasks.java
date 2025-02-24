package br.com.system.bukkit.resource;

import br.com.system.bukkit.SystemPlugin;
import com.tcoded.folialib.FoliaLib;

public class Tasks {

  private static final SystemPlugin SYSTEM_PLUGIN;
  private static final FoliaLib FOLIA_LIB;

  public static void async(Runnable runnable) {
    FOLIA_LIB.getScheduler().runAsync((task) -> runnable.run());
  }

  static {
    SYSTEM_PLUGIN = SystemPlugin.getInstance();
    FOLIA_LIB = new FoliaLib(SYSTEM_PLUGIN);
  }
}
