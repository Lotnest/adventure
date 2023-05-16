package dev.lotnest.adventure.common.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;

@RequiredArgsConstructor
@Getter
public enum Rarity {

    COMMON(ChatColor.WHITE.toString() + ChatColor.BOLD + "COMMON"),
    UNCOMMON(ChatColor.GREEN.toString() + ChatColor.BOLD + "UNCOMMON"),
    RARE(ChatColor.BLUE.toString() + ChatColor.BOLD + "RARE"),
    EPIC(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "EPIC"),
    LEGENDARY(ChatColor.GOLD.toString() + ChatColor.BOLD + "LEGENDARY"),
    MYTHIC(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "MYTHIC"),
    GODLY(ChatColor.RED.toString() + ChatColor.BOLD + "GODLY");

    private final String displayName;

    @Override
    public String toString() {
        return displayName;
    }

}
