package br.com.system.core.conversation;

import br.com.system.core.conversation.step.ConversationStep;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public abstract class Conversation {

  private final UUID id;

  private final List<ConversationStep> conversationSteps = new ArrayList<>();
  private final ConversationContext context = new ConversationContext();

  @Setter
  private int currentStep = 0;

  public ConversationStep getCurrentConversationStep() {
    return conversationSteps.get(currentStep);
  }

  public void addConversationStep(ConversationStep conversationStep) {
    conversationSteps.add(conversationStep);
  }

  public boolean isOnLastStep() {
    return currentStep == (conversationSteps.size() - 1);
  }

  public void addConversationSteps(ConversationStep... conversationSteps) {
    Arrays.stream(conversationSteps).forEach(this::addConversationStep);
  }

  public void removeConversationStep(ConversationStep conversationStep) {
    conversationSteps.remove(conversationStep);
  }

  public boolean hasStep(ConversationStep conversationStep) {
    return conversationSteps.contains(conversationStep);
  }

  public void removeStep(ConversationStep conversationStep) {
    conversationSteps.remove(conversationStep);
  }

  public void startProcess(Player player) {
    currentStep = 0;
    startCurrentStep(player);
  }

  private void backStep(Player player) {
    currentStep--;
    startCurrentStep(player);
  }

  public void advanceStep(Player player) {
    currentStep++;
    startCurrentStep(player);
  }

  protected void startCurrentStep(Player player) {
    if (currentStep < 0 || currentStep >= conversationSteps.size()) return;

    ConversationStep conversationStep = conversationSteps.get(currentStep);
    if (conversationStep == null) return;

    conversationStep.send(player);
  }

  public abstract void onFinish(Player player, ConversationContext context);

  public abstract void onCancel(Player player);
}
