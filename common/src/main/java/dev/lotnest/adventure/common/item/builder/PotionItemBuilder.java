package dev.lotnest.adventure.common.item.builder;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PotionItemBuilder extends ItemBuilder {

    private PotionMaterial potionMaterial;
    private List<PotionEffect> potionEffects;
    private Color color;

    private PotionItemBuilder() {
    }

    @Contract(value = " -> new", pure = true)
    @NotNull
    public static PotionItemBuilder builder() {
        return new PotionItemBuilder();
    }

    @NotNull
    public PotionItemBuilder potionMaterial(@NotNull PotionMaterial potionMaterial) {
        this.potionMaterial = potionMaterial;
        return this;
    }

    @NotNull
    public PotionItemBuilder potionEffects(@NotNull List<PotionEffect> potionEffects) {
        this.potionEffects = potionEffects;
        return this;
    }

    @NotNull
    public PotionItemBuilder color(@Nullable Color color) {
        this.color = color;
        return this;
    }

    @NotNull
    public ItemStack build() {
        ItemStack result = build(potionMaterial.getMaterial(), amount, displayName, lore, isUnbreakable,
                enchantments, itemFlags);

        if (potionEffects != null || color != null) {
            PotionMeta potionMeta = (PotionMeta) result.getItemMeta();
            if (potionMeta != null) {
                if (potionEffects != null && !potionEffects.isEmpty()) {
                    for (PotionEffect potionEffect : potionEffects) {
                        potionMeta.addCustomEffect(potionEffect, true);
                    }
                }

                potionMeta.setColor(color);
                result.setItemMeta(potionMeta);
            }
        }

        return result;
    }

    @RequiredArgsConstructor
    @Getter
    public enum PotionMaterial {

        DRINKABLE(Material.POTION),
        SPLASH(Material.SPLASH_POTION),
        LINGERING(Material.LINGERING_POTION),
        TIPPED_ARROW(Material.TIPPED_ARROW);

        private final Material material;

    }

}
