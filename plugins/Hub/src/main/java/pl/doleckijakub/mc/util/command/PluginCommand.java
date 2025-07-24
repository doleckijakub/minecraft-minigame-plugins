package pl.doleckijakub.mc.util.command;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class PluginCommand implements CommandExecutor {

    @Getter
    private final CommandInfo info;

    protected PluginCommand() {
        this.info = getClass().getDeclaredAnnotation(CommandInfo.class);
        Objects.requireNonNull(info, "CommandInfo annotation required");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String permission = info.permission();

        if (!permission.isEmpty() && !sender.hasPermission(permission)) {
            sender.sendMessage(Component.text("You don't have permission to execute this command", NamedTextColor.RED));
            return true;
        }

        if (info.requiresPlayer()) {
            if (sender instanceof Player player) {
                execute(player, args);
                return true;
            }

            sender.sendMessage(Component.text("You must be a player to execute this command", NamedTextColor.RED));
            return true;
        }

        execute(sender, args);
        return true;
    }

    public void execute(@NotNull Player player, String[] args) {}
    public void execute(@NotNull CommandSender sender, String[] args) {}

}
