package pl.doleckijakub.mc.util.builder;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemStackBuilder(Material material, int count) {
        this.itemStack = new ItemStack(material, count);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemStackBuilder(Material material) {
        this(material, 1);
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStackBuilder displayName(Component name) {
        itemMeta.displayName(name.decoration(TextDecoration.ITALIC, false)); // TODO: ponder removing italics
        return this;
    }

}
