package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.bukkit.SystemUtil;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import br.com.system.core.model.user.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCommand extends BaseCommand {

  private final SystemPlugin plugin;

  public NickCommand(CommandInfo info, SystemPlugin plugin) {
    super(info);
    this.plugin = plugin;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof Player)) {
      plugin.getExecutor().msg(sender, "commands.only-players");
      return;
    }

    if (args.length != 1) {
      plugin.getExecutor().msg(sender, "commands.nick.usage");
      return;
    }

    if (!args[0].matches(plugin.getSettings().getNickFormat())) {
      plugin.getExecutor().msg(sender, "commands.nick.invalid");
    }

    String displayName = SystemUtil.withColor(args[0]);

    User user = plugin.getUserManager().getUserByName(sender.getName());
    user.setDisplay(displayName);

    ((Player) sender).setDisplayName(displayName);

    plugin.getUserRepository().save(user);

    plugin.getExecutor().msg(sender, "commands.nick.changed",
     "%nick%", displayName
    );
  }
}
