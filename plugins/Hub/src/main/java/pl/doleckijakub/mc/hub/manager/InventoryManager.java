package pl.doleckijakub.mc.hub.manager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import pl.doleckijakub.mc.util.builder.ItemStackBuilder;

public class InventoryManager {

    public static final ItemStack SERVER_SELECTOR = new ItemStackBuilder(Material.COMPASS)
            .displayName(
                    Component.text("Server Selector", NamedTextColor.GOLD)
                            .append(Component.text(" (Right click)", NamedTextColor.GRAY))
            )
            .build();

    public void setDefaultInventory(Player player) {
        PlayerInventory inventory = player.getInventory();
        inventory.clear();

        inventory.setItem(0, SERVER_SELECTOR);
    }

}
