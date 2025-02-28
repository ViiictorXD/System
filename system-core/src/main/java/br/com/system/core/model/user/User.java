package br.com.system.core.model.user;

import br.com.system.core.model.Identifier;
import br.com.system.core.model.user.address.Address;
import br.com.system.core.model.user.kit.UserKit;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Setter
public class User extends Identifier {

  private String display;

  private Location spawnLocation;
  private Location lastLocation;

  private List<Address> addresses;
  private List<UserKit> kits;

  private Instant firstAt;
  private Instant lastAt;

  public User(
   UUID id,
   String name,
   String display,
   Location spawnLocation,
   Location lastLocation,
   List<Address> addresses,
   List<UserKit> kits,
   Instant firstAt,
   Instant lastAt
  ) {
    super(id, name);
    this.display = display;
    this.spawnLocation = spawnLocation;
    this.lastLocation = lastLocation;
    this.addresses = addresses;
    this.kits = kits;
    this.firstAt = firstAt;
    this.lastAt = lastAt;
  }

  public Address getLatestAddress() {
    return addresses.stream()
     .sorted(Comparator.comparingLong(Address::toEpochMilli).reversed())
     .findAny()
     .orElse(null);
  }

  public String getBeautifulAddresses() {
    return IntStream.range(0, addresses.size())
     .mapToObj(i -> (i == addresses.size() - 1)
      ? addresses.get(i).getAddress() : (i == addresses.size() - 2)
      ? addresses.get(i).getAddress() + " e " : addresses.get(i).getAddress() + ", ")
     .collect(Collectors.joining());
  }

  public UserKit getUserKit(String kitName) {
    return kits.stream().filter(kit -> kit.getKitName().equalsIgnoreCase(kitName)).findAny().orElse(null);
  }

  public void addUserKit(UserKit kit) {
    kits.add(kit);
  }

  public void removeUserKit(UserKit kit) {
    kits.remove(kit);
  }
}
