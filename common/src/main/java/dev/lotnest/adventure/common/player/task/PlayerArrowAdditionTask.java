package dev.lotnest.adventure.common.player.task;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Getter
public class PlayerArrowAdditionTask extends BukkitRunnable {

    @NotNull
    private final Player player;

    @NotNull
    private final Arrow arrow;

    @Override
    public void run() {

    }

}
