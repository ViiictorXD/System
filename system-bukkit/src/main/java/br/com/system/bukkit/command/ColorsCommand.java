package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import org.bukkit.command.CommandSender;

public class ColorsCommand extends BaseCommand {

  private final SystemPlugin system;

  public ColorsCommand(CommandInfo info, SystemPlugin system) {
    super(info);
    this.system = system;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    system.getExecutor().msg(sender, "commands.colors.success");
  }
}
