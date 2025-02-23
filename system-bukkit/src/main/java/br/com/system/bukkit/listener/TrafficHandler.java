package br.com.system.bukkit.listener;

import br.com.system.bukkit.SystemPlugin;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class TrafficHandler implements Listener {

  private final SystemPlugin plugin;

  @EventHandler
  private void onJoin(PlayerJoinEvent event) {
    plugin.getUserManager().load(event.getPlayer());
  }

  @EventHandler
  private void onQuit(PlayerQuitEvent event) {
    plugin.getUserManager().unload(event.getPlayer());
  }
}
