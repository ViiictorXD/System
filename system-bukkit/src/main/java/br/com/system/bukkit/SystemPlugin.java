package br.com.system.bukkit;

import br.com.system.bukkit.factory.AddressFactory;
import br.com.system.bukkit.factory.KitFactory;
import br.com.system.bukkit.factory.UserFactory;
import br.com.system.bukkit.manager.ConversationManager;
import br.com.system.bukkit.manager.KitManager;
import br.com.system.bukkit.manager.UserManager;
import br.com.system.bukkit.repository.AddressRepository;
import br.com.system.bukkit.repository.UserKitRepository;
import br.com.system.bukkit.repository.UserRepository;
import br.com.system.bukkit.service.CommandService;
import br.com.system.bukkit.service.DatabaseService;
import br.com.system.bukkit.service.ListenerService;
import br.com.system.core.BukkitNMSVersion;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class SystemPlugin extends JavaPlugin {

  private SystemConfiguration configuration;
  private SystemCommands commands;
  private SystemSettings settings;
  private SystemExecutor executor;

  private UserManager userManager;
  private ConversationManager conversationManager;
  private KitManager kitManager;

  private UserFactory userFactory;
  private AddressFactory addressFactory;
  private KitFactory kitFactory;

  private UserRepository userRepository;
  private AddressRepository addressRepository;
  private UserKitRepository userKitRepository;

  private CommandService commandService;
  private DatabaseService databaseService;
  private ListenerService listenerService;

  @Override
  public void onLoad() {
    if (!getDataFolder().exists() && getDataFolder().mkdirs()) {
      saveResource("commands.yml", false);
      saveResource("config.yml", false);
      saveResource("langs/pt_BR.yml", false);
      saveResource("langs/en_US.yml", false);
    }

    configuration = new SystemConfiguration();
    commands = new SystemCommands();
    settings = new SystemSettings();
    executor = new SystemExecutor(this);

    userManager = new UserManager(this);
    conversationManager = new ConversationManager();
    kitManager = new KitManager();

    userFactory = new UserFactory();
    addressFactory = new AddressFactory();
    kitFactory = new KitFactory();

    userRepository = new UserRepository(this);
    addressRepository = new AddressRepository(this);
    userKitRepository = new UserKitRepository(this);

    commandService = new CommandService(this);
    databaseService = new DatabaseService(this);
    listenerService = new ListenerService(this);
  }

  @Override
  public void onEnable() {
    commands.resolve(this);
    settings.resolve(this);

    configuration.resolve(this);

    commandService.start();
    databaseService.start();
    listenerService.start();

    kitManager.init(this);

    userRepository.createNonExistentTable();
    addressRepository.createNonExistentTable();
    userKitRepository.createNonExistentTable();

    userRepository.load()
     .thenAcceptAsync(loadedUsers -> userManager.addEntities(loadedUsers))
     .thenAcceptAsync(unused -> {
       for (Player onlinePlayer : Bukkit.getOnlinePlayers())
         Bukkit.getPluginManager().callEvent(new PlayerJoinEvent(onlinePlayer, settings.getJoinMessage()));
     });

    getLogger().info("System está pronto para ser usado!");
    getLogger().info("Seu servidor é na versão: " + BukkitNMSVersion.getCurrentVersion().getSimpleName());
  }

  @Override
  public void onDisable() {
    if (databaseService.getDatabaseProvider() != null) {
      databaseService.getDatabaseProvider().closeConnection();
    }
  }

  public static SystemPlugin getInstance() {
    return getPlugin(SystemPlugin.class);
  }
}
