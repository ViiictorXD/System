package br.com.system.bukkit.listener.prevent;

import br.com.system.bukkit.SystemPlugin;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

@RequiredArgsConstructor
public class WorldListener implements Listener {

  private final SystemPlugin plugin;

  @EventHandler
  private void onWeatherChange(WeatherChangeEvent event) {
    if (!event.isCancelled()
     && plugin.getSettings().getCancelWeatherChangeWorlds().contains(event.getWorld().getName())) {
      event.setCancelled(true);
    }
  }
}
