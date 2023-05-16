package dev.lotnest.adventure.common.listener.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface MenuHandler extends Listener {

    @NotNull
    String getInventoryTitle();

    void openInventory(@NotNull Player player);

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void onMenuClick(@NotNull InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {
            return;
        }

        if (event.getCurrentItem() == null) {
            return;
        }

        if (!event.getView().getTitle().equals(getInventoryTitle())) {
            return;
        }

        handleMenuClick(event, event.getCurrentItem());
    }

    void handleMenuClick(@NotNull InventoryClickEvent event, @NotNull ItemStack item);

}
