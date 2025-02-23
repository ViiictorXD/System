package br.com.system.core.command;

import org.bukkit.command.CommandSender;

public interface SimpleExecutor {

  void execute(CommandSender sender, String[] args);
}
