package dev.lotnest.adventure.common.player;

import com.google.common.collect.Maps;
import dev.lotnest.adventure.common.character.race.Races;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class AdventurePlayerFactory {

    private static final Map<UUID, AdventurePlayer> ADVENTURE_PLAYERS = Maps.newHashMap();

    private AdventurePlayerFactory() {
    }

    @NotNull
    public static Map<UUID, AdventurePlayer> getAdventurePlayers() {
        return ADVENTURE_PLAYERS;
    }

    @NotNull
    public static Optional<AdventurePlayer> getAdventurePlayer(@NotNull OfflinePlayer player) {
        return getAdventurePlayer(player.getUniqueId());
    }

    @NotNull
    public static Optional<AdventurePlayer> getAdventurePlayer(@NotNull UUID uuid) {
        return Optional.ofNullable(ADVENTURE_PLAYERS.get(uuid));
    }

    @NotNull
    public static AdventurePlayer createAdventurePlayer(@NotNull String uuid) {
        return createAdventurePlayer(UUID.fromString(uuid));
    }

    @NotNull
    public static AdventurePlayer createAdventurePlayer(@NotNull UUID uuid) {
        return ADVENTURE_PLAYERS.computeIfAbsent(uuid, u -> AdventurePlayer.builder()
                .uuid(u)
                .race(Races.HUMAN)
                .settings(new Settings())
                .build());
    }

    @NotNull
    public static AdventurePlayer createAdventurePlayer(@NotNull Player player) {
        return ADVENTURE_PLAYERS.computeIfAbsent(player.getUniqueId(), uuid -> AdventurePlayer.builder()
                .uuid(uuid)
                .offlinePlayer(player)
                .player(player)
                .race(Races.HUMAN)
                .mana(Mana.builder()
                        .currentMana(Mana.DEFAULT_STARTING_MANA)
                        .maxMana(Mana.DEFAULT_STARTING_MANA)
                        .build()
                )
                .settings(new Settings())
                .build());
    }

    @NotNull
    public static AdventurePlayer createAdventurePlayer(@NotNull OfflinePlayer offlinePlayer) {
        return ADVENTURE_PLAYERS.computeIfAbsent(offlinePlayer.getUniqueId(), uuid -> AdventurePlayer.builder()
                .uuid(uuid)
                .offlinePlayer(offlinePlayer)
                .race(Races.HUMAN)
                .mana(Mana.builder()
                        .currentMana(Mana.DEFAULT_STARTING_MANA)
                        .maxMana(Mana.DEFAULT_STARTING_MANA)
                        .build()
                )
                .settings(new Settings())
                .build());
    }

}
