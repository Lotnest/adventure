package dev.lotnest.adventure.common.listener;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

public class BloodEffectHandler implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(@NotNull EntityDamageEvent event) {
        event.getEntity().getWorld().spawnParticle(Particle.BLOCK_CRACK, event.getEntity().getLocation(),
                10, 0.5, 0.5, 0.5, 0.5, Material.REDSTONE_BLOCK.createBlockData());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDeath(@NotNull EntityDeathEvent event) {
        event.getEntity().getWorld().spawnParticle(Particle.REDSTONE, event.getEntity().getLocation(), 2,
                new Particle.DustOptions(Color.RED, 5));
    }
}
