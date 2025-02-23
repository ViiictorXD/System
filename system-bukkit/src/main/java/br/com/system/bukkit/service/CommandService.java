package br.com.system.bukkit.service;

import br.com.system.bukkit.SystemCommands;
import br.com.system.bukkit.SystemPlugin;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import br.com.system.core.data.Service;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

@SuppressWarnings("all")
@RequiredArgsConstructor
public class CommandService implements Service {

  private static final String COMMAND_CLASS_PATTERN = "br.com.system.bukkit.command.%sCommand";

  private final SystemPlugin plugin;

  private CommandMap commandMap;

  @Override
  public void start() {
    try {
      Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
      field.setAccessible(true);

      commandMap = (CommandMap) field.get(Bukkit.getServer());
    } catch (Exception exception) {
      plugin.getLogger().warning("Não foi possível instanciar a CommandMap para registrar os comandos.");
      return;
    }

    int registered = 0;

    SystemCommands commands = plugin.getCommands();
    for (CommandInfo commandInfo : commands.all()) {
      registerCommand(commandInfo);
      ++registered;
    }

    plugin.getLogger().info(registered + " comandos registrados com sucesso");
  }

  @SneakyThrows
  private void registerCommand(CommandInfo commandInfo) {
    if (!commandInfo.isEnabled()) return;

    Class<? extends BaseCommand> clazz = (Class<? extends BaseCommand>) Class.forName(String.format(COMMAND_CLASS_PATTERN, commandInfo.getKey()));

    BaseCommand baseCommand = clazz.getConstructor(
     CommandInfo.class,
     SystemPlugin.class
    ).newInstance(commandInfo, plugin);

    commandMap.register(commandInfo.getName(), baseCommand);
  }
}
