package dev.lotnest.adventure.common.character;

import dev.lotnest.adventure.common.character.ability.Ability;
import dev.lotnest.adventure.common.character.race.RaceType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class Character {

    @NotNull
    private final String id;

    @NotNull
    private final String name;

    @NotNull
    private final String description;

    @NotNull
    private final EntityType entityType;

    @NotNull
    private final Set<VoiceLine> voiceLines;

    @NotNull
    private final RaceType raceType;

    @NotNull
    private final Set<Ability> extraAbilities;

}
