package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvseeCommand extends BaseCommand {

  private final SystemPlugin system;

  public InvseeCommand(CommandInfo info, SystemPlugin system) {
    super(info);
    this.system = system;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof Player)) {
      system.getExecutor().msg(sender, "commands.only-players");
      return;
    }

    if (args.length <= 1) {
      system.getExecutor().msg(sender, "commands.invsee.usage");
      return;
    }

    Player target = Bukkit.getPlayer(args[0]);

    if (target == null) {
      system.getExecutor().msg(sender, "player-not-found", "%target-name%", args[0]);
      return;
    }

    ((Player) sender).openInventory(target.getInventory());

    if (args.length == 2 || !args[1].equalsIgnoreCase("-s")) {
      system.getExecutor().msg(target, "commands.invsee.target-inv", "%player-name%", sender.getName());
    }

    system.getExecutor().msg(sender, "commands.invsee.opened", "%target-name%", target.getName());
  }
}
