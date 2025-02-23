package br.com.system.bukkit.listener.prevent;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.bukkit.SystemSettings;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@RequiredArgsConstructor
public class GeneralListener implements Listener {

  private final SystemPlugin plugin;

  @EventHandler
  private void onInventoryOpen(InventoryOpenEvent event) {
    if (event.isCancelled()) return;

    List<InventoryType> inventoryTypes = plugin.getSettings().getCancelOpenContainerTypes();

    if (!event.isCancelled()
     && !inventoryTypes.isEmpty()
     && inventoryTypes.contains(event.getInventory().getType())
     && !event.getPlayer().hasPermission("system.bypass.open.containers")) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  private void onEntityDamage(EntityDamageEvent event) {
    if (event.isCancelled()) return;

    Entity entity = event.getEntity();
    EntityDamageEvent.DamageCause cause = event.getCause();

    SystemSettings settings = plugin.getSettings();

    if (entity.getType() == EntityType.PLAYER && cause == EntityDamageEvent.DamageCause.VOID) {
      if (settings.isCancelVoidDamage()) {
        event.setCancelled(true);
        event.setDamage(0D);
      }

      if (settings.getWarpToTeleportWhenVoidDamage() != null) {
        // todo: teleport player to warp listed in settings
      }
    }
  }

  @EventHandler
  private void onPlayerBedEnter(PlayerBedEnterEvent event) {
    if (event.isCancelled()) return;

    if (plugin.getSettings().isCancelBedEnter()
     && !event.getPlayer().hasPermission("system.bypass.bed.enter")) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  private void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
    if (event.isCancelled()) return;

    Player player = event.getPlayer();
    String command = event.getMessage().split(" ")[0];

    for (String blockedCommand : plugin.getSettings().getBlockedCommands()) {
      if ((command.equalsIgnoreCase(blockedCommand) || (command.split(":").length > 1 && command.split(":")[0].equalsIgnoreCase(blockedCommand)))
       && !player.hasPermission("system.bypass.block.commands")) {
        event.setCancelled(true);
      }
    }
  }

  @EventHandler
  private void onBlockForm(BlockFormEvent event) {
    if (!event.isCancelled()
     && plugin.getSettings().isCancelIceWater()
     && event.getBlock().getType().name().contains("WATER")) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  private void onPrepareItemCraft(PrepareItemCraftEvent event) {
    if (event.getRecipe() != null
     && event.getRecipe().getResult() != null
     && !event.getView().getPlayer().hasPermission("system.bypass.craft.item")) {
      ItemStack itemStack = event.getRecipe().getResult();
      if (plugin.getSettings().getCancelRecipes().contains(itemStack.getType())) {
        event.getInventory().setResult(new ItemStack(Material.AIR));
      }
    }
  }

  @EventHandler
  private void onPortalCreate(PortalCreateEvent event) {
    if (!event.isCancelled()
     && plugin.getSettings().isCancelCreatePortal()) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  private void onBlockFade(BlockFadeEvent event) {
    Block block = event.getBlock();

    if (!event.isCancelled()
     && (block.getType() == Material.SNOW || block.getType() == Material.ICE)
     && plugin.getSettings().isCancelMeltSnow()) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    if (!event.isCancelled()
     && event.getEntity() instanceof Item
     && plugin.getSettings().isCancelExplodeItems()) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  private void onPlayerLogin(PlayerLoginEvent event) {
    if (plugin.getSettings().isCancelDoubleLogin()) {
      Player player = Bukkit.getPlayer(event.getPlayer().getName());
      if (player != null) {
        event.setKickMessage(plugin.getSettings().getKickDoubleLoginMessage());
        event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
      }
    }
  }

  @EventHandler
  private void onPlayerKick(PlayerKickEvent event) {
    if (event.getReason().contains("You logged in from another location")
     && plugin.getSettings().isCancelDoubleLogin()) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  private void onEntityChangeBlock(EntityChangeBlockEvent event) {
    if (!event.isCancelled()
     && plugin.getSettings().isCancelBreakFarmJumping()) {
      event.setCancelled(true);
    }
  }
}
