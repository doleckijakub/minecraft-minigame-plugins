package pl.doleckijakub.mc.util.builder;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

public class GuiBuilder {

    private static NamespacedKey HANDLER_KEY;
    private static Map<UUID, Consumer<InventoryClickEvent>> HANDLERS = new HashMap<>();

    private final int rows;
    private final Inventory inventory;

    public static void init(Plugin plugin) {
        HANDLER_KEY = new NamespacedKey(plugin, "gui_builder_click_handler");

        plugin.getServer().getPluginManager().registerEvents(new Listener() {

            @EventHandler
            public void onInventoryClick(InventoryClickEvent event) {
                ItemStack item = event.getCurrentItem();
                if (item == null || !item.hasItemMeta()) return;

                ItemMeta meta = item.getItemMeta();
                PersistentDataContainer container = meta.getPersistentDataContainer();

                if (container.has(HANDLER_KEY, PersistentDataType.STRING)) {
                    event.setCancelled(true);

                    UUID uuid = UUID.fromString(Objects.requireNonNull(container.get(HANDLER_KEY, PersistentDataType.STRING)));
                    Consumer<InventoryClickEvent> handler = HANDLERS.get(uuid);
                    if (handler != null) {
                        handler.accept(event);
                    }
                }
            }

        }, plugin);
    }

    public GuiBuilder(int rows, Component title) {
        if (rows < 1 || rows > 6) {
            throw new IllegalArgumentException("Inventory rows must be between 1 and 6");
        }

        this.rows = rows;
        this.inventory = Bukkit.createInventory(null, 9 * rows, title);
    }

    public GuiBuilder withItemAt(int row, int column, @NotNull ItemStack item, @Nullable Consumer<InventoryClickEvent> eventConsumer) {
        if (column < 0 || column > 8) {
            throw new IllegalArgumentException("Column must be between 0 and 8");
        }
        if (row < 0 || row > rows - 1) {
            throw new IllegalArgumentException("Row out of bounds");
        }

        int idx = row * 9 + column;

        if (eventConsumer != null) {
            UUID uuid = UUID.randomUUID();
            HANDLERS.put(uuid, eventConsumer);

            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.getPersistentDataContainer().set(HANDLER_KEY, PersistentDataType.STRING, uuid.toString());
            item.setItemMeta(itemMeta);
        }

        inventory.setItem(idx, item);

        return this;
    }

    public Inventory build() {
        return inventory;
    }

}
