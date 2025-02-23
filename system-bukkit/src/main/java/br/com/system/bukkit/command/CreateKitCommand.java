package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.bukkit.conversation.kit.create.KitCreateConversation;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateKitCommand extends BaseCommand {

  private final SystemPlugin plugin;

  public CreateKitCommand(CommandInfo info, SystemPlugin plugin) {
    super(info);
    this.plugin = plugin;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    KitCreateConversation kitCreateConversation = new KitCreateConversation(((Player) sender).getUniqueId(), plugin);
    kitCreateConversation.startProcess((Player) sender);

    plugin.getConversationManager().addEntity(kitCreateConversation);
  }
}
