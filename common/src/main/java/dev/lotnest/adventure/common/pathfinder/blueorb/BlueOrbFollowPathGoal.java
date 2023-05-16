package dev.lotnest.adventure.common.pathfinder.blueorb;

import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlueOrbFollowPathGoal extends BukkitRunnable {

    @NotNull
    private final BlueOrb blueOrb;
    private final Location location;
    private final Player player;

    public BlueOrbFollowPathGoal(@NotNull BlueOrb blueOrb) {
        this.blueOrb = blueOrb;
        this.location = blueOrb.getSpawnLocation();
        this.player = blueOrb.getPlayer();
        blueOrb.setMoving(true);
    }

    @Override
    public void run() {
        if (!player.isOnline()) {
            cancel();
            return;
        }

        Location playerLocation = player.getLocation();
        double distanceToOrb = playerLocation.distance(location);

        player.spawnParticle(Particle.REDSTONE, location, 10, 0, 0, 0, 0, BlueOrb.BLUE_PARTICLE_DUST);

        while (blueOrb.isMoving() && distanceToOrb <= 5) {
            playerLocation = player.getLocation();
            distanceToOrb = playerLocation.distance(location);

            if (!Double.isNaN(location.getX()) && !Double.isNaN(location.getY()) && !Double.isNaN(location.getZ())) {
                blueOrb.getSnowball().setPos(location.getX(), location.getY(), location.getZ());

                ClientboundTeleportEntityPacket setEntityPositionPacket = new ClientboundTeleportEntityPacket(blueOrb.getSnowball());
                ((CraftPlayer) player).getHandle().connection.send(setEntityPositionPacket);
            }

            Location next = getNextLocation();
            if (next == null) {
                blueOrb.stopPathfinding();
                return;
            }

            Vector velocity = next.subtract(location).toVector().normalize().multiply(0.5);
            location.add(velocity);
        }
    }


    @Nullable
    private Location getNextLocation() {
        double shortestDistance = Double.MAX_VALUE;
        Location result = null;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (dx == 0 && dy == 0 && dz == 0) {
                        continue;
                    }

                    Location neighbor = location.clone().add(dx, dy, dz);

                    if (!neighbor.getBlock().isPassable()) {
                        continue;
                    }

                    double distance = neighbor.distance(blueOrb.getTargetLocation());

                    if (distance < shortestDistance) {
                        shortestDistance = distance;
                        result = neighbor;
                    }
                }
            }
        }

        return result;
    }

}
