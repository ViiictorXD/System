package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.bukkit.types.SystemGameMode;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeCommand extends BaseCommand {

  private final SystemPlugin system;

  public GameModeCommand(CommandInfo info, SystemPlugin system) {
    super(info);
    this.system = system;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (args.length < 1) {
      system.getExecutor().msg(sender, "commands.gm.usage");
      return;
    }

    SystemGameMode systemGameMode = SystemGameMode.resolve(args[0]);
    if (systemGameMode == null) {
      system.getExecutor().msg(sender, "commands.gm.not-found");
      return;
    }

    if (args.length > 1) {
      Player target = Bukkit.getPlayer(args[1]);

      if (target == null) {
        system.getExecutor().msg(sender, "player-not-found",
         "%target-name%", args[1]
        );
        return;
      }

      target.setGameMode(systemGameMode.getGameMode());

      if (args.length == 3 || !args[2].equalsIgnoreCase("-s")) {
        system.getExecutor().msg(target, "commands.gm.target-changed", "%gamemode%", getGameModeBeautifulName(systemGameMode), "%player-name%", sender.getName());
      }

      system.getExecutor().msg(sender, "commands.clear.target-changed-self", "%gamemode%", getGameModeBeautifulName(systemGameMode), "%target-name%", target.getName());
      return;
    }

    if (sender instanceof Player) {
      ((Player) sender).setGameMode(systemGameMode.getGameMode());
      system.getExecutor().msg(sender, "commands.gm.changed", "%gamemode%", getGameModeBeautifulName(systemGameMode));
      return;
    }
    system.getExecutor().msg(sender, "commands.gm.usage-console");
  }

  private String getGameModeBeautifulName(SystemGameMode systemGameMode) {
    return system.getExecutor().msg("translate.gamemode." + systemGameMode.toString().toLowerCase());
  }
}
