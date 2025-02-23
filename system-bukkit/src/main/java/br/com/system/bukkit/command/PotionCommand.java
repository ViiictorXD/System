package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemUtil;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import com.cryptomorin.xseries.XMaterial;
import br.com.system.bukkit.SystemPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionCommand extends BaseCommand {

  private final SystemPlugin system;

  public PotionCommand(CommandInfo info, SystemPlugin system) {
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

    if (args.length != 3) {
      system.getExecutor().msg(sender, "commands.potion.usage");
      return;
    }

    ItemStack itemInHand = player.getItemInHand();
    if (itemInHand == null || !XMaterial.matchXMaterial(itemInHand.getType()).name().contains("POTION")) {
      system.getExecutor().msg(sender, "commands.potion.not-potion");
      return;
    }

    PotionEffectType type = PotionEffectType.getByName(args[0]);
    if (type == null) {
      system.getExecutor().msg(sender, "commands.potion.not-found");
      return;
    }

    Integer duration = SystemUtil.asInt(args[1]);
    if (duration == null) {
      system.getExecutor().msg(sender, "commands.potion.invalid-duration");
      return;
    }

    Integer amplifier = SystemUtil.asInt(args[2]);
    if (amplifier == null) {
      system.getExecutor().msg(sender, "commands.potion.invalid-amplifier");
      return;
    }

    PotionMeta potionMeta = (PotionMeta) itemInHand.getItemMeta();
    potionMeta.addCustomEffect(new PotionEffect(type, duration, amplifier), true);

    itemInHand.setItemMeta(potionMeta);

    system.getExecutor().msg(sender, "commands.potion.applied");
  }
}
