package dev.lotnest.adventure.common.listener;

import dev.lotnest.adventure.common.character.kit.Kits;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.jetbrains.annotations.NotNull;

public class KitItemDropBlocker implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onKitItemDrop(@NotNull PlayerDropItemEvent event) {
        if (Kits.isItemKit(event.getItemDrop().getItemStack())) {
            event.setCancelled(true);
        }
    }

}
