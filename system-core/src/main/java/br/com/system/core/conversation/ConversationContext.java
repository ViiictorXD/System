package br.com.system.core.conversation;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
public class ConversationContext {

  private final Map<String, Object> processContext = new HashMap<>();

  public void set(String key, Object value) {
    processContext.put(key, value);
  }

  public <T> T get(String key) {
    return (T) processContext.get(key);
  }
}
