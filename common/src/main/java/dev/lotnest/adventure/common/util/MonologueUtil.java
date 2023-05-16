package dev.lotnest.adventure.common.util;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.jetbrains.annotations.NotNull;

public class MonologueUtil {

    private MonologueUtil() {
    }

    public static void send(@NotNull Audience audience, @NotNull String characterName, @NotNull TextComponent voiceLineTextComponent) {
        TextComponent monologueTextComponent = Component.text()
                .append(Component.text().appendNewline())
                .append(Component.text().appendNewline())
                .append(Component.text().appendNewline())
                .append(Component.text(characterName + ": "))
                .append(voiceLineTextComponent.appendNewline())
                .append(Component.text().appendNewline())
                .append(Component.text().appendNewline())
                .build();
        audience.sendMessage(monologueTextComponent);
    }

}
