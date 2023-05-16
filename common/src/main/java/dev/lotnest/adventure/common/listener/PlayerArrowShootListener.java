package dev.lotnest.adventure.common.listener;

import dev.lotnest.adventure.common.player.task.PlayerArrowAdditionTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public record PlayerArrowShootListener(@NotNull Plugin plugin) implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerArrowShoot(@NotNull ProjectileLaunchEvent event) {
        if (event.getEntityType() != EntityType.ARROW) {
            return;
        }

        if (!(event.getEntity().getShooter() instanceof Player player)) {
            return;
        }

        Arrow arrow = (Arrow) event.getEntity();
        Bukkit.getScheduler().runTaskLater(plugin, () -> new PlayerArrowAdditionTask(player, arrow).run(), 140L);
    }

}
