package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.bukkit.SystemUtil;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChatCommand extends BaseCommand {

  private final SystemPlugin system;

  public ClearChatCommand(CommandInfo info, SystemPlugin system) {
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

      target.sendMessage(SystemUtil.CLEAR_CHAT);

      if (args.length == 1 || !args[1].equalsIgnoreCase("-s")) {
        system.getExecutor().msg(target, "commands.clearchat.target-cleared", "%player-name%", sender.getName());
      }

      system.getExecutor().msg(sender, "commands.clearchat.target-cleared-self", "%target-name%", target.getName());
      return;
    }

    for (Player onlinePlayer : Bukkit.getOnlinePlayers())
      onlinePlayer.sendMessage(SystemUtil.CLEAR_CHAT);

    system.getExecutor().msg(sender, "commands.clearchat.cleared");
  }
}
