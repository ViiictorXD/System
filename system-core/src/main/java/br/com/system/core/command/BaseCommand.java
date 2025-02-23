package br.com.system.core.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class BaseCommand extends Command implements SimpleExecutor {

  protected CommandSender sender;
  protected String[] args;

  protected CommandInfo info;

  public BaseCommand(CommandInfo info) {
    super(
     info.getName(),
     info.getDescription(),
     info.getUsageMessage(),
     info.getAlias()
    );

    this.info = info;

    if (info.getPermission() != null && info.getPermissionMessage() != null) {
      setPermission(info.getPermission());
      setPermissionMessage(info.getPermissionMessage());
    }
  }

  @Override
  public boolean execute(CommandSender sender, String label, String[] args) {
    if (info.getPermission() != null && !sender.hasPermission(info.getPermission())) {
      sender.sendMessage(info.getPermissionMessage());
      return false;
    }

    execute(
     this.sender = sender,
     this.args = args
    );
    return false;
  }
}
