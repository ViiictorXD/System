package br.com.system.bukkit.database.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public class SqlDatabaseProperty {

  private final Map<String, String> propertyMap;

  public static SqlDatabaseProperty of(FileConfiguration configuration) {
    return new SqlDatabaseProperty(new HashMap<String, String>(){{
      put("host", configuration.getString("database.properties.host"));
      put("database", configuration.getString("database.properties.database"));
      put("username", configuration.getString("database.properties.username"));
      put("password", configuration.getString("database.properties.password"));
      put("port", configuration.getString("database.properties.port"));
    }});
  }


  public String getProperty(String key) {
    return propertyMap.get(key);
  }

  public void addProperty(String key, String value) {
    propertyMap.put(key, value);
  }
}
