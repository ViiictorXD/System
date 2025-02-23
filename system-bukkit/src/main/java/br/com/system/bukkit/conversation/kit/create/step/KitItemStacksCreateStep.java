package br.com.system.bukkit.conversation.kit.create.step;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.core.conversation.ConversationContext;
import br.com.system.core.conversation.step.ConversationStep;
import br.com.system.core.serializer.ItemStackSerializer;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiPredicate;

@RequiredArgsConstructor
public class KitItemStacksCreateStep implements ConversationStep {

  private final SystemPlugin plugin;

  @Override
  public void send(Player player, Object... objects) {
    plugin.getExecutor().msg(player, "conversation.kit.create.item-stacks-step", objects);
  }

  @Override
  public BiPredicate<Object, Player> handlePlayerInput(ConversationContext context) {
    return (inputMessage, player) -> {
      String response = inputMessage.toString();
      if (response.equalsIgnoreCase("ok")) {
        context.set("itemStacks", ItemStackSerializer.serialize(player.getInventory().getContents()));
        return true;
      }
      return false;
    };
  }
}
