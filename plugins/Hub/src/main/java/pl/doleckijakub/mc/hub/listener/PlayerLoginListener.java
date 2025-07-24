package pl.doleckijakub.mc.hub.listener;

import com.destroystokyo.paper.profile.PlayerProfile;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.util.Vector;
import pl.doleckijakub.mc.hub.Hub;
import pl.doleckijakub.mc.util.model.Rank;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
public class PlayerLoginListener implements Listener {

    private Hub plugin;

    @EventHandler
    public void onPlayerLogin(AsyncPlayerPreLoginEvent event) {
        PlayerProfile playerProfile = event.getPlayerProfile();
        UUID uuid = playerProfile.getId();
        World world = Objects.requireNonNull(Bukkit.getWorld("world"));

        File playerdata = new File(world.getWorldFolder(), "playerdata/" + uuid + ".dat");
        if (playerdata.exists()) playerdata.delete();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(player.getWorld().getSpawnLocation().clone().add(new Vector(0.5, 0, 0.5)));
        plugin.getInventoryManager().setDefaultInventory(player);
        plugin.getRankManager().update(player);

        Rank rank = plugin.getRankManager().getRank(player);

        TextComponent joinMessage = Component.text("[", NamedTextColor.DARK_GRAY)
                .append(Component.text("+", NamedTextColor.GREEN))
                .append(Component.text("] ", NamedTextColor.DARK_GRAY))
                .append(Rank.getPlayerHandle(player, rank));

        event.joinMessage(joinMessage);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Rank rank = plugin.getRankManager().getRank(player);

        TextComponent joinMessage = Component.text("[", NamedTextColor.DARK_GRAY)
                .append(Component.text("-", NamedTextColor.RED))
                .append(Component.text("] ", NamedTextColor.DARK_GRAY))
                .append(Rank.getPlayerHandle(player, rank));

        event.quitMessage(joinMessage);
    }

}
