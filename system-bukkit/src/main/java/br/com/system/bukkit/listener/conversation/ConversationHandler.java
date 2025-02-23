package br.com.system.bukkit.listener.conversation;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.core.conversation.Conversation;
import br.com.system.core.conversation.step.ConversationStep;
import br.com.system.core.conversation.step.ConversationStepType;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class ConversationHandler implements Listener {

  private final SystemPlugin plugin;

  @EventHandler
  private void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
    Player player = event.getPlayer();

    Conversation conversation = plugin.getConversationManager().getConversation(player.getUniqueId());
    if (conversation == null) return;

    event.setCancelled(true);

    ConversationStep conversationStep = conversation.getCurrentConversationStep();
    if (conversationStep == null || conversationStep.getType() != ConversationStepType.CHAT) return;

    String message = event.getMessage();

    if (message.equalsIgnoreCase("cancelar")) {
      plugin.getConversationManager().removeEntity(plugin.getConversationManager().getConversation(player.getUniqueId()));
      conversation.onCancel(player);
      return;
    }

    if (conversationStep.handlePlayerInput(conversation.getContext()).test(message, player)) {
      if (conversation.isOnLastStep()) {
        conversation.onFinish(player, conversation.getContext());
        return;
      }

      conversation.advanceStep(player);
    }
  }

  @EventHandler
  private void onPlayerQuit(PlayerQuitEvent event) {
    plugin.getConversationManager().removeEntity(plugin.getConversationManager().getConversation(event.getPlayer().getUniqueId()));
  }

  @EventHandler
  private void onPlayerKick(PlayerKickEvent event) {
    plugin.getConversationManager().removeEntity(plugin.getConversationManager().getConversation(event.getPlayer().getUniqueId()));
  }
}
