package pl.doleckijakub.mc.util.model;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum Rank {
    OWNER, ADMIN, MOD, MVP, VIP;

    public static @NotNull NamedTextColor getTextColor(@Nullable Rank rank) {
        if (rank == null) return NamedTextColor.GRAY;

        return switch (rank) {
            case OWNER -> NamedTextColor.LIGHT_PURPLE;
            case ADMIN -> NamedTextColor.RED;
            case MOD -> NamedTextColor.DARK_GREEN;
            case MVP -> NamedTextColor.GOLD;
            case VIP -> NamedTextColor.AQUA;
        };
    }

    public static Component getPlayerHandle(Player player, @Nullable Rank rank) {
        Component handle = rank == null
                ? Component.empty()
                : rank.getColorizedRankName()
                    .append(Component.text(" "));

        return handle
                .append(Component.text(player.getName(), Rank.getTextColor(rank)));
    }

    public Component getColorizedRankName() {
        return Component.text("[" + this + "]", getTextColor(this));
    }
}