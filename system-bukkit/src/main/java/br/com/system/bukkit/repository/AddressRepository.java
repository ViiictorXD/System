package br.com.system.bukkit.repository;

import br.com.system.bukkit.SystemPlugin;
import br.com.system.bukkit.resource.Tasks;
import br.com.system.core.data.Repository;
import br.com.system.core.model.user.User;
import br.com.system.core.model.user.address.Address;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("all")
@RequiredArgsConstructor
public class AddressRepository implements Repository {

  private final SystemPlugin plugin;

  public List<Address> load(UUID id) {
    try {
      Connection connection = plugin.getDatabaseService()
       .getDatabaseProvider()
       .getConnection();

      try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM system_users_addresses WHERE id = ?")) {
        ps.setString(1, id.toString());
        try(ResultSet rs = ps.executeQuery()) {
          List<Address> addresses = new ArrayList<>();
          while(rs.next()) {
            addresses.add(resolveAddress(rs));
          }
          return addresses;
        }
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ArrayList<>();
    }
  }

  public void create(User user, Address address) {
    Tasks.async(() -> {
      try {
        Connection connection = plugin.getDatabaseService()
         .getDatabaseProvider()
         .getConnection();

        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO system_users_addresses (id, address, date) VALUES (?,?,?)")) {
          ps.setString(1, user.getId().toString());
          ps.setString(2, address.getAddress());
          ps.setTimestamp(3, Timestamp.from(address.getDate()));
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
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS system_users_addresses (" +
         "id CHAR(36) NOT NULL, " +
         "address VARCHAR(32) NOT NULL, " +
         "date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP" +
         ")");
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }

  @SneakyThrows
  private Address resolveAddress(ResultSet rs) {
    String address = rs.getString("address");
    Instant date = rs.getTimestamp("date").toInstant();

    return new Address(address, date);
  }
}
