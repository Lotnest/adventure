package dev.lotnest.adventure.common.item;

import com.google.common.collect.Maps;
import dev.lotnest.adventure.common.item.builder.ItemBuilder;
import dev.lotnest.adventure.common.item.builder.SkullItemBuilder;
import dev.lotnest.adventure.common.message.Lores;
import dev.lotnest.adventure.common.util.Base64Constants;
import dev.lotnest.adventure.common.util.Rarity;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

public class Items {

    private static final Map<Player, ItemStack> CHARACTER_MENU_CACHE = Maps.newHashMap();

    public static final ItemStack CHARACTER_MENU = SkullItemBuilder.builder()
            .skullTexture(Base64Constants.QUESTION_MARK_SKULL_TEXTURE)
            .displayName(ChatColor.GOLD + "Character Menu")
            .lore(Lores.CLICK_TO_OPEN)
            .build();
    public static final ItemStack CHARACTER_MENU_LEVEL = ItemBuilder.builder()
            .material(Material.EXPERIENCE_BOTTLE)
            .displayName(org.bukkit.ChatColor.GREEN + "Level")
            .lore(Collections.singletonList(ChatColor.GRAY + "Your level: " + ChatColor.GREEN + "1"))
            .build();
    public static final ItemStack CHARACTER_MENU_QUEST_BOOK = ItemBuilder.builder()
            .material(Material.WRITABLE_BOOK)
            .displayName(ChatColor.YELLOW + "Quests")
            .lore(Lores.CLICK_TO_OPEN)
            .build();
    public static final ItemStack CHARACTER_MENU_SKILLS = ItemBuilder.builder()
            .material(Material.NETHER_STAR)
            .displayName(ChatColor.BLUE + "Skills")
            .lore(Lores.CLICK_TO_OPEN)
            .build();
    public static final ItemStack CHARACTER_MENU_SETTINGS = ItemBuilder.builder()
            .material(Material.COMPARATOR)
            .displayName(ChatColor.RED + "Settings")
            .lore(Lores.CLICK_TO_OPEN)
            .build();

    public static final ItemStack SETTINGS_MENU_SHOW_PARTICLES = ItemBuilder.builder()
            .material(Material.FIREWORK_STAR)
            .displayName(ChatColor.GRAY + "Show particles")
            .lore(Lores.CLICK_TO_TOGGLE)
            .build();
    public static final ItemStack SETTINGS_MENU_SHOW_BLOOD_EFFECTS = ItemBuilder.builder()
            .material(Material.REDSTONE)
            .displayName(ChatColor.RED + "Show blood effects")
            .lore(Lores.CLICK_TO_TOGGLE)
            .build();

    public static final ItemStack ZOMBIE_BRAIN = ItemBuilder.builder()
            .material(Material.ZOMBIE_HEAD)
            .displayName(ChatColor.LIGHT_PURPLE + "Zombie Brain")
            .rarity(Rarity.COMMON)
            .lore(Collections.singletonList(ChatColor.GRAY + "Yikes."))
            .build();

    private Items() {
    }

    @NotNull
    public static ItemStack getCharacterMenu(@NotNull Player player) {
        return CHARACTER_MENU_CACHE.computeIfAbsent(player, p -> {
            ItemStack itemStack = CHARACTER_MENU.clone();
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
            if (skullMeta != null) {
                skullMeta.setOwningPlayer(player);
                itemStack.setItemMeta(skullMeta);
            }
            return itemStack;
        });
    }

    public static boolean hasSerializedProfile(@NotNull ItemStack itemStack) {
        if (!itemStack.hasItemMeta()) {
            return false;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (!(itemMeta instanceof SkullMeta skullMeta)) {
            return false;
        }

        if (skullMeta.getOwningPlayer() == null) {
            return false;
        }

        PlayerProfile playerProfile = skullMeta.getOwningPlayer().getPlayerProfile();
        return !playerProfile.getTextures().isEmpty();
    }

}
