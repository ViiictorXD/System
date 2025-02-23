package br.com.system.bukkit.factory;

import br.com.system.core.model.user.address.Address;
import org.bukkit.entity.Player;

import java.time.Instant;

public class AddressFactory {

  public Address createAddress(Player player) {
    return new Address(player.getAddress().getHostName(), Instant.now());
  }
}
