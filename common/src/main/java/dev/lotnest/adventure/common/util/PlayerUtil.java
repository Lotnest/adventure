package dev.lotnest.adventure.common.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerUtil {

    private PlayerUtil() {
    }

    public static String getPlayerLocationDirections(@NotNull Player player) {
        Location location = player.getLocation();
        int x = location.getBlockX();
        int z = location.getBlockZ();
        float yaw = player.getLocation().getYaw();
        String direction;

        if (yaw < 0) {
            yaw += 360;
        }

        if (yaw >= 338 || yaw < 23) {
            direction = ChatColor.GOLD + "S";
        } else if (yaw < 68) {
            direction = ChatColor.GOLD + "S" + ChatColor.LIGHT_PURPLE + "W";
        } else if (yaw < 113) {
            direction = ChatColor.LIGHT_PURPLE + "W";
        } else if (yaw < 158) {
            direction = ChatColor.WHITE + "N" + ChatColor.LIGHT_PURPLE + "W";
        } else if (yaw < 203) {
            direction = ChatColor.WHITE + "N";
        } else if (yaw < 248) {
            direction = ChatColor.WHITE + "N" + ChatColor.GREEN + "E";
        } else if (yaw < 293) {
            direction = ChatColor.GREEN + "E";
        } else {
            direction = ChatColor.GOLD + "S" + ChatColor.GREEN + "E";
        }

        return String.format("%s %s %s", ChatColor.GRAY.toString() + x, direction, ChatColor.GRAY.toString() + z);
    }

}
