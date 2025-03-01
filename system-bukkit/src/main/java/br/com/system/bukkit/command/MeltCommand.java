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

public class MeltCommand extends BaseCommand {

  private final SystemPlugin system;

  public MeltCommand(CommandInfo info, SystemPlugin system) {
    super(info);
    this.system = system;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof Player)) {
      system.getExecutor().msg(sender, "commands.only-players");
      return;
    }

    int melt = melt((Player) sender);
    if (melt <= 0) {
      system.getExecutor().msg(sender, "commands.melt.no-one");
      return;
    }

    system.getExecutor().msg(sender, "commands.melt.melted", "%ore-count%", melt);
  }

  private int melt(Player player) {
    PlayerInventory inventory = player.getInventory();
    ItemStack[] contents = inventory.getContents();

    int melt = 0;
    for (ItemStack itemStack : contents) {
      if (itemStack == null) continue;

      XMaterial material = XMaterial.matchXMaterial(itemStack.getType());
      if (!material.name().contains("ORE")) continue;

      SystemOreType systemOreType = SystemOreType.findAny(material);
      if (systemOreType == null) continue;

      inventory.remove(itemStack);

      ItemStack oreAsBlock = systemOreType.getMaterial().parseItem();
      if (oreAsBlock == null) continue;
      oreAsBlock.setAmount(itemStack.getAmount());

      inventory.addItem(oreAsBlock);

      melt += itemStack.getAmount();
    }
    return melt;
  }
}
