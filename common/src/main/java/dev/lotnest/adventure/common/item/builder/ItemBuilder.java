package dev.lotnest.adventure.common.item.builder;

import com.google.common.collect.Lists;
import dev.lotnest.adventure.common.util.Rarity;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class ItemBuilder {

    protected Material material;
    protected int amount = 1;
    protected String displayName;
    protected Rarity rarity;
    protected List<String> lore;
    protected boolean isUnbreakable;
    protected Map<Enchantment, Integer> enchantments;
    protected Set<ItemFlag> itemFlags;

    protected ItemBuilder() {
    }

    @Contract(value = " -> new", pure = true)
    @NotNull
    public static ItemBuilder builder() {
        return new ItemBuilder() {
        };
    }

    @NotNull
    public ItemBuilder material(@Nullable Material material) {
        this.material = material;
        return this;
    }

    @NotNull
    public final ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    @NotNull
    public final ItemBuilder displayName(@Nullable String displayName) {
        this.displayName = displayName;
        return this;
    }

    @NotNull
    public final ItemBuilder rarity(@Nullable Rarity rarity) {
        this.rarity = rarity;
        return this;
    }

    @NotNull
    public final ItemBuilder lore(@Nullable List<String> lore) {
        this.lore = lore;
        return this;
    }

    @NotNull
    public final ItemBuilder isUnbreakable(boolean isUnbreakable) {
        this.isUnbreakable = isUnbreakable;
        return this;
    }

    @NotNull
    public final ItemBuilder enchantments(@Nullable Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
        return this;
    }

    @NotNull
    public final ItemBuilder itemFlags(@Nullable Set<ItemFlag> itemFlags) {
        this.itemFlags = itemFlags;
        return this;
    }

    @NotNull
    public final ItemBuilder defaultItemFlags() {
        itemFlags(Set.of(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_POTION_EFFECTS));
        return this;
    }

    @NotNull
    public ItemStack build() {
        return build(material, amount, displayName, lore, isUnbreakable, enchantments, itemFlags);
    }

    @NotNull
    protected ItemStack build(@Nullable Material material, int amount, @Nullable String displayName,
                              @Nullable List<String> lore, boolean isUnbreakable,
                              @Nullable Map<Enchantment, Integer> enchantments, @Nullable Set<ItemFlag> itemFlags) {
        if (material == null) {
            return new ItemStack(Material.AIR);
        }

        ItemStack result = new ItemStack(material);
        result.setAmount(amount);
        ItemMeta resultItemMeta = result.getItemMeta();

        if (resultItemMeta != null) {
            resultItemMeta.setDisplayName(displayName);

            if (rarity != null) {
                if (lore == null) {
                    lore = Lists.newArrayList();
                }

                if (!(lore instanceof ArrayList<String>)) {
                    lore = Lists.newArrayList(lore);
                }

                lore.add(0, rarity.toString());

                if (lore.size() > 1) {
                    lore.add(1, "");
                }
            }

            if (lore != null && !lore.isEmpty()) {
                resultItemMeta.setLore(lore);
            }

            resultItemMeta.setUnbreakable(isUnbreakable);

            if (enchantments != null && !enchantments.isEmpty()) {
                enchantments.forEach((key, value) -> resultItemMeta.addEnchant(key, value, true));
            }

            if (itemFlags != null && !itemFlags.isEmpty()) {
                itemFlags.forEach(resultItemMeta::addItemFlags);
            }
        }

        result.setItemMeta(resultItemMeta);
        return result;
    }

}

