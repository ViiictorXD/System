package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import br.com.system.core.model.user.User;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackCommand extends BaseCommand {

  private final SystemPlugin plugin;

  public BackCommand(CommandInfo info, SystemPlugin plugin) {
    super(info);
    this.plugin = plugin;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof Player)) {
      plugin.getExecutor().msg(sender, "commands.only-players");
      return;
    }

    Player player = (Player) sender;

    User user = plugin.getUserManager().getUserById(player.getUniqueId());

    Location lastLocation = user.getLastLocation();
    if (lastLocation == null) {
      plugin.getExecutor().msg(sender, "commands.back.location-not-found");
      return;
    }

    player.teleport(lastLocation);
    plugin.getExecutor().msg(sender, "commands.back.teleported");
  }
}
