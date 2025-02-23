package br.com.system.core.conversation.step;

import br.com.system.core.conversation.ConversationContext;
import org.bukkit.entity.Player;

import java.util.function.BiPredicate;

public interface ConversationStep {

  void send(Player player, Object... objects);

  default ConversationStepType getType() {
    return ConversationStepType.CHAT;
  }

  BiPredicate<Object, Player> handlePlayerInput(ConversationContext context);
}
