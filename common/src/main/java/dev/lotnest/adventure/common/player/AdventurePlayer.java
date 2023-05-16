package dev.lotnest.adventure.common.player;

import dev.lotnest.adventure.common.character.race.Race;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Builder
@Getter
@Setter
public class AdventurePlayer {

    @NotNull
    private final UUID uuid;

    @Nullable
    private OfflinePlayer offlinePlayer;

    @Nullable
    private Player player;

    @Nullable
    private Race race;

    @Nullable
    private Mana mana;

    @NotNull
    private Settings settings;

    @NotNull
    public OfflinePlayer getOfflinePlayer() {
        if (offlinePlayer == null) {
            offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        }
        return offlinePlayer;
    }

    @NotNull
    public Optional<Player> getPlayer() {
        if (player == null) {
            player = Bukkit.getPlayer(uuid);
        }
        return Optional.ofNullable(player);
    }

    public int getHealth() {
        return getPlayer()
                .map(Player::getHealth)
                .map(Double::intValue)
                .orElse(0);
    }

    public int getMaxHealth() {
        return getPlayer()
                .map(playerFromOptional -> Objects.requireNonNull(
                        playerFromOptional.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue()
                ).map(Double::intValue)
                .orElse(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        return new EqualsBuilder()
                .append(uuid, ((AdventurePlayer) o).uuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(uuid)
                .toHashCode();
    }

    @Override
    @NotNull
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", uuid)
                .append("race", race)
                .append("mana", mana)
                .append("settings", settings)
                .toString();
    }

}
