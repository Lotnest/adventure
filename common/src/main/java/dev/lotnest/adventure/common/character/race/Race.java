package dev.lotnest.adventure.common.character.race;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.lotnest.adventure.common.character.ability.AbilityHandler;
import dev.lotnest.adventure.common.deserializer.AbilityHandlerDeserializer;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
@Jacksonized
public class Race {

    @NotNull
    private final String id;

    @NotNull
    private final RaceType type;

    @NotNull
    private final String summary;

    @NotNull
    private final String strengths;

    @NotNull
    private final String weaknesses;

    @NotNull
    private final String kitId;

    @JsonProperty("abilityHandlerClass")
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "class")
    @JsonDeserialize(using = AbilityHandlerDeserializer.class)
    @NotNull
    private final AbilityHandler abilityHandler;

}
