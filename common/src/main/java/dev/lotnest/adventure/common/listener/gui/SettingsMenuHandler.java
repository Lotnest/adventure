package dev.lotnest.adventure.common.listener.gui;

import dev.lotnest.adventure.common.item.Items;
import dev.lotnest.adventure.common.item.builder.ItemBuilder;
import dev.lotnest.adventure.common.message.Lores;
import dev.lotnest.adventure.common.player.AdventurePlayerFactory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SettingsMenuHandler implements MenuHandler {

    public static final SettingsMenuHandler INSTANCE = new SettingsMenuHandler();
    private static final String INVENTORY_TITLE = Objects.requireNonNull(Items.CHARACTER_MENU_SETTINGS.getItemMeta()).getDisplayName();

    private SettingsMenuHandler() {
    }

    @Override
    @NotNull
    public String getInventoryTitle() {
        return INVENTORY_TITLE;
    }

    @Override
    public void openInventory(@NotNull Player player) {
        AdventurePlayerFactory.getAdventurePlayer(player).ifPresent(adventurePlayer -> {
            Inventory inventory = Bukkit.createInventory(player, 54, INVENTORY_TITLE);

            inventory.setItem(10, Items.SETTINGS_MENU_SHOW_PARTICLES);
            inventory.setItem(19, ItemBuilder.builder()
                    .material(adventurePlayer.getSettings().getShowParticlesValueAsMaterial())
                    .displayName(adventurePlayer.getSettings().getShowParticlesValueAsFormattedString())
                    .lore(Lores.CLICK_TO_TOGGLE)
                    .build());

            inventory.setItem(16, Items.SETTINGS_MENU_SHOW_BLOOD_EFFECTS);
            inventory.setItem(25, ItemBuilder.builder()
                    .material(adventurePlayer.getSettings().getShowBloodEffectsValueAsMaterial())
                    .displayName(adventurePlayer.getSettings().getShowBloodEffectsValueAsFormattedString())
                    .lore(Lores.CLICK_TO_TOGGLE)
                    .build());

            player.openInventory(inventory);
        });
    }

    @Override
    public void handleMenuClick(@NotNull InventoryClickEvent event, @NotNull ItemStack item) {
        event.getWhoClicked().sendMessage(ChatColor.RED + "Not implemented yet!");
        event.setCancelled(true);
    }

}
