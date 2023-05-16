package dev.lotnest.adventure.common.listener;

import dev.lotnest.adventure.common.cutscene.CutsceneManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class PlayerMovementBlocker implements Listener {

    @NotNull
    private final CutsceneManager cutsceneManager;

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerMove(@NotNull PlayerMoveEvent event) {
        if (cutsceneManager.isInCutscene(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

}
