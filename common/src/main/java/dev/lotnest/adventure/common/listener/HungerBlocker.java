package dev.lotnest.adventure.common.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.jetbrains.annotations.NotNull;

public class HungerBlocker implements Listener {

    @EventHandler
    public void onFoodLevelChange(@NotNull FoodLevelChangeEvent event) {
        if (event.getFoodLevel() < event.getEntity().getFoodLevel()) {
            event.setCancelled(true);
            event.getEntity().setFoodLevel(20);
        }
    }

}
