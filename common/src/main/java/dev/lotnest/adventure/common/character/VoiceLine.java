package dev.lotnest.adventure.common.character;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class VoiceLine {

    @NotNull
    private final String id;

    @NotNull
    private final String text;

    @NotNull
    private final VoiceLineType type;

}
