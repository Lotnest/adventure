package dev.lotnest.adventure.common.listener;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerSelfArrowDamageBlocker implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(@NotNull EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Arrow arrow &&
                (arrow.getShooter() instanceof Player shooter && (shooter.equals(event.getEntity())))) {
            event.setCancelled(true);
        }
    }

}
