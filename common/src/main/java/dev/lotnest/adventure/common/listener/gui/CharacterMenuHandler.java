package dev.lotnest.adventure.common.listener.gui;

import dev.lotnest.adventure.common.item.Items;
import dev.lotnest.adventure.common.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CharacterMenuHandler implements MenuHandler {

    public static final CharacterMenuHandler INSTANCE = new CharacterMenuHandler();
    private static final String INVENTORY_TITLE = Objects.requireNonNull(Items.CHARACTER_MENU.getItemMeta()).getDisplayName();

    private CharacterMenuHandler() {
    }

    @NotNull
    @Override
    public String getInventoryTitle() {
        return INVENTORY_TITLE;
    }

    public void openInventory(@NotNull Player player) {
        Inventory inventory = Bukkit.createInventory(player, 9, INVENTORY_TITLE);
        inventory.setItem(1, Items.CHARACTER_MENU_LEVEL);
        inventory.setItem(3, Items.CHARACTER_MENU_QUEST_BOOK);
        inventory.setItem(5, Items.CHARACTER_MENU_SKILLS);
        inventory.setItem(7, Items.CHARACTER_MENU_SETTINGS);
        player.openInventory(inventory);
    }

    public void handleMenuClick(@NotNull InventoryClickEvent event, @NotNull ItemStack item) {
        if (item.isSimilar(Items.CHARACTER_MENU_SETTINGS)) {
            SettingsMenuHandler.INSTANCE.openInventory((Player) event.getWhoClicked());
            return;
        }

        event.getWhoClicked().sendMessage(ChatColor.RED + "Not implemented yet!");
        event.setCancelled(true);
    }

    @EventHandler
    public void onCharacterMenuItemClick(@NotNull PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (event.getAction() == Action.PHYSICAL) {
            return;
        }

        if (item == null || item.getType().isAir()) {
            return;
        }

        if (!Items.hasSerializedProfile(item)) {
            return;
        }

        if (ItemUtil.isNotEqual(item, Items.CHARACTER_MENU)) {
            return;
        }

        event.setCancelled(true);
        openInventory(event.getPlayer());
    }

}
