package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.bukkit.SystemUtil;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearCommand extends BaseCommand {

  private final SystemPlugin plugin;

  public ClearCommand(CommandInfo info, SystemPlugin plugin) {
    super(info);
    this.plugin = plugin;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (args.length > 0) {
      Player target = Bukkit.getPlayer(args[0]);

      if (target == null) {
        plugin.getExecutor().msg(sender, "player-not-found",
         "%target-name%", args[0]
        );
        return;
      }

      SystemUtil.clearPlayerInventory(target);

      if (args.length == 1 || !args[1].equalsIgnoreCase("-s")) {
        plugin.getExecutor().msg(target, "commands.clear.target-cleared",
         "%player-name%", sender.getName()
        );
      }

      plugin.getExecutor().msg(sender, "commands.clear.target-cleared-self",
       "%target-name%", target.getName()
      );
      return;
    }

    if (sender instanceof Player) {
      Player player = (Player) sender;
      SystemUtil.clearPlayerInventory(player);
      plugin.getExecutor().msg(sender, "commands.clear.cleared");
      return;
    }

    plugin.getExecutor().msg(sender, "commands.clear.usage");
  }
}
