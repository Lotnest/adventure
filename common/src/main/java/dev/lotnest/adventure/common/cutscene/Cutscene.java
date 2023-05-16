package dev.lotnest.adventure.common.cutscene;

import com.google.common.collect.Lists;
import dev.lotnest.adventure.common.potion.PotionEffects;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@RequiredArgsConstructor
@Setter
public abstract class Cutscene extends BukkitRunnable {

    public static final double DEFAULT_CAMERA_PANNING_SPEED = 0.06;

    @NotBlank
    private final String id;

    @NotNull
    private final Location startLocation;

    @Nullable
    private final Location endLocation;

    private final long durationInTicks;

    @NotNull
    private final Player player;

    @NotNull
    private final Plugin plugin;

    private List<PotionEffect> cameraEffects = Lists.newArrayListWithCapacity(0);

    @NotNull
    public final String getId() {
        return id;
    }

    @NotNull
    public final Location getStartLocation() {
        return startLocation;
    }

    @Nullable
    public final Location getEndLocation() {
        return endLocation;
    }

    public final long getDurationInTicks() {
        return durationInTicks;
    }

    @NotNull
    public final Player getPlayer() {
        return player;
    }

    @NotNull
    public final Plugin getPlugin() {
        return plugin;
    }

    @NotNull
    public final List<PotionEffect> getCameraEffects() {
        return cameraEffects;
    }

    @NotNull
    public final Cutscene withZoomedInEffect() {
        cameraEffects.add(PotionEffects.PERMANENT_SLOWNESS_2);
        return this;
    }

    @NotNull
    public final Cutscene withZoomedOutEffect() {
        cameraEffects.add(PotionEffects.PERMANENT_SPEED_2);
        return this;
    }

    @NotNull
    public final Cutscene withNightVisionEffect() {
        cameraEffects.add(PotionEffects.PERMANENT_NIGHT_VISION);
        return this;
    }

    @NotNull
    public final Cutscene withBlindnessEffect() {
        cameraEffects.add(PotionEffects.PERMANENT_BLINDNESS);
        return this;
    }

}
