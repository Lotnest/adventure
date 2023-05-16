package dev.lotnest.adventure.common.pathfinder.blueorb;

import dev.lotnest.adventure.common.util.EntityUtil;
import fr.skytasul.glowingentities.GlowingEntities;
import lombok.Getter;
import lombok.SneakyThrows;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

@Getter
public class BlueOrb {

    static final Particle.DustOptions BLUE_PARTICLE_DUST = new Particle.DustOptions(Color.BLUE, 1);

    private final Player player;
    private final ServerPlayer nmsPlayer;
    private final Location targetLocation;
    private final Plugin plugin;
    private final GlowingEntities glowingApi;
    private final Location spawnLocation;
    private final Snowball snowball;

    private int taskId;
    private boolean isMoving;

    public BlueOrb(@NotNull Player player, @NotNull Location targetLocation, @NotNull Plugin plugin,
                   @NotNull GlowingEntities glowingApi) {
        this.player = player;
        nmsPlayer = ((CraftPlayer) player).getHandle();
        this.targetLocation = targetLocation;
        this.plugin = plugin;
        this.glowingApi = glowingApi;
        spawnLocation = player.getLocation().add(player.getLocation().getDirection().multiply(3));
        snowball = spawnSnowball();
    }

    @SneakyThrows
    @NotNull
    private Snowball spawnSnowball() {
        Level playerWorldLevel = ((CraftWorld) player.getWorld()).getHandle().getLevel();
        Snowball result = new Snowball(EntityType.SNOWBALL, playerWorldLevel);

        result.setPos(spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ());
        result.setOwner(nmsPlayer);
        result.setNoGravity(true);
        result.setSilent(true);
        result.setInvulnerable(true);
        result.setGlowingTag(true);

        EntityUtil.spawnClientboundEntity(nmsPlayer, result);
        EntityUtil.addClientboundGlowingToEntity(glowingApi, result, player, ChatColor.BLUE);

        return result;
    }

    public void startPathfinding() {
        taskId = new BlueOrbFollowPathGoal(this).runTaskTimerAsynchronously(plugin, 0, 1).getTaskId();
    }

    public void stopPathfinding() {
        if (taskId != 0) {
            Bukkit.getScheduler().cancelTask(taskId);
            EntityUtil.removeClientboundEntity(nmsPlayer, snowball);
        }
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

}
