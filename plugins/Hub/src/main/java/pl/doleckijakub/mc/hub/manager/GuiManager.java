package pl.doleckijakub.mc.hub.manager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.doleckijakub.mc.util.builder.GuiBuilder;
import pl.doleckijakub.mc.util.builder.ItemStackBuilder;

public class GuiManager {

    public static final ItemStack ITEM_SPLEEF_SELECTOR = new ItemStackBuilder(Material.DIAMOND_SHOVEL)
            .displayName(Component.text("Spleef", NamedTextColor.AQUA))
            .build();

    public static final Inventory GUI_SERVER_SELECTOR = new GuiBuilder(3, Component.text("Server selector"))
            .withItemAt(1, 4, ITEM_SPLEEF_SELECTOR, e -> e.getWhoClicked().sendMessage("test"))
            .withItemAt(2, 8, ITEM_SPLEEF_SELECTOR, e -> e.getWhoClicked().sendMessage("testing"))
            .build();

}
