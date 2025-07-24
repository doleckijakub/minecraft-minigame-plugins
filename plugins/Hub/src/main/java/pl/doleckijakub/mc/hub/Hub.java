package pl.doleckijakub.mc.hub;

import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import pl.doleckijakub.mc.util.builder.GuiBuilder;
import pl.doleckijakub.mc.util.command.PluginCommand;
import pl.doleckijakub.mc.hub.manager.InventoryManager;
import pl.doleckijakub.mc.hub.manager.RankManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public final class Hub extends JavaPlugin {

    @Getter
    private InventoryManager inventoryManager;

    @Getter
    private final RankManager rankManager;

    @Getter
    private Connection dbConnection;

    public Hub() {
        this.inventoryManager = new InventoryManager();
        this.rankManager = new RankManager(this);

        saveDefaultConfig();
        connectToDatabase();
    }

    @Override
    public void onEnable() throws RuntimeException {
        GuiBuilder.init(this);

        PluginManager pluginManager = getServer().getPluginManager();

        new Reflections(getClass().getPackage().getName() + ".listener").getSubTypesOf(Listener.class).forEach(listenerClass -> {
            try {
                Listener listener = listenerClass.getDeclaredConstructor(Hub.class).newInstance(this);
                pluginManager.registerEvents(listener, this);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });

        new Reflections(getClass().getPackage().getName() + ".command").getSubTypesOf(PluginCommand.class).forEach(commandClass -> {
            try {
                PluginCommand pluginCommand = commandClass.getDeclaredConstructor(Hub.class).newInstance(this);
                getCommand(pluginCommand.getInfo().name()).setExecutor(pluginCommand);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void onDisable() {
        try {
            if (dbConnection != null) dbConnection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void connectToDatabase() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            getLogger().severe("Failed to register PostgreSQL driver: " + e.getMessage());
        }

        try {
            String url = Objects.requireNonNull(getConfig().getString("db.url"));
            String user = Objects.requireNonNull(getConfig().getString("db.user"));
            String pass = Objects.requireNonNull(getConfig().getString("db.password"));

            dbConnection = DriverManager.getConnection(url, user, pass);

            getLogger().info("Connected to the database");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
