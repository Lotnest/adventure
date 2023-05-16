package dev.lotnest.adventure.common.scheduler;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SchedulerUtil {

    private SchedulerUtil() {
    }

    /**
     * Schedules a repeating task that will cancel after a specified number of ticks.
     */
    public static void scheduleSyncRepeatingTaskWithCancel(@NotNull Plugin plugin, @NotNull Runnable runnable, long delayTicks,
                                                           long periodTicks, long cancelAfterTicks) {
        scheduleSyncRepeatingTaskWithCancel(plugin, runnable, delayTicks, periodTicks, cancelAfterTicks, null);
    }

    /**
     * Schedules a repeating task that will cancel after a specified number of ticks, can run an action after cancel.
     */
    public static void scheduleSyncRepeatingTaskWithCancel(@NotNull Plugin plugin, @NotNull Runnable runnable, long delayTicks,
                                                           long periodTicks, long cancelAfterTicks, @Nullable Runnable actionOnCancel) {
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        int taskId = scheduler.scheduleSyncRepeatingTask(plugin, runnable, delayTicks, periodTicks);
        scheduler.runTaskLater(plugin, () -> {
            scheduler.cancelTask(taskId);

            if (actionOnCancel != null) {
                actionOnCancel.run();
            }
        }, cancelAfterTicks);
    }

}
