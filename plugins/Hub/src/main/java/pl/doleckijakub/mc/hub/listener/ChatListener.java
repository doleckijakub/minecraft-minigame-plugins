package pl.doleckijakub.mc.hub.listener;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import lombok.AllArgsConstructor;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import pl.doleckijakub.mc.hub.Hub;
import pl.doleckijakub.mc.util.model.Rank;

@AllArgsConstructor
public class ChatListener implements Listener, ChatRenderer {

    private Hub plugin;

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        event.renderer(this);
    }

    @Override
    public @NotNull Component render(@NotNull Player player, @NotNull Component username, @NotNull Component message, @NotNull Audience viewer) {
        Rank rank = plugin.getRankManager().getRank(player);

        return Rank.getPlayerHandle(player, rank)
                .append(Component.text(": ").color(NamedTextColor.WHITE))
                .append(message.color(NamedTextColor.GRAY));
    }

}