package br.com.system.bukkit.types;

import lombok.Getter;
import org.bukkit.GameMode;

import java.util.Arrays;

@Getter
public enum SystemGameMode {

  SURVIVAL(GameMode.SURVIVAL, "survival", "sobrevivencia", "s", "0"),
  CREATIVE(GameMode.CREATIVE, "creative", "criativo", "c", "1"),
  ADVENTURE(GameMode.ADVENTURE, "adventure", "aventura", "a", "2"),
  SPECTATOR(GameMode.SPECTATOR, "spectator", "espectador", "e", "3");

  private final GameMode gameMode;
  private final String[] aliases;

  SystemGameMode(GameMode gameMode, String... aliases) {
    this.gameMode = gameMode;
    this.aliases = aliases;
  }

  public static SystemGameMode resolve(String input) {
    return Arrays.stream(values())
     .filter(mode -> mode.aliases != null && Arrays.stream(mode.aliases).anyMatch(alias -> alias.equalsIgnoreCase(input)))
     .findAny()
     .orElse(null);
  }
}
