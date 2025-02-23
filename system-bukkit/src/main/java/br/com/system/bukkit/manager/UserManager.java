package br.com.system.bukkit.manager;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.core.manager.AbstractManager;
import br.com.system.core.model.user.User;
import br.com.system.core.model.user.address.Address;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.UUID;

@RequiredArgsConstructor
public class UserManager extends AbstractManager<User> {

  private final SystemPlugin plugin;

  public User getUserById(UUID id) {
    return optional(user -> user.getId().equals(id))
     .orElse(null);
  }

  public User getUserByName(String name) {
    return optional(user -> user.getName().equalsIgnoreCase(name))
     .orElse(null);
  }

  public void load(Player player) {
    User user = getUserById(player.getUniqueId());
    if (user != null) {
      Address latestAddress = user.getLatestAddress();

      /* Se não existir, cria um novo endereço e salva no banco de dados */
      if (latestAddress == null) {
        latestAddress = plugin.getAddressFactory().createAddress(player);
        plugin.getAddressRepository().create(user, latestAddress);
      }

      /* Se existir e for diferente, cria um novo endereço e salva no banco de dados */
      if (!latestAddress.getAddress().equalsIgnoreCase(player.getAddress().getHostName())) {
        user.getAddresses().add(latestAddress = plugin.getAddressFactory().createAddress(player));
        plugin.getAddressRepository().create(user, latestAddress);
      }
      return;
    }

    Address address = plugin.getAddressFactory().createAddress(player);

    addEntity(user = plugin.getUserFactory().createUser(player));
    user.getAddresses().add(address);

    plugin.getUserRepository().create(user);
    plugin.getAddressRepository().create(user, address);
  }

  public void unload(Player player) {
    /* É... */
  }
}
