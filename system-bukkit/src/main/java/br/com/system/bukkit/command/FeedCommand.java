package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand extends BaseCommand {

  private final SystemPlugin system;

  public FeedCommand(CommandInfo info, SystemPlugin system) {
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

      target.setAllowFlight(!target.getAllowFlight());

      if (args.length == 1 || !args[1].equalsIgnoreCase("-s")) {
        system.getExecutor().msg(target, "commands.feed.target-feed", "%player-name%", sender.getName());
      }

      system.getExecutor().msg(sender, "commands.feed.target-feed-self", "%target-name%", target.getName());
      return;
    }

    if (sender instanceof Player) {
      Player player = (Player) sender;
      player.setFoodLevel(20);
      system.getExecutor().msg(player, "commands.feed.feed");
      return;
    }

    system.getExecutor().msg(sender, "commands.feed.usage");
  }
}
