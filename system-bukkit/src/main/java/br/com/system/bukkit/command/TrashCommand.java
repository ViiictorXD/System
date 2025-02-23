package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrashCommand extends BaseCommand {

  private final SystemPlugin system;

  public TrashCommand(CommandInfo info, SystemPlugin system) {
    super(info);
    this.system = system;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof Player)) {
      system.getExecutor().msg(sender, "commands.only-players");
      return;
    }

    ((Player) sender).openInventory(Bukkit.createInventory(null,
     9 * system.getSettings().getTrashSize(),
     system.getSettings().getTrashTitle()
    ));

    system.getExecutor().msg(sender, "commands.trash.opened");
  }
}
