package dev.lotnest.adventure.common.character.ability;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.checkerframework.checker.index.qual.Positive;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
@Builder
@Jacksonized
public class Ability {

    @NotNull
    private final String id;

    @NotNull
    private final String name;

    @NotNull
    private final String description;

    @Nullable
    Integer durationInSeconds;

    @Positive
    private final int cooldownInSeconds;

    @Positive
    private final int manaCost;

}
