package dev.lotnest.adventure.chapter.zero.listener;

import dev.lotnest.adventure.chapter.zero.ChapterZeroPlugin;
import dev.lotnest.adventure.chapter.zero.cutscenes.episode.one.PrologueCutscene;
import dev.lotnest.adventure.common.character.kit.Kits;
import dev.lotnest.adventure.common.character.race.Race;
import dev.lotnest.adventure.common.item.Items;
import dev.lotnest.adventure.common.player.AdventurePlayer;
import dev.lotnest.adventure.common.player.AdventurePlayerFactory;
import dev.lotnest.adventure.common.player.task.AdventurePlayerTickTask;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class ChapterZeroTest implements Listener {

    private static final ChapterZeroPlugin PLUGIN = ChapterZeroPlugin.getInstance();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(@NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (AdventurePlayerFactory.getAdventurePlayer(player).isPresent()) {
            return;
        }

        AdventurePlayer adventurePlayer = AdventurePlayerFactory.createAdventurePlayer(player);

        if (adventurePlayer.getRace() != null) {
            Kits.findKitById(adventurePlayer.getRace().getKitId())
                    .ifPresent(kit -> kit.apply(player));
            player.getInventory().setItem(8, Items.getCharacterMenu(player));
        }

        new AdventurePlayerTickTask(adventurePlayer).runTaskTimer(PLUGIN, 0L, 1L);

        PLUGIN.getCutsceneManager().startCutscene(new PrologueCutscene(player, PLUGIN));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAbility(@NotNull PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null || !Kits.isItemWithAbility(item)) {
            return;
        }

        AdventurePlayerFactory.getAdventurePlayer(player).ifPresent(adventurePlayer -> {
            Race playerRace = adventurePlayer.getRace();

            if (playerRace == null) {
                return;
            }

            playerRace.getAbilityHandler()
                    .handleAbilityClickSequenceClick(ChapterZeroPlugin.getInstance(), adventurePlayer, event.getAction());
        });
    }

}
