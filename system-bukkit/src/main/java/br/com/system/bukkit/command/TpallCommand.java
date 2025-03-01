package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import com.tcoded.folialib.FoliaLib;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TpallCommand extends BaseCommand {

  private final SystemPlugin plugin;
  private final FoliaLib foliaLib;

  public TpallCommand(CommandInfo info, SystemPlugin plugin) {
    super(info);
    this.plugin = plugin;
    this.foliaLib = new FoliaLib(plugin);
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (args.length > 0) {
      Player target = Bukkit.getPlayer(args[0]);

      if (target == null) {
        plugin.getExecutor().msg(sender, "player-not-found", "%target-name%", args[0]);
        return;
      }

      teleportTo(target);
      plugin.getExecutor().msg(sender, "commands.tpall.target-tpall", "%target-name%", target.getName());
      return;
    }

    if (sender instanceof Player) {
      teleportTo((Player) sender);
      plugin.getExecutor().msg(sender, "commands.tpall.tpall");
      return;
    }

    plugin.getExecutor().msg(sender, "commands.tpall.usage");
  }

  private void teleportTo(Player player) {
    Location playerLocation = player.getLocation();
    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
      if (onlinePlayer.getUniqueId().equals(player.getUniqueId())) continue;
      foliaLib.getScheduler().teleportAsync(onlinePlayer, playerLocation, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }
  }
}
