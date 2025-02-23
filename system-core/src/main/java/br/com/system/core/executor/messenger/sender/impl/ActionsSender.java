package br.com.system.core.executor.messenger.sender.impl;

import br.com.system.core.BukkitNMSVersion;
import br.com.system.core.executor.messenger.sender.Sender;
import com.cryptomorin.xseries.messages.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ActionsSender implements Sender {

  @Override
  public void send(CommandSender sender, String message) {

    for (Player target : Bukkit.getOnlinePlayers()) {
      if (sender.getName().equalsIgnoreCase(target.getName())) {
        continue;
      }

      if (BukkitNMSVersion.getCurrentVersion().higherThan(BukkitNMSVersion.V1_7)) ActionBar.sendActionBar(target, message);
      else target.sendMessage(message);
    }

    if (BukkitNMSVersion.getCurrentVersion().higherThan(BukkitNMSVersion.V1_7) && sender instanceof Player) {
      ActionBar.sendActionBar((Player) sender, message);
      return;
    }

    sender.sendMessage(message);
  }
}
