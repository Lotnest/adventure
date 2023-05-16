package dev.lotnest.adventure.common.character.kit.factory;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class KitFactory {

    protected KitFactory() {
    }

    public static ItemStack @NotNull [] createArmor() {
        return new ItemStack[4];
    }

    public static ItemStack @NotNull [] createItems() {
        return new ItemStack[36];
    }
}
