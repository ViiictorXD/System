package br.com.system.bukkit.service;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.bukkit.listener.TrafficHandler;
import br.com.system.bukkit.listener.conversation.ConversationHandler;
import br.com.system.bukkit.listener.prevent.GeneralListener;
import br.com.system.bukkit.listener.prevent.PlayerListener;
import br.com.system.bukkit.listener.prevent.WorldListener;
import br.com.system.core.data.Service;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

@RequiredArgsConstructor
public class ListenerService implements Service {

  private final SystemPlugin plugin;

  @Override
  public void start() {
    listeners(
     new ConversationHandler(plugin),
     new GeneralListener(plugin),
     new PlayerListener(plugin),
     new WorldListener(plugin),
     new TrafficHandler(plugin)
    );

    plugin.getLogger().info("Eventos carregados com sucesso");
  }

  private void listeners(Listener... listeners) {
    for (Listener listener : listeners) {
      Bukkit.getPluginManager().registerEvents(listener, plugin);
    }
  }
}
