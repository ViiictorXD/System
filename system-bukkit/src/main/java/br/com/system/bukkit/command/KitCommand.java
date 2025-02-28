package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.core.PlayerUtil;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import br.com.system.core.format.time.TimeFormatter;
import br.com.system.core.model.kit.Kit;
import br.com.system.core.model.user.User;
import br.com.system.core.model.user.kit.UserKit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class KitCommand extends BaseCommand {

  private final SystemPlugin plugin;

  public KitCommand(CommandInfo info, SystemPlugin plugin) {
    super(info);
    this.plugin = plugin;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof Player)) {
      plugin.getExecutor().msg(sender, "commands.only-players");
      return;
    }

    if (args.length == 0) {
      plugin.getExecutor().msg(sender, "commands.kit.usage");
      return;
    }

    String paramKitName = args[0];
    if (!plugin.getKitManager().anyMatch(kit -> kit.getName().equalsIgnoreCase(paramKitName))) {
      plugin.getExecutor().msg(sender, "commands.kit.not-found",
       "%kit-name%", paramKitName
      );
      return;
    }

    Kit kit = plugin.getKitManager().getKitByName(paramKitName);

    if (!sender.hasPermission(kit.getPermission())) {
      plugin.getExecutor().msg(sender, "commands.kit.no-permission",
       "%kit-name%", kit.getName(),
       "%kit-permission%", kit.getPermission()
      );
      return;
    }

    Player player = (Player) sender;

    User user = plugin.getUserManager().getUserById(player.getUniqueId());
    UserKit userKit = user.getUserKit(kit.getName());

    if (userKit == null) {
      user.addUserKit(userKit = new UserKit(kit.getName(), 0, 0L));
    }

    if (!player.hasPermission("system.kit.delay.bypass")
     && userKit.getNextPickup(kit.getDelay()) > System.currentTimeMillis()) {
      plugin.getExecutor().msg(player, "commands.kit.in-delay",
       "%kit-delay%", TimeFormatter.format(userKit.getNextPickup(kit.getDelay()) - System.currentTimeMillis())
      );
      return;
    }

    if (!player.hasPermission("system.kit.max.bypass")
     && kit.getMaxPickups() > 0 && userKit.getPickupCount() >= kit.getMaxPickups()) {
      plugin.getExecutor().msg(player, "commands.kit.max-reached",
       "%kit-max-pickups%", kit.getMaxPickups()
      );
      return;
    }

    if (!plugin.getSettings().isDropIfFull() && PlayerUtil.getPlayerInventorySpace(player) < kit.getKitItemsCount()) {
      plugin.getExecutor().msg(player, "commands.kit.space-enough");
      return;
    }

    userKit.setLastPickup(System.currentTimeMillis());
    userKit.setPickupCount(userKit.getPickupCount() + 1);

    if (userKit.getPickupCount() == 1) plugin.getUserKitRepository().create(user, userKit);
    else plugin.getUserKitRepository().update(user, userKit);

    HashMap<Integer, ItemStack> remainingItemMap = player.getInventory().addItem(kit.getItemStacks());
    if (!remainingItemMap.isEmpty()) {
      for (ItemStack item : remainingItemMap.values()) {
        player.getWorld().dropItemNaturally(player.getLocation(), item);
      }

      plugin.getExecutor().msg(player, "commands.kit.dropped");
    }

    plugin.getExecutor().msg(sender, "commands.kit.received",
     "%kit-name%", kit.getName()
    );
  }
}
