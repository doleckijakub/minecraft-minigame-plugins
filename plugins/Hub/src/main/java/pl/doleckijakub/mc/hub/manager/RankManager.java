package pl.doleckijakub.mc.hub.manager;

import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.doleckijakub.mc.hub.Hub;
import pl.doleckijakub.mc.util.model.Rank;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@AllArgsConstructor
public class RankManager {

    private Hub plugin;

    public @Nullable Rank getRank(@NotNull Player player) {
        return getRank(player.getUniqueId());
    }

    public @Nullable Rank getRank(@NotNull UUID uuid) {
        String sql = "SELECT rank FROM player_ranks WHERE uuid = ?";

        try(PreparedStatement stmt = plugin.getDbConnection().prepareStatement(sql)) {
            stmt.setObject(1, uuid);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return Rank.valueOf(rs.getString("rank"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void setRank(Player player, Rank rank) {
        setRank(player.getUniqueId(), rank);
    }

    public void setRank(@NotNull UUID uuid, @Nullable Rank rank) {
        String sql = rank == null
                ? "DELETE FROM player_ranks WHERE uuid = ?"
                : "INSERT INTO player_ranks (uuid, rank) VALUES (?, ?::rank_t) " +
                    "ON CONFLICT (uuid) DO UPDATE SET rank = EXCLUDED.rank";

        try(PreparedStatement stmt = plugin.getDbConnection().prepareStatement(sql)) {
            stmt.setObject(1, uuid);
            if (rank != null) stmt.setString(2, rank.name());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        update(Bukkit.getPlayer(uuid));
    }

    public void update(@Nullable Player player) {
        if (player == null) return;

        Component playerHandle = Rank.getPlayerHandle(player, getRank(player));

        player.displayName(playerHandle);
        player.playerListName(playerHandle);
    }

}
