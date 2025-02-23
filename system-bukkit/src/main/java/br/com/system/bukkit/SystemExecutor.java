package br.com.system.bukkit;

import br.com.system.core.executor.ExecutorContext;
import br.com.system.core.executor.ExecutorType;
import br.com.system.core.executor.messenger.Messenger;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class SystemExecutor {

  private final SystemPlugin system;

  public void run(CommandSender sender, String id, String path, Object... objects) {
    FileConfiguration configuration = system.getConfiguration().getConfiguration(id);
    if (configuration == null) return;

    List<ExecutorContext> contexts = generateContexts(configuration, path);
    if (contexts.isEmpty()) return;

    for (ExecutorContext context : contexts) {
      ExecutorType type = context.getType();
      String subject = resolveReplacement(context.getContext(), objects);

      if (type == ExecutorType.COMMAND) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), type.stripIdentifiers(subject));
        continue;
      }

      Messenger messenger = Messenger.resolve(subject);
      messenger.getSender().send(sender, SystemUtil.withColor(messenger.strip(subject)));
    }
  }

  public String get(String id, String path, Object... objects) {
    FileConfiguration configuration = system.getConfiguration().getConfiguration(id);
    if (configuration == null) return null;

    String value = configuration.getString(path);
    if (value == null) return null;

    return resolveReplacement(value, objects);
  }

  private String resolveReplacement(String value, Object... objects) {
    if (objects != null && objects.length % 2 == 0) {
      for (int index = 0; index < objects.length; index += 2) {
        String replacementKey = objects[index].toString();
        String replacementValue = objects[index + 1].toString();

        value = value.replace(replacementKey, replacementValue);
      }
    }
    return value;
  }

  private List<ExecutorContext> generateContexts(FileConfiguration configuration, String path) {
    if (!configuration.contains(path)) return new ArrayList<>();

    List<ExecutorContext> output = new ArrayList<>();

    Object object = configuration.get(path);
    if (object instanceof String) {
      String subject = String.valueOf(object);

      output.add(new ExecutorContext(ExecutorType.resolve(subject), subject));
    }

    if (object instanceof Collection) {
      List<String> contexts = (List<String>) object;

      for (String context : contexts) {
        output.add(new ExecutorContext(ExecutorType.resolve(context), context));
      }
    }
    return output;
  }

  public void msg(CommandSender sender, String path, Object... objects) {
    run(sender, system.getConfig().getString("language", "pt_BR"), path, objects);
  }

  public String msg(String path, Object... objects) {
    return get(system.getConfig().getString("language", "pt_BR"), path, objects);
  }
}
