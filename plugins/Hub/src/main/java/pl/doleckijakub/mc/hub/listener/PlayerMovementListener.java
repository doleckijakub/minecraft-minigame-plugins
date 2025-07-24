package pl.doleckijakub.mc.hub.listener;

import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import pl.doleckijakub.mc.hub.Hub;

@AllArgsConstructor
public class PlayerMovementListener implements Listener {

    private Hub plugin;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Location destination = event.getTo();
        if (destination.getY() >= -61) return;

        Player player = event.getPlayer();
        World world = player.getWorld();
        Location spawn = world.getSpawnLocation().clone().add(0.5, 0, 0.5);

        Location midpoint = spawn.clone().add(destination).multiply(0.5);
        Vector direction = midpoint.toVector().subtract(destination.toVector()).normalize();

        Vector offset = direction.clone().multiply(-1);
        offset.setY(0);

        Vector velocity = direction.multiply(2);
        velocity.setY(2);

        world.spawnParticle(Particle.CLOUD, destination, 200, 2, 0.5, 2, 0.01);
        world.spawnParticle(Particle.CRIT_MAGIC, destination, 100, 1, 0.3, 1, 0.05);
        world.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.5f, 1.2f);

        player.getLocation().setY(-60);
        player.setVelocity(velocity);
    }

}
