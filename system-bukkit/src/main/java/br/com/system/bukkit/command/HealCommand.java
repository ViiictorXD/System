package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand extends BaseCommand {

  private final SystemPlugin system;

  public HealCommand(CommandInfo info, SystemPlugin system) {
    super(info);
    this.system = system;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (args.length > 0) {
      Player target = Bukkit.getPlayer(args[0]);

      if (target == null) {
        system.getExecutor().msg(sender, "player-not-found",
         "%target-name%", args[0]
        );
        return;
      }

      target.setHealth(20D);

      if (args.length == 1 || !args[1].equalsIgnoreCase("-s")) {
        system.getExecutor().msg(target, "commands.heal.target-healed", "%player-name%", sender.getName());
      }

      system.getExecutor().msg(sender, "commands.heal.target-healed-self", "%target-name%", target.getName());
      return;
    }

    if (sender instanceof Player) {
      Player player = (Player) sender;
      player.setMaxHealth(20D);
      system.getExecutor().msg(player, "commands.heal.healed");
      return;
    }

    system.getExecutor().msg(sender, "commands.heal.usage");
  }
}
