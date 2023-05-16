package dev.lotnest.adventure.common.player;

import lombok.Data;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

@Data
public class Settings {

    private boolean showParticles = true;
    private boolean showBloodEffects = true;

    @NotNull
    public String getShowParticlesValueAsFormattedString() {
        return showParticles ? ChatColor.GREEN + "ON" : ChatColor.RED + "OFF";
    }

    @NotNull
    public Material getShowParticlesValueAsMaterial() {
        return showParticles ? Material.LIME_DYE : Material.RED_DYE;
    }

    @NotNull
    public String getShowBloodEffectsValueAsFormattedString() {
        return showBloodEffects ? ChatColor.GREEN + "ON" : ChatColor.RED + "OFF";
    }

    @NotNull
    public Material getShowBloodEffectsValueAsMaterial() {
        return showBloodEffects ? Material.LIME_DYE : Material.RED_DYE;
    }

}
