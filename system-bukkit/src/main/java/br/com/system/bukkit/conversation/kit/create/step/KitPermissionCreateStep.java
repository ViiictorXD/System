package br.com.system.bukkit.conversation.kit.create.step;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.core.conversation.ConversationContext;
import br.com.system.core.conversation.step.ConversationStep;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.function.BiPredicate;

@RequiredArgsConstructor
public class KitPermissionCreateStep implements ConversationStep {

  private final SystemPlugin plugin;

  @Override
  public void send(Player player, Object... objects) {
    plugin.getExecutor().msg(player, "conversation.kit.create.permission-step", objects);
  }

  @Override
  public BiPredicate<Object, Player> handlePlayerInput(ConversationContext context) {
    return (inputMessage, player) -> {
      context.set("permission", inputMessage.toString());
      return true;
    };
  }
}
