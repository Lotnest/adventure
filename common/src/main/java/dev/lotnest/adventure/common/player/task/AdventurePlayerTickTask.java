package dev.lotnest.adventure.common.player.task;

import dev.lotnest.adventure.common.message.MessageSenders;
import dev.lotnest.adventure.common.message.Spacer;
import dev.lotnest.adventure.common.message.Unicodes;
import dev.lotnest.adventure.common.player.AdventurePlayer;
import dev.lotnest.adventure.common.player.Mana;
import dev.lotnest.adventure.common.util.PlayerUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
@Getter
public class AdventurePlayerTickTask extends BukkitRunnable {

    private static final String MESSAGE_FORMAT = ChatColor.RED + Unicodes.HEART + " %d/%d " + Spacer.tab(2) +
            "%s" + Spacer.tab(2) + ChatColor.AQUA + Unicodes.MANA + " %d/%d";

    private final AdventurePlayer adventurePlayer;
    private Player player;
    private int ticksPassed;

    @Override
    public void run() {
        Mana mana = adventurePlayer.getMana();
        if (mana == null) {
            return;
        }

        if (!adventurePlayer.getOfflinePlayer().isOnline()) {
            return;
        }

        player = adventurePlayer.getPlayer().orElse(null);
        if (player == null) {
            return;
        }

        if (player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }

        if (ticksPassed == 5) {
            if (mana.getCurrentMana() < mana.getMaxMana()) {
                mana.addMana(1);
            }
            ticksPassed = 0;
        }

        MessageSenders.COLORED.sendActionBarMessage(player, MESSAGE_FORMAT.formatted(adventurePlayer.getHealth(),
                adventurePlayer.getMaxHealth(), PlayerUtil.getPlayerLocationDirections(player), mana.getCurrentMana(),
                mana.getMaxMana()));
        ticksPassed++;
    }

}
