package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import br.com.system.core.model.user.User;
import org.bukkit.command.CommandSender;

public class AddressesCommand extends BaseCommand {

  private final SystemPlugin plugin;

  public AddressesCommand(CommandInfo info, SystemPlugin plugin) {
    super(info);
    this.plugin = plugin;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (args.length != 1) {
      plugin.getExecutor().msg(sender, "commands.addresses.usage");
      return;
    }

    User user = plugin.getUserManager().getUserByName(args[0]);

    if (user == null) {
      plugin.getExecutor().msg(sender, "player-not-found",
       "%target-name%", args[0]
      );
      return;
    }

    plugin.getExecutor().msg(sender, "commands.addresses.success",
     "%target-name%", user.getName(),
     "%target-addresses%", user.getBeautifulAddresses()
    );
  }
}
