package dev.lotnest.adventure.common.item.builder;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LeatherArmorItemBuilder extends ItemBuilder {

    private LeatherMaterial leatherMaterial;
    private Color color;

    private LeatherArmorItemBuilder() {
    }

    @Contract(value = " -> new", pure = true)
    @NotNull
    public static LeatherArmorItemBuilder builder() {
        return new LeatherArmorItemBuilder();
    }

    @NotNull
    public LeatherArmorItemBuilder leatherMaterial(@NotNull LeatherMaterial leatherMaterial) {
        this.leatherMaterial = leatherMaterial;
        return this;
    }

    @NotNull
    public LeatherArmorItemBuilder color(@Nullable Color color) {
        this.color = color;
        return this;
    }

    @NotNull
    public ItemStack build() {
        ItemStack result = build(leatherMaterial.getMaterial(), amount, displayName, lore, isUnbreakable,
                enchantments, itemFlags);

        if (color != null) {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) result.getItemMeta();
            if (leatherArmorMeta != null) {
                leatherArmorMeta.setColor(color);
                result.setItemMeta(leatherArmorMeta);
            }
        }

        return result;
    }

    @RequiredArgsConstructor
    @Getter
    public enum LeatherMaterial {

        HELMET(Material.LEATHER_HELMET),
        CHESTPLATE(Material.LEATHER_CHESTPLATE),
        LEGGINGS(Material.LEATHER_LEGGINGS),
        BOOTS(Material.LEATHER_BOOTS);

        private final Material material;

    }

}
