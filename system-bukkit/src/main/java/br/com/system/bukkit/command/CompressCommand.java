package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.bukkit.types.SystemOreType;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class CompressCommand extends BaseCommand {

  private final SystemPlugin system;

  public CompressCommand(CommandInfo info, SystemPlugin system) {
    super(info);
    this.system = system;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof Player)) {
      system.getExecutor().msg(sender, "commands.only-players");
      return;
    }

    int compress = compress((Player) sender);
    if (compress <= 0) {
      system.getExecutor().msg(sender, "commands.compress.empty");
      return;
    }

    system.getExecutor().msg(sender, "commands.compress.compressed", "%ore-count%", compress);
  }

  private int compress(Player player) {
    PlayerInventory inventory = player.getInventory();
    ItemStack[] contents = inventory.getContents();

    List<ItemStack> giveBack = new ArrayList<>();

    int compress = 0;
    for (int index = 0; index < contents.length; index++) {
      ItemStack itemStack = contents[index];
      if (itemStack == null) continue;

      XMaterial material = XMaterial.matchXMaterial(itemStack.getType());

      if (material == XMaterial.AIR
       || itemStack.getAmount() < 9
       || itemStack.hasItemMeta()) {
        continue;
      }

      SystemOreType systemOreType = SystemOreType.findSingle(material);
      if (systemOreType == null) continue;

      int giveAmount = itemStack.getAmount() / 9; // 1
      int giveBackAmount = itemStack.getAmount() - (giveAmount * 9);

      inventory.remove(itemStack);

      ItemStack oreAsResult = systemOreType.getResult().parseItem();
      if (oreAsResult == null) continue;

      oreAsResult.setAmount(giveAmount);
      inventory.addItem(oreAsResult);

      if (giveBackAmount > 0) {
        ItemStack oreAsSimple = systemOreType.getMaterial().parseItem();
        if (oreAsSimple == null) continue;
        oreAsSimple.setAmount(giveBackAmount);

        giveBack.add(oreAsSimple);
      }

      compress += giveAmount * 9;
    }

    for (ItemStack itemStack : giveBack) {
      for (ItemStack rest : inventory.addItem(itemStack).values()) {
        player.getWorld().dropItem(player.getLocation(), rest);
      }
    }
    return compress;
  }
}
