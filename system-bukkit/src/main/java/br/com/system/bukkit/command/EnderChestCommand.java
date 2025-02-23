package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderChestCommand extends BaseCommand {

  private final SystemPlugin system;

  public EnderChestCommand(CommandInfo info, SystemPlugin system) {
    super(info);
    this.system = system;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof Player)) {
      system.getExecutor().msg(sender, "commands.only-players");
      return;
    }

    ((Player) sender).openInventory(((Player) sender).getEnderChest());
    system.getExecutor().msg(sender, "commands.enderchest.opened");
  }
}
