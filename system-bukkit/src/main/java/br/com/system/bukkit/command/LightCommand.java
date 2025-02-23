package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LightCommand extends BaseCommand {

  private final SystemPlugin system;

  public LightCommand(CommandInfo info, SystemPlugin system) {
    super(info);
    this.system = system;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (args.length > 0) {
      Player target = Bukkit.getPlayer(args[0]);

      if (target == null) {
        system.getExecutor().msg(sender, "player-not-found",
         "%target-name%", args[0]
        );
        return;
      }

      String path = "commands.light.target-light-";

      if (target.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
        target.removePotionEffect(PotionEffectType.NIGHT_VISION);
        path += "off";
      } else {
        target.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1));
        path += "on";
      }

      if (args.length == 1 || !args[1].equalsIgnoreCase("-s")) {
        system.getExecutor().msg(target, path, "%player-name%", sender.getName());
      }

      system.getExecutor().msg(sender, path + "-self");
      return;
    }

    if (sender instanceof Player) {
      Player player = (Player) sender;

      if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        system.getExecutor().msg(sender, "commands.light.light-off");
        return;
      }

      player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1));
      system.getExecutor().msg(sender, "commands.light.light-on");
      return;
    }

    system.getExecutor().msg(sender, "commands.light.usage");
  }
}
