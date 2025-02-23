package br.com.system.bukkit.listener.prevent;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.bukkit.SystemSettings;
import br.com.system.core.model.user.User;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

@RequiredArgsConstructor
public class PlayerListener implements Listener {

  private final SystemPlugin plugin;

  @EventHandler
  private void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();

    String joinMessage = plugin.getSettings().getJoinMessage();
    if (joinMessage == null || joinMessage.isEmpty()) {
      event.setJoinMessage(null);
      return;
    }

    event.setJoinMessage(joinMessage.replace("%player-name%", player.getName()));
  }

  @EventHandler
  private void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();

    String quitMessage = plugin.getSettings().getQuitMessage();
    if (quitMessage == null || quitMessage.isEmpty()) {
      event.setQuitMessage(null);
      return;
    }

    event.setQuitMessage(quitMessage.replace("%player-name%", player.getName()));
  }

  @EventHandler
  private void onPlayerDeath(PlayerDeathEvent event) {
    Player player = event.getEntity();

    String quitMessage = plugin.getSettings().getDeathMessage();
    if (quitMessage == null || quitMessage.isEmpty()) {
      event.setDeathMessage(null);
      return;
    }

    event.setDeathMessage(quitMessage.replace("%player-name%", player.getName()));
  }

  @EventHandler
  private void onPlayerTeleport(PlayerTeleportEvent event) {
    if (event.isCancelled() || event.getTo() == null) return;

    Player player = event.getPlayer();

    User user = plugin.getUserManager().getUserById(player.getUniqueId());
    user.setLastLocation(event.getTo());

    plugin.getUserRepository().save(user);
  }

  @EventHandler
  private void onEntityDamage(EntityDamageEvent event) {
    if (!event.isCancelled()
     && event.getEntity().getType() == EntityType.PLAYER
     && SystemSettings.GOD_MODE.contains(event.getEntity().getUniqueId())) {
      event.setCancelled(true);
    }
  }
}
