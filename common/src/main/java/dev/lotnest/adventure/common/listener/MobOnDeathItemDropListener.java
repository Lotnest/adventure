package dev.lotnest.adventure.common.listener;

import dev.lotnest.adventure.common.mob.Mob;
import net.minecraft.world.entity.Entity;
import org.apache.commons.lang.StringUtils;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

public class MobOnDeathItemDropListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMobDeath(@NotNull EntityDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            return;
        }

        if (StringUtils.isBlank(event.getEntity().getCustomName())) {
            return;
        }

        Entity entity = ((CraftEntity) event.getEntity()).getHandle();

        if (!entity.getTags().contains("adventureMob")) {
            return;
        }

        event.getDrops().clear();
        event.getDrops().addAll(((Mob) entity).getDrops());
    }

}
