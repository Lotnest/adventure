package dev.lotnest.adventure.common.listener;

import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.jetbrains.annotations.NotNull;

public class ArrowHitRemover implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onArrowHit(@NotNull ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow arrow) {
            arrow.remove();
        }
    }

}
