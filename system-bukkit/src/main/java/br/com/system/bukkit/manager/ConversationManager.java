package br.com.system.bukkit.manager;

import br.com.system.core.manager.AbstractManager;
import br.com.system.core.conversation.Conversation;

import java.util.UUID;

public class ConversationManager extends AbstractManager<Conversation> {

  public Conversation getConversation(UUID id) {
    return optional(conversation -> conversation.getId().equals(id))
     .orElse(null);
  }

  public boolean hasConversation(UUID id) {
    return anyMatch(conversation -> conversation.getId().equals(id));
  }
}
