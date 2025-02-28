package br.com.system.bukkit.repository;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.bukkit.resource.Tasks;
import br.com.system.core.data.Repository;
import br.com.system.core.model.user.User;
import br.com.system.core.model.user.kit.UserKit;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("all")
@RequiredArgsConstructor
public class UserKitRepository implements Repository {

  private final SystemPlugin plugin;

  public List<UserKit> load(UUID id) {
    try {
      Connection connection = plugin.getDatabaseService()
       .getDatabaseProvider()
       .getConnection();

      try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM system_users_kits WHERE id = ?")) {
        ps.setString(1, id.toString());
        try(ResultSet rs = ps.executeQuery()) {
          List<UserKit> kits = new ArrayList<>();
          while(rs.next()) {
            kits.add(resolveUserKit(rs));
          }
          return kits;
        }
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ArrayList<>();
    }
  }

  public void create(User user, UserKit kit) {
    Tasks.async(() -> {
      try {
        Connection connection = plugin.getDatabaseService()
         .getDatabaseProvider()
         .getConnection();

        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO system_users_kits (id, kit_name, pickup_count, last_pickup) VALUES (?,?,?,?)")) {
          ps.setString(1, user.getId().toString());
          ps.setString(2, kit.getKitName());
          ps.setInt(3, kit.getPickupCount());
          ps.setLong(4, kit.getLastPickup());
          ps.executeUpdate();
        }
      } catch (SQLException exception) {
        exception.printStackTrace();
      }
    });
  }

  public void update(User user, UserKit kit) {
    Tasks.async(() -> {
      try {
        Connection connection = plugin.getDatabaseService()
         .getDatabaseProvider()
         .getConnection();

        try (PreparedStatement ps = connection.prepareStatement("UPDATE system_users_kits SET pickup_count = ?, last_pickup = ? WHERE kit_name = ? AND id = ?")) {
          ps.setInt(1, kit.getPickupCount());
          ps.setLong(2, kit.getLastPickup());
          ps.setString(3, kit.getKitName());
          ps.setString(4, user.getId().toString());
          ps.executeUpdate();
        }
      } catch (SQLException exception) {
        exception.printStackTrace();
      }
    });
  }

  @Override
  public void createNonExistentTable() {
    try {
      Connection connection = plugin.getDatabaseService()
       .getDatabaseProvider()
       .getConnection();

      try(Statement statement = connection.createStatement()) {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS system_users_kits (" +
         "id CHAR(36) NOT NULL, " +
         "kit_name VARCHAR(32) NOT NULL, " +
         "pickup_count INTEGER NOT NULL DEFAULT 0, " +
         "last_pickup LONG NOT NULL DEFAULT 0" +
         ")");
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }

  @SneakyThrows
  private UserKit resolveUserKit(ResultSet rs) {
    String kitName = rs.getString("kit_name");
    int pickupCount = rs.getInt("pickup_count");
    long lastPickup = rs.getLong("last_pickup");

    return new UserKit(kitName, pickupCount, lastPickup);
  }
}
