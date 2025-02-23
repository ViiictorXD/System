package br.com.system.bukkit.command;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.core.command.BaseCommand;
import br.com.system.core.command.CommandInfo;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class EditSignCommand extends BaseCommand {

  private final SystemPlugin system;

  public EditSignCommand(CommandInfo info, SystemPlugin system) {
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

    Block targetBlock = player.getTargetBlock((Set<Material>) null, 10);
    if (targetBlock == null || targetBlock.getType().toString().contains("SIGN") || !(targetBlock.getState() instanceof Sign)) {
      system.getExecutor().msg(sender, "commands.editsign.not-found");
      return;
    }

    if (args.length <= 2) {
      system.getExecutor().msg(sender, "commands.editsign.usage");
      return;
    }

    int line = -1;
    try {
      line = Integer.parseInt(args[0]);
    } catch (NumberFormatException ignored) {}

    if (line <= 0) {
      system.getExecutor().msg(sender, "commands.editsign.usage");
      return;
    }

    StringBuilder builder = new StringBuilder();
    for (int index = 1; index < args.length; index++) {
      builder.append(args[index]);
    }

    String fullText = builder.toString();

    Sign sign = (Sign) targetBlock;
    sign.setLine(line, fullText);
    sign.update();

    system.getExecutor().msg(sender, "commands.editsign.edited", "%line%", line, "%text%", fullText);
  }
}
