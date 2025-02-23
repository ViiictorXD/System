package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.bukkit.SystemSettings;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodCommand extends BaseCommand {

  private final SystemPlugin system;

  public GodCommand(CommandInfo info, SystemPlugin system) {
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

      String path = "commands.god.target-god-";

      if (SystemSettings.GOD_MODE.remove(target.getUniqueId()))
        path += "off";
      else {
        SystemSettings.GOD_MODE.add(target.getUniqueId());
        path += "on";
      }

      if (args.length == 1 || !args[1].equalsIgnoreCase("-s")) {
        system.getExecutor().msg(target, path, "%player-name%", sender.getName());
      }

      system.getExecutor().msg(sender, path + "-self");
      return;
    }

    if (sender instanceof Player) {
      Player player = (Player) sender;

      if (SystemSettings.GOD_MODE.remove(player.getUniqueId())) {
        system.getExecutor().msg(sender, "commands.god.god-off");
        return;
      }

      SystemSettings.GOD_MODE.add(player.getUniqueId());
      system.getExecutor().msg(sender, "commands.god.god-on");
      return;
    }

    system.getExecutor().msg(sender, "commands.god.usage");
  }
}
