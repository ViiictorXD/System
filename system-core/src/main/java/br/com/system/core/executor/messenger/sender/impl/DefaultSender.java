package br.com.system.core.executor.messenger.sender.impl;

import br.com.system.core.executor.messenger.sender.Sender;
import org.bukkit.command.CommandSender;

public class DefaultSender implements Sender {

  @Override
  public void send(CommandSender sender, String message) {
    sender.sendMessage(message);
  }
}
