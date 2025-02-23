package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HatCommand extends BaseCommand {

  private final SystemPlugin system;

  public HatCommand(CommandInfo info, SystemPlugin system) {
    super(info);
    this.system = system;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof Player)) {
      system.getExecutor().msg(sender, "commands.only-players");
      return;
    }

    Player player = (Player) sender;

    ItemStack itemInHand = player.getItemInHand();
    ItemStack helmet = player.getInventory().getHelmet();

    if (!player.hasPermission("system.bypass.hat")
     && (itemInHand.getType() == Material.AIR || !itemInHand.getType().isBlock())) {
      system.getExecutor().msg(sender, "commands.hat.invalid");
      return;
    }

    player.getInventory().setHelmet(itemInHand);
    player.setItemInHand(helmet);

    system.getExecutor().msg(sender, "commands.hat.changed");
  }
}
