package dev.lotnest.adventure.common.cutscene;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class CutsceneManager {

    private static final Map<UUID, Cutscene> ACTIVE_CUTSCENES = Maps.newHashMap();

    public void startCutscene(@NotNull Cutscene cutscene) {
        Player player = cutscene.getPlayer();
        if (isInCutscene(player)) {
            return;
        }

        Bukkit.getScheduler().runTaskLater(cutscene.getPlugin(), () -> {
            GameMode gameMode = player.getGameMode();
            Location location = player.getLocation();
            boolean canFly = player.getAllowFlight();
            boolean isFlying = player.isFlying();
            boolean isCollidable = player.isCollidable();
            Collection<PotionEffect> activePotionEffects = player.getActivePotionEffects();

            player.setGameMode(GameMode.SPECTATOR);
            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
            player.addPotionEffects(cutscene.getCameraEffects());
            player.teleport(cutscene.getStartLocation());
            player.updateInventory();

            ACTIVE_CUTSCENES.put(player.getUniqueId(), cutscene);

            cutscene.run();

            Bukkit.getScheduler().runTaskLater(cutscene.getPlugin(), () -> {
                player.setGameMode(gameMode);
                player.teleport(cutscene.getEndLocation() == null ? location : cutscene.getEndLocation());
                player.setAllowFlight(canFly);
                player.setFlying(isFlying);
                player.setCollidable(isCollidable);
                player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
                player.addPotionEffects(activePotionEffects);
                player.updateInventory();

                ACTIVE_CUTSCENES.remove(player.getUniqueId());
            }, cutscene.getDurationInTicks());
        }, 1L);
    }

    public boolean isInCutscene(@NotNull Player player) {
        return ACTIVE_CUTSCENES.containsKey(player.getUniqueId());
    }

    @NotNull
    public Optional<Cutscene> getActiveCutscene(@NotNull Player player) {
        return Optional.ofNullable(ACTIVE_CUTSCENES.get(player.getUniqueId()));
    }

}
