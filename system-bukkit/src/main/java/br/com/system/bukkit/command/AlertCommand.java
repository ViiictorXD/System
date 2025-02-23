package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AlertCommand extends BaseCommand {

  private final SystemPlugin system;

  public AlertCommand(CommandInfo info, SystemPlugin system) {
    super(info);
    this.system = system;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (args.length == 0) {
      system.getExecutor().msg(sender, "commands.alert.usage");
      return;
    }

    String fullMessage = String.join(" ", args);

    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
      system.getExecutor().msg(onlinePlayer, "commands.alert.success",
       "%message%", fullMessage,
       "%player-name%", sender.getName()
      );
    }

    system.getExecutor().msg(sender, "commands.alert.sent");
  }
}
