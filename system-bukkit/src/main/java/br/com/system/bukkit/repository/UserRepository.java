package br.com.system.bukkit.repository;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.bukkit.resource.Tasks;
import br.com.system.core.serializer.LocationSerializer;
import br.com.system.core.data.Repository;
import br.com.system.core.model.user.User;
import br.com.system.core.model.user.address.Address;
import br.com.system.core.model.user.kit.UserKit;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.Location;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("all")
@RequiredArgsConstructor
public class UserRepository implements Repository {

  private final SystemPlugin plugin;

  public CompletableFuture<List<User>> load() {
    return CompletableFuture.supplyAsync(() -> {
      try {
        Connection connection = plugin.getDatabaseService()
         .getDatabaseProvider()
         .getConnection();

        try (
         PreparedStatement ps = connection.prepareStatement("SELECT * FROM system_users");
         ResultSet rs = ps.executeQuery();
        ) {
          List<User> users = new ArrayList<>();
          while (rs.next()) {
            users.add(resolverUser(rs));
          }
          return users;
        }
      } catch (SQLException exception) {
        exception.printStackTrace();
      }
      return new ArrayList<>();
    });
  }

  public void create(User user) {
    Tasks.async(() -> {
      try {
        Connection connection = plugin.getDatabaseService()
         .getDatabaseProvider()
         .getConnection();

        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO system_users (id, name, display, spawn_location, last_location, first_at, last_at) VALUES (?,?,?,?,?,?,?)")) {
          ps.setString(1, user.getId().toString());
          ps.setString(2, user.getName());
          ps.setString(3, user.getDisplay());
          ps.setString(4, LocationSerializer.serialize(user.getSpawnLocation()));
          ps.setString(5, LocationSerializer.serialize(user.getLastLocation()));
          ps.setTimestamp(6, Timestamp.from(user.getFirstAt()));
          ps.setTimestamp(7, Timestamp.from(user.getLastAt()));
          ps.executeUpdate();
        }
      } catch (SQLException exception) {
        exception.printStackTrace();
      }
    });
  }

  public void save(User user) {
    Tasks.async(() -> {
      try {
        Connection connection = plugin.getDatabaseService()
         .getDatabaseProvider()
         .getConnection();

        try (PreparedStatement ps = connection.prepareStatement("UPDATE system_users SET display = ?, spawn_location = ?, last_location = ?, last_at = ? WHERE id = ?")) {
          ps.setString(1, user.getDisplay());
          ps.setString(2, LocationSerializer.serialize(user.getSpawnLocation()));
          ps.setString(3, LocationSerializer.serialize(user.getLastLocation()));
          ps.setTimestamp(4, Timestamp.from(user.getLastAt()));
          ps.setString(5, user.getId().toString());
          ps.executeUpdate();
        }
      } catch (SQLException exception) {
        exception.printStackTrace();
      }
    });
  }

  public void delete(User user) {
    Tasks.async(() -> {
      try {
        Connection connection = plugin.getDatabaseService()
         .getDatabaseProvider()
         .getConnection();

        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM system_users WHERE id = ?")) {
          ps.setString(1, user.getId().toString());
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

      try (Statement statement = connection.createStatement()) {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS system_users (" +
         "id CHAR(36) NOT NULL, " +
         "name VARCHAR(32) NOT NULL, " +
         "display VARCHAR(64) NOT NULL, " +
         "spawn_location VARCHAR(128) NOT NULL, " +
         "last_location VARCHAR(128) NOT NULL, " +
         "first_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
         "last_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
         "PRIMARY KEY (id)" +
         ")");
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }

  @SneakyThrows
  private User resolverUser(ResultSet rs) {
    UUID id = UUID.fromString(rs.getString("id"));
    String name = rs.getString("name");
    String display = rs.getString("display");

    Location spawnLocation = LocationSerializer.deserialize(rs.getString("spawn_location"));
    Location lastLocation = LocationSerializer.deserialize(rs.getString("last_location"));

    List<Address> addresses = plugin.getAddressRepository().load(id);
    List<UserKit> kits = plugin.getUserKitRepository().load(id);

    Instant firstAt = rs.getTimestamp("first_at").toInstant();
    Instant lastAt = rs.getTimestamp("last_at").toInstant();

    return new User(
     id,
     name,
     display,
     spawnLocation,
     lastLocation,
     addresses,
     firstAt,
     lastAt
    );
  }
}
