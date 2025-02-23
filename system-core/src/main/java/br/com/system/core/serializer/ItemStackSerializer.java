package br.com.system.core.serializer;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class ItemStackSerializer {

  public static String serialize(ItemStack[] items) {
    return privateSerialize(removeNullItems(items).toArray(new ItemStack[0]));
  }

  private static String privateSerialize(ItemStack[] items) {
    YamlConfiguration config = new YamlConfiguration();
    config.set("items", items);

    try {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      outputStream.write(config.saveToString().getBytes());
      return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    } catch (IOException exception) {
      exception.printStackTrace();
      return null;
    }
  }

  public static ItemStack[] deserialize(String data) {
    if (data == null || data.isEmpty()) {
      return new ItemStack[0];
    }

    try {
      byte[] decodedBytes = Base64.getDecoder().decode(data);
      String yaml = new String(decodedBytes);

      YamlConfiguration config = new YamlConfiguration();
      config.loadFromString(yaml);

      return ((List<ItemStack>) config.get("items")).toArray(new ItemStack[0]);
    } catch (Exception exception) {
      exception.printStackTrace();
      return new ItemStack[0];
    }
  }

  private static List<ItemStack> removeNullItems(ItemStack[] itemStacks) {
    return Arrays.stream(itemStacks)
     .filter(itemStack -> itemStack != null && itemStack.getType() != Material.AIR)
     .collect(Collectors.toList());
  }
}
