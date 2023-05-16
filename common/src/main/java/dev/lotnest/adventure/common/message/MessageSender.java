package dev.lotnest.adventure.common.message;

import dev.lotnest.adventure.common.player.AdventurePlayer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Builder
@Getter
@Setter
public class MessageSender {

    private boolean isPrefixed;
    private boolean isColored;
    private boolean isCentered;

    @NotNull
    public String colorMessage(@Nullable String message) {
        if (message == null) {
            return StringUtils.EMPTY;
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public void sendMessage(@Nullable Player player, @Nullable String message) {
        if (player == null) {
            return;
        }

        if (message == null) {
            message = StringUtils.EMPTY;
        }

        if (isPrefixed) {
            //TODO
        }

        if (isColored) {
            message = colorMessage(message);
        }

        if (isCentered) {
            //TODO
        }

        player.sendMessage(message);
    }

    public void sendMessage(@NotNull AdventurePlayer adventurePlayer, @Nullable String message) {
        adventurePlayer.getPlayer().ifPresent(player -> sendMessage(player, message));
    }

    public void sendMessages(@Nullable Player player, @Nullable String... messages) {
        for (String message : messages) {
            sendMessage(player, message);
        }
    }

    public void sendMessages(@NotNull AdventurePlayer adventurePlayer, @Nullable String... messages) {
        adventurePlayer.getPlayer().ifPresent(player -> sendMessages(player, messages));
    }

    public void sendActionBarMessage(@Nullable Player player, @Nullable String message) {
        if (player == null) {
            return;
        }

        if (message == null) {
            message = StringUtils.EMPTY;
        }

        if (isColored) {
            message = colorMessage(message);
        }

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }
}
