package dev.lotnest.adventure.chapter.zero.cutscenes.episode.one;

import dev.lotnest.adventure.chapter.zero.ChapterZeroPlugin;
import dev.lotnest.adventure.common.CharacterType;
import dev.lotnest.adventure.common.cutscene.Cutscene;
import dev.lotnest.adventure.common.scheduler.SchedulerUtil;
import dev.lotnest.adventure.common.texture.ClientboundTexturedServerPlayer;
import dev.lotnest.adventure.common.util.EntityUtil;
import dev.lotnest.adventure.common.util.MonologueUtil;
import dev.lotnest.adventure.common.util.SoundUtil;
import dev.lotnest.adventure.common.util.TicksConverter;
import dev.lotnest.adventure.common.util.TitleUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import static java.util.Objects.requireNonNull;

public class PrologueCutscene extends Cutscene {

    private static final Component PROLOGUE_TITLE_COMPONENT = Component.text("Adventure: Chapter Zero", NamedTextColor.GREEN);
    private static final Component PROLOGUE_SUBTITLE_COMPONENT = Component.text("Prologue", NamedTextColor.GOLD);

    private static final Component CREDITS_TITLE_COMPONENT = Component.text("A project", NamedTextColor.GRAY);
    private static final Component CREDITS_SUBTITLE_COMPONENT = Component.text("by Lotnest & HadesTurtle", NamedTextColor.YELLOW);

    private static final World WORLD = requireNonNull(Bukkit.getWorld("Adventure_Holden"));

    public PrologueCutscene(@NotNull Player player, @NotNull Plugin plugin) {
        super("chapter_zero_prologue", WORLD.getSpawnLocation(), WORLD.getSpawnLocation(),
                TicksConverter.fromSeconds(5), player, plugin);
        withBlindnessEffect().withZoomedInEffect();
    }

    //TODO rewrite this method
    @Override
    public void run() {
        Player player = getPlayer();
        Audience audience = ((ChapterZeroPlugin) getPlugin()).getAdventureApi().player(player);

        player.setPlayerTime(0L, false);

        Bukkit.getScheduler().runTask(getPlugin(), () -> {
            TitleUtil.sendTitle(audience, CREDITS_TITLE_COMPONENT, CREDITS_SUBTITLE_COMPONENT);

            Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
                player.setGameMode(GameMode.SPECTATOR);

                ClientboundTexturedServerPlayer thorne = spawnThorne(player);
                Location cameraTargetLocation = new Location(player.getWorld(), -292.0, 6.5, 70.0, 90.5F, 3.3F);

                player.teleport(new Location(player.getWorld(), -284.0, 6.5, 70.0, 90.5F, 3.3F));

                MonologueUtil.send(audience, CharacterType.THORNE.getName(), Component.text(
                                "And that goes in here... turn on the valve... and done!"
                        ).color(NamedTextColor.GREEN)
                );

                Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
                    player.setPlayerTime(18000L, false);
                    MonologueUtil.send(audience,
                            CharacterType.THORNE.getName(), Component.text(
                                    "Huh, it's gotten this late already? I guess I will have to make the delivery" +
                                            " runs tomorrow."
                            ).color(NamedTextColor.GREEN));
                }, TicksConverter.THREE_SECONDS);

                SchedulerUtil.scheduleSyncRepeatingTaskWithCancel(getPlugin(), () -> {
                    Location currentLocation = player.getLocation().clone();
                    if (currentLocation.distance(cameraTargetLocation) <= 0.2) {
                        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
                            double bedLocationX = -302.0;
                            double bedLocationY = 6.0;
                            double bedLocationZ = 74.0;

                            player.teleport(new Location(player.getWorld(), -304.0, 7.0, 69.0, -13.1F, 29.3F));
                            thorne.teleport(new Location(player.getWorld(), bedLocationX, bedLocationY, bedLocationZ, 91.1F, -0.2F));
                            EntityUtil.makeClientboundServerPlayerSleep(thorne.getResultBukkit(),
                                    player.getWorld().getBlockAt((int) bedLocationX, (int) bedLocationY, (int) bedLocationZ));

                            Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
                                TitleUtil.sendTitle(audience, PROLOGUE_TITLE_COMPONENT, PROLOGUE_SUBTITLE_COMPONENT);

                                SchedulerUtil.scheduleSyncRepeatingTaskWithCancel(getPlugin(), () ->
                                        player.teleport(
                                                new Location(player.getWorld(), -312.0, 12.0, 71.0, 91.0F, -70.3F)
                                        ), 0L, 1L, TitleUtil.DEFAULT_FULL_CYCLE_TICKS, () -> {
                                    player.setPlayerTime(0L, false);
                                    if (player.getPreviousGameMode() != null) {
                                        player.setGameMode(player.getPreviousGameMode() != GameMode.SPECTATOR
                                                ? player.getPreviousGameMode() : GameMode.ADVENTURE);
                                    }

                                    thorne.remove();
                                });
                            }, TicksConverter.THREE_SECONDS);
                        }, 5L);
                        return;
                    }

                    currentLocation.add(
                            cameraTargetLocation.clone()
                                    .subtract(currentLocation)
                                    .toVector()
                                    .normalize()
                                    .multiply(DEFAULT_CAMERA_PANNING_SPEED)
                    );
                    currentLocation.setYaw(cameraTargetLocation.getYaw());
                    currentLocation.setPitch(cameraTargetLocation.getPitch());

                    player.teleport(currentLocation);
                }, 0L, 1L, TicksConverter.SEVEN_SECONDS - 8L);

                for (int i = 0; i < 3; i++) {
                    Bukkit.getScheduler().runTaskLater(getPlugin(), () -> player.playSound(
                                    player, Sound.BLOCK_GRASS_PLACE, SoundCategory.BLOCKS, 1.0F, SoundUtil.getRandomPitch()
                            ), 30L * i
                    );
                }

                Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
                    for (int i = 0; i < 3; i++) {
                        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> player.playSound(
                                        player, Sound.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, SoundUtil.getRandomPitch()
                                ), 32L * i
                        );
                    }
                }, TicksConverter.ONE_SECOND);
            }, TicksConverter.FIVE_SECONDS);

        });
    }

    @NotNull
    private ClientboundTexturedServerPlayer spawnThorne(@NotNull Player receivingPlayer) {
        ClientboundTexturedServerPlayer thorne = new ClientboundTexturedServerPlayer(
                CharacterType.THORNE.getSkinTexture(), CharacterType.THORNE.getName(), receivingPlayer
        );
        thorne.spawn(new Location(receivingPlayer.getWorld(), -297.75, 6.0, 71.5, -179.9F, 3.0F));
        return thorne;
    }

}
