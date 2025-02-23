package br.com.system.core.serializer;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Locale;
import java.util.StringJoiner;

public class LocationSerializer {

  public static String serialize(Location location) {
    try {
      StringJoiner joiner = new StringJoiner(";");
      joiner.add(location.getWorld().getName());
      joiner.add(String.format(Locale.US, "%.2f", location.getX()));
      joiner.add(String.format(Locale.US, "%.2f", location.getY()));
      joiner.add(String.format(Locale.US, "%.2f", location.getZ()));
      joiner.add(String.format(Locale.US, "%.2f", location.getYaw()));
      joiner.add(String.format(Locale.US, "%.2f", location.getPitch()));

      return joiner.toString();
    } catch (Exception ignored) {
      return null;
    }
  }

  public static Location deserialize(String serializedLocation) {
    if (serializedLocation == null) return null;

    String[] pattern = serializedLocation.split(";");

    return new Location(
     Bukkit.getWorld(pattern[0]),
     Double.parseDouble(pattern[1]),
     Double.parseDouble(pattern[2]),
     Double.parseDouble(pattern[3]),
     Float.parseFloat(pattern[4]),
     Float.parseFloat(pattern[5])
    );
  }
}
