package br.com.system.bukkit;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryType;

import java.util.*;
import java.util.stream.Collectors;

import static br.com.system.bukkit.SystemUtil.withColor;

@Getter
public class SystemSettings {

  public static Set<UUID> GOD_MODE = new HashSet<>();

  private String nickFormat;

  private String trashTitle;
  private int trashSize;

  private String joinMessage;
  private String quitMessage;
  private String deathMessage;

  private List<String> cancelWeatherChangeWorlds;
  private List<InventoryType> cancelOpenContainerTypes;

  private boolean cancelVoidDamage;
  private String warpToTeleportWhenVoidDamage;

  private boolean cancelBedEnter;

  private List<String> blockedCommands;

  private boolean cancelIceWater;

  private List<Material> cancelRecipes;

  private boolean cancelCreatePortal;
  private boolean cancelMeltSnow;
  private boolean cancelExplodeItems;

  private boolean cancelDoubleLogin;
  private String kickDoubleLoginMessage;

  private boolean cancelBreakFarmJumping;

  /* Kit Settings */
  private boolean dropIfFull;

  public void resolve(SystemPlugin system) {
    FileConfiguration configuration = system.getConfig();

    this.nickFormat = configuration.getString("nick-format", "%player-name%");

    this.trashTitle = withColor(configuration.getString("trash.title", "Lixeira"));
    this.trashSize = configuration.getInt("trash.size", 6);

    this.joinMessage = withColor(configuration.getString("events-settings.join-message", ""));
    this.quitMessage = withColor(configuration.getString("events-settings.quit-message", ""));
    this.deathMessage = withColor(configuration.getString("events-settings.death-message", ""));

    this.cancelWeatherChangeWorlds = configuration.getStringList("events-settings.weather-change.cancel-worlds");

    this.cancelOpenContainerTypes = configuration.getStringList("events-settings.cancel-open-container-types")
     .stream()
     .map(String::toUpperCase)
     .map(InventoryType::valueOf)
     .collect(Collectors.toList());

    this.cancelVoidDamage = configuration.getBoolean("events-settings.void-damage.cancel", false);
    this.warpToTeleportWhenVoidDamage = configuration.getString("events-settings.void-damage.teleport-to-warp", "");

    this.cancelBedEnter = configuration.getBoolean("events-settings.cancel-bed-enter", false);

    this.blockedCommands = configuration.getStringList("events-settings.blocked-commands");

    this.cancelIceWater = configuration.getBoolean("events-settings.cancel-ice-water", false);

    this.cancelRecipes = configuration.getStringList("events-settings.cancel-recipes")
     .stream()
     .map(String::toUpperCase)
     .map(Material::valueOf)
     .collect(Collectors.toList());

    this.cancelCreatePortal = configuration.getBoolean("events-settings.cancel-create-portal", false);
    this.cancelMeltSnow = configuration.getBoolean("events-settings.cancel-melt-snow", false);
    this.cancelExplodeItems = configuration.getBoolean("events-settings.cancel-explode-items", false);

    this.cancelDoubleLogin = configuration.getBoolean("events-settings.double-login.cancel", false);
    this.kickDoubleLoginMessage = withColor(configuration.getString("events-settings.double-login.kick-message", ""));

    this.cancelBreakFarmJumping = configuration.getBoolean("events-settings.cancel-break-farm-jumping", false);

    this.dropIfFull = configuration.getBoolean("kit-settings.drop-if-full", true);
  }
}
