package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpallCommand extends BaseCommand {

  private final SystemPlugin system;

  public TpallCommand(CommandInfo info, SystemPlugin system) {
    super(info);
    this.system = system;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (args.length > 0) {
      Player target = Bukkit.getPlayer(args[0]);

      if (target == null) {
        system.getExecutor().msg(sender, "player-not-found", "%target-name%", args[0]);
        return;
      }

      teleportTo(target);
      system.getExecutor().msg(sender, "commands.tpall.target-tpall", "%target-name%", target.getName());
      return;
    }

    if (sender instanceof Player) {
      teleportTo((Player) sender);
      system.getExecutor().msg(sender, "commands.tpall.tpall");
      return;
    }

    system.getExecutor().msg(sender, "commands.tpall.usage");
  }

  private void teleportTo(Player player) {
    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
      if (onlinePlayer.getUniqueId().equals(player.getUniqueId())) continue;
      onlinePlayer.teleport(player);
    }
  }
}
