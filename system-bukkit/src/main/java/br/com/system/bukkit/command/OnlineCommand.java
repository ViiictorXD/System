package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class OnlineCommand extends BaseCommand {

  private final SystemPlugin system;

  public OnlineCommand(CommandInfo info, SystemPlugin system) {
    super(info);
    this.system = system;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    system.getExecutor().msg(sender, "commands.online.success",
     "%players%", Bukkit.getOnlinePlayers().size(),
     "%max-players%", Bukkit.getMaxPlayers()
    );
  }
}
