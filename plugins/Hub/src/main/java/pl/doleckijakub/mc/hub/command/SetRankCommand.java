package pl.doleckijakub.mc.hub.command;

import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.doleckijakub.mc.hub.Hub;
import pl.doleckijakub.mc.util.command.CommandInfo;
import pl.doleckijakub.mc.util.command.PluginCommand;
import pl.doleckijakub.mc.util.model.Rank;

import java.util.Arrays;

@AllArgsConstructor
@CommandInfo(name = "setrank", permission = "op", requiresPlayer = false)
public class SetRankCommand extends PluginCommand {

    private Hub plugin;

    private void usage(CommandSender sender) {
        sender.sendMessage(Component.text("Usage: /" + getInfo().name() + " <username> <rank|null>", NamedTextColor.RED));
    }

    @Override
    public void execute(@NotNull CommandSender sender, String[] args) {
        if (args.length != 2) {
            usage(sender);
            return;
        }

        String username = args[0];
        String rankName = args[1];

        Player player = Bukkit.getPlayer(username);
        if (player == null) {
            sender.sendMessage(Component.text("Player " + username + "not found", NamedTextColor.RED));
            return;
        }

        if (rankName.equalsIgnoreCase("null")) {
            plugin.getRankManager().setRank(player, null);
            sender.sendMessage(
                    Component.text("Player ", NamedTextColor.GREEN)
                            .append(Component.text(player.getName(), NamedTextColor.GOLD))
                            .append(Component.text(" has had their rank removed", NamedTextColor.GREEN))
            );
            player.sendMessage(
                    Component.text("Your rank has been removed", NamedTextColor.AQUA)
            );
            return;
        }

        Rank rank = Arrays.stream(Rank.values()).filter(r -> r.toString().equalsIgnoreCase(rankName)).findFirst().orElse(null);
        if (rank == null) {
            sender.sendMessage(Component.text("Rank " + rankName + "not found", NamedTextColor.RED));
            sender.sendMessage(Component.text("Availible ranks:", NamedTextColor.YELLOW));

            for (Rank r : Rank.values()) {
                sender.sendMessage(
                        Component.text(" - ", NamedTextColor.YELLOW)
                                .append(r.getColorizedRankName())
                );
            }

            return;
        }

        plugin.getRankManager().setRank(player, rank);
        sender.sendMessage(
                Component.text("Player ", NamedTextColor.GREEN)
                        .append(Component.text(player.getName(), NamedTextColor.GOLD))
                        .append(Component.text("'s rank has been changed to ", NamedTextColor.GREEN))
                        .append(rank.getColorizedRankName())
        );
        player.sendMessage(
                Component.text("Your rank has been chnaged to ", NamedTextColor.AQUA)
                        .append(rank.getColorizedRankName())
        );
    }

}
