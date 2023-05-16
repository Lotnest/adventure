package dev.lotnest.adventure.common.character.kit.factory;

import dev.lotnest.adventure.common.character.kit.Kit;
import dev.lotnest.adventure.common.character.race.RaceType;
import dev.lotnest.adventure.common.item.builder.ItemBuilder;
import dev.lotnest.adventure.common.item.builder.PotionItemBuilder;
import dev.lotnest.adventure.common.message.Unicodes;
import dev.lotnest.adventure.common.util.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class HumanKitFactory extends KitFactory {

    private static final String HUMAN_RACE_DISPLAY_NAME = RaceType.HUMAN.getDisplayName();

    private HumanKitFactory() {
    }

    public static ItemStack @NotNull [] createArmor() {
        ItemStack boots = ItemBuilder.builder()
                .material(Material.IRON_BOOTS)
                .displayName(HUMAN_RACE_DISPLAY_NAME + " Boots")
                .rarity(Rarity.COMMON)
                .isUnbreakable(true)
                .itemFlags(Set.of(ItemFlag.HIDE_UNBREAKABLE))
                .enchantments(Map.of(Enchantment.PROTECTION_ENVIRONMENTAL, 2))
                .build();

        ItemStack leggings = ItemBuilder.builder()
                .material(Material.IRON_LEGGINGS)
                .displayName(HUMAN_RACE_DISPLAY_NAME + " Leggings")
                .rarity(Rarity.COMMON)
                .isUnbreakable(true)
                .itemFlags(Set.of(ItemFlag.HIDE_UNBREAKABLE))
                .enchantments(Map.of(Enchantment.PROTECTION_ENVIRONMENTAL, 2))
                .build();

        ItemStack chestplate = ItemBuilder.builder()
                .material(Material.IRON_CHESTPLATE)
                .displayName(HUMAN_RACE_DISPLAY_NAME + " Chestplate")
                .rarity(Rarity.COMMON)
                .isUnbreakable(true)
                .itemFlags(Set.of(ItemFlag.HIDE_UNBREAKABLE))
                .enchantments(Map.of(Enchantment.PROTECTION_ENVIRONMENTAL, 2))
                .build();

        ItemStack helmet = ItemBuilder.builder()
                .material(Material.IRON_HELMET)
                .displayName(HUMAN_RACE_DISPLAY_NAME + " Helmet")
                .rarity(Rarity.COMMON)
                .isUnbreakable(true)
                .itemFlags(Set.of(ItemFlag.HIDE_UNBREAKABLE))
                .enchantments(Map.of(Enchantment.PROTECTION_ENVIRONMENTAL, 2))
                .build();

        return new ItemStack[]{boots, leggings, chestplate, helmet};
    }

    public static ItemStack @NotNull [] createItems() {
        ItemStack[] result = new ItemStack[36];

        ItemStack sword = ItemBuilder.builder()
                .material(Material.IRON_SWORD)
                .displayName(HUMAN_RACE_DISPLAY_NAME + " Sword")
                .rarity(Rarity.COMMON)
                .isUnbreakable(true)
                .defaultItemFlags()
                .enchantments(Map.of(Enchantment.DAMAGE_ALL, 3, Enchantment.SWEEPING_EDGE, 2))
                .itemFlags(Set.of(ItemFlag.HIDE_UNBREAKABLE))
                .build();
        result[0] = sword;

        ItemStack bow = ItemBuilder.builder()
                .material(Material.BOW)
                .displayName(HUMAN_RACE_DISPLAY_NAME + " Bow")
                .rarity(Rarity.COMMON)
                .isUnbreakable(true)
                .itemFlags(Set.of(ItemFlag.HIDE_UNBREAKABLE))
                .enchantments(Map.of(Enchantment.ARROW_DAMAGE, 2, Enchantment.ARROW_INFINITE, 7, Enchantment.ARROW_FIRE, 1))
                .build();
        result[1] = bow;

        ItemStack arrow = PotionItemBuilder.builder()
                .potionMaterial(PotionItemBuilder.PotionMaterial.TIPPED_ARROW)
                .color(Color.GRAY)
                .amount(10)
                .displayName(ChatColor.GRAY + "Flint Arrow")
                .rarity(Rarity.COMMON)
                .isUnbreakable(true)
                .defaultItemFlags()
                .build();
        result[2] = arrow;

        ItemStack instantHealthPotion = PotionItemBuilder.builder()
                .potionMaterial(PotionItemBuilder.PotionMaterial.DRINKABLE)
                .potionEffects(List.of(new PotionEffect(PotionEffectType.HEAL, 0, 0, true, false)))
                .color(Color.RED)
                .displayName(ChatColor.RED + "Healing Potion (2 " + Unicodes.HEART + ")")
                .rarity(Rarity.COMMON)
                .isUnbreakable(true)
                .defaultItemFlags()
                .build();
        result[3] = instantHealthPotion;

        ItemStack fireResistancePotion = PotionItemBuilder.builder()
                .potionMaterial(PotionItemBuilder.PotionMaterial.DRINKABLE)
                .potionEffects(List.of(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 35 * 20, 0, true, false)))
                .color(Color.ORANGE)
                .displayName(ChatColor.GOLD + "Fire Resistance Potion (35s)")
                .rarity(Rarity.COMMON)
                .isUnbreakable(true)
                .itemFlags(Set.of(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES))
                .build();
        result[4] = fireResistancePotion;

        ItemStack nightVisionPotion = PotionItemBuilder.builder()
                .potionMaterial(PotionItemBuilder.PotionMaterial.DRINKABLE)
                .potionEffects(List.of(new PotionEffect(PotionEffectType.NIGHT_VISION, 45 * 20, 0, true, false)))
                .color(Color.BLUE)
                .displayName(ChatColor.DARK_BLUE + "Night Vision Potion (45s)")
                .rarity(Rarity.COMMON)
                .isUnbreakable(true)
                .itemFlags(Set.of(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES))
                .build();
        result[5] = nightVisionPotion;

        return result;
    }

    @NotNull
    public static Kit createKit() {
        return new Kit("human", createArmor(), createItems());
    }
}
