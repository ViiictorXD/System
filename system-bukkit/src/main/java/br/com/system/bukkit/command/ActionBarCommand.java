package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ActionBarCommand extends BaseCommand {

  private final SystemPlugin plugin;

  public ActionBarCommand(CommandInfo info, SystemPlugin plugin) {
    super(info);
    this.plugin = plugin;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (args.length == 0) {
      plugin.getExecutor().msg(sender, "commands.action.usage");
      return;
    }

    String fullMessage = String.join(" ", args);

    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
      plugin.getExecutor().msg(onlinePlayer, "commands.action.success",
       "%message%", fullMessage,
       "%player-name%", sender.getName()
      );
    }

    plugin.getExecutor().msg(sender, "commands.action.sent");
  }
}
