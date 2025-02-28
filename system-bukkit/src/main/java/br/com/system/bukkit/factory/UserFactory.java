package br.com.system.bukkit.factory;

import br.com.system.core.model.user.User;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

public class UserFactory {

  public User createUser(UUID id, String name, String display, Location spawnLocation, Location lastLocation) {
    return new User(id, name, display, spawnLocation, lastLocation, new ArrayList<>(), new ArrayList<>(), Instant.now(), Instant.now());
  }

  public User createUser(Player player) {
    return createUser(
     player.getUniqueId(),
     player.getName(),
     player.getName(),
     player.getLocation(),
     player.getLocation()
    );
  }
}
