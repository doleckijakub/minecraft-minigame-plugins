package pl.doleckijakub.mc.hub.listener;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import pl.doleckijakub.mc.hub.Hub;
import pl.doleckijakub.mc.hub.manager.GuiManager;
import pl.doleckijakub.mc.hub.manager.InventoryManager;

@AllArgsConstructor
public class InventoryListener implements Listener {

    private Hub plugin;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        event.setCancelled(true);

        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();

        if (InventoryManager.SERVER_SELECTOR.equals(itemStack) && event.getAction().toString().startsWith("RIGHT_CLICK_")) {
            player.openInventory(GuiManager.GUI_SERVER_SELECTOR);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        event.setCancelled(true);
    }

}
