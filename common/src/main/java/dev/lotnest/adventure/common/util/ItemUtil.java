package dev.lotnest.adventure.common.util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class ItemUtil {

    private ItemUtil() {
    }

    public static boolean isEqual(@NotNull ItemStack itemStack1, @NotNull ItemStack itemStack2) {
        ItemMeta itemMeta1 = itemStack1.getItemMeta();
        ItemMeta itemMeta2 = itemStack2.getItemMeta();

        boolean isMaterialEqual = itemStack1.getType() == itemStack2.getType();

        if (itemMeta1 != null && itemMeta2 != null) {
            return isMaterialEqual && itemMeta1.getDisplayName().equals(itemMeta2.getDisplayName());
        }

        return isMaterialEqual;
    }

    public static boolean isNotEqual(@NotNull ItemStack itemStack1, @NotNull ItemStack itemStack2) {
        return !isEqual(itemStack1, itemStack2);
    }

}
