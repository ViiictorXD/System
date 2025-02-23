package br.com.system.bukkit.database;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum DatabaseType {

  SQLITE("SQLite"),
  MYSQL("MySQL");

  private final String name;

  public static DatabaseType getType(String str) {
    return Arrays.stream(values())
     .filter(type -> type.toString().equalsIgnoreCase(str))
     .findAny()
     .orElse(SQLITE);
  }
}
