package br.com.system.bukkit.conversation.kit.create;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.bukkit.conversation.kit.create.step.*;
import br.com.system.core.conversation.Conversation;
import br.com.system.core.conversation.ConversationContext;
import br.com.system.core.serializer.ItemStackSerializer;
import br.com.system.core.model.kit.Kit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class KitCreateConversation extends Conversation {

  private final SystemPlugin plugin;

  public KitCreateConversation(UUID id, SystemPlugin plugin) {
    super(id);

    this.plugin = plugin;

    addConversationSteps(
     new KitNameCreateStep(plugin),
     new KitPermissionCreateStep(plugin),
     new KitItemStacksCreateStep(plugin),
     new KitDelayCreateStep(plugin),
     new KitMaxPickupsCreateStep(plugin)
    );
  }

  @Override
  public void onFinish(Player player, ConversationContext context) {
    String name = context.get("name");
    String permission = context.get("permission");
    ItemStack[] itemStacks = ItemStackSerializer.deserialize(context.get("itemStacks"));
    long delay = TimeUnit.SECONDS.toMillis(context.get("delay"));
    int maxPickups = context.get("max-pickups");

    Kit kit = plugin.getKitFactory().createKit(name, permission, itemStacks, delay, maxPickups);

    plugin.getKitManager().addEntity(kit);
    plugin.getKitManager().save(kit, new File(plugin.getDataFolder(), String.format("kits/%s.yml", kit.getName())));

    plugin.getConversationManager().removeEntity(this);

    plugin.getExecutor().msg(player, "commands.create-kit.created", "%kit-name%", name);
  }

  @Override
  public void onCancel(Player player) {
    plugin.getExecutor().msg(player, "commands.create-kit.operation-cancelled");
  }
}
