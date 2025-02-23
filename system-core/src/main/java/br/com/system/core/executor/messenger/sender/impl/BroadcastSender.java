package br.com.system.core.executor.messenger.sender.impl;

import br.com.system.core.executor.messenger.sender.Sender;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class BroadcastSender implements Sender {

  @Override
  public void send(CommandSender sender, String message) {
    Bukkit.broadcastMessage(message);
  }
}
