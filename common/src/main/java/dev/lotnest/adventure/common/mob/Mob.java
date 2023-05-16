package dev.lotnest.adventure.common.mob;

import dev.lotnest.adventure.common.message.Unicodes;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.world.entity.Entity;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Mob {

    ChatColor FRIENDLY_COLOR = ChatColor.GREEN;
    ChatColor ENEMY_COLOR = ChatColor.RED;

    @NotNull
    static String getFormattedName(boolean isFriendly, @NotNull String name, int level, float health) {
        return (isFriendly ? FRIENDLY_COLOR : ENEMY_COLOR) + name + ChatColor.GOLD + " [" + ChatColor.YELLOW + "Lvl "
                + level + ChatColor.GOLD + "] " + ChatColor.RED + (int) health + " " + Unicodes.HEART;
    }

    int getMobLevel();

    float getMobBaseHealth();

    static void summon(@NotNull Entity entity, @NotNull CraftWorld world) {
        world.addEntityToWorld(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    void updateMobName();

    @NotNull
    List<ItemStack> getDrops();

}
