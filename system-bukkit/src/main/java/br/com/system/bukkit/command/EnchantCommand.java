package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.bukkit.SystemUtil;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EnchantCommand extends BaseCommand {

  private final SystemPlugin system;

  public EnchantCommand(CommandInfo info, SystemPlugin system) {
    super(info);
    this.system = system;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof Player)) {
      system.getExecutor().msg(sender, "commands.only-players");
      return;
    }


    Player player = (Player) sender;
    ItemStack itemInHand = player.getItemInHand();

    if (itemInHand == null || itemInHand.getType() == Material.AIR) {
      system.getExecutor().msg(sender, "commands.enchant.empty-hand");
      return;
    }

    if (args.length != 2) {
      system.getExecutor().msg(sender, "commands.enchant.usage");
      return;
    }

    Enchantment enchantment = Enchantment.getByName(args[0].toUpperCase());
    if (enchantment == null) {
      system.getExecutor().msg(sender, "commands.enchant.not-found",
       "%enchant-name%", args[0],
       "%enchants%", getAllEnchantmentNames()
      );
      return;
    }

    Integer enchantLevel = SystemUtil.asInt(args[1]);
    if (enchantLevel == null || enchantLevel <= 0) {
      system.getExecutor().msg(sender, "commands.enchant.level");
      return;
    }

    itemInHand.addUnsafeEnchantment(enchantment, enchantLevel);

    system.getExecutor().msg(sender, "commands.enchant.enchanted",
     "%enchant-name%", enchantment.getName(),
     "%enchant-level%", enchantLevel
    );
  }

  public String getAllEnchantmentNames() {
    List<String> enchantmentNames = new ArrayList<>();
    for (Enchantment enchantment : Enchantment.values()) {
      enchantmentNames.add(WordUtils.capitalize(enchantment.getName()));
    }
    return String.join(", ", enchantmentNames.toArray(new String[0]));
  }
}
