package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class CraftCommand extends BaseCommand {

  private final SystemPlugin plugin;

  public CraftCommand(CommandInfo info, SystemPlugin plugin) {
    super(info);
    this.plugin = plugin;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof Player)) {
      plugin.getExecutor().msg(sender, "commands.only-players");
      return;
    }

    ((Player) sender).openInventory(Bukkit.createInventory(null, InventoryType.WORKBENCH));
    plugin.getExecutor().msg(sender, "commands.craft.opened");
  }
}
