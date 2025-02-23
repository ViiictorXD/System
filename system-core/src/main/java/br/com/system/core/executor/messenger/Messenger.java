package br.com.system.core.executor.messenger;

import br.com.system.core.executor.messenger.sender.Sender;
import br.com.system.core.executor.messenger.sender.impl.ActionSender;
import br.com.system.core.executor.messenger.sender.impl.ActionsSender;
import br.com.system.core.executor.messenger.sender.impl.BroadcastSender;
import br.com.system.core.executor.messenger.sender.impl.DefaultSender;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Messenger {

  ACTION(new ActionSender()),
  ACTIONS(new ActionsSender()),
  // todo: create title's instances
  TITLE(new DefaultSender()),
  TITLES(new DefaultSender()),
  BROADCAST(new BroadcastSender()),
  NONE(new DefaultSender());

  private final Sender sender;

  public String strip(String subject) {
    return subject.replace("[" + toString().toLowerCase() + "]", "")
     .replace("[" + toString().toLowerCase() + "] ", "");
  }

  public static Messenger resolve(String subject) {
    return Arrays.stream(values())
     .filter(type -> subject.toLowerCase().startsWith("[" + type.name().toLowerCase() + "]"))
     .findAny()
     .orElse(NONE);
  }
}
